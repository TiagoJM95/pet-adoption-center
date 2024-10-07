package com.petadoption.center.service;

import com.petadoption.center.converter.*;
import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.enums.Status;
import com.petadoption.center.exception.not_found.InterestNotFoundException;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;
import com.petadoption.center.exception.not_found.PetNotFoundException;
import com.petadoption.center.exception.not_found.InvalidStatusChangeException;
import com.petadoption.center.exception.not_found.UserNotFoundException;
import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.model.Interest;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Family;
import com.petadoption.center.repository.InterestRepository;
import com.petadoption.center.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.petadoption.center.converter.EnumConverter.convertStringToEnum;
import static com.petadoption.center.enums.Status.*;
import static com.petadoption.center.util.Messages.*;

@Service
public class InterestService implements InterestServiceI {

    private final InterestRepository interestRepository;
    private final PetServiceI petService;
    private final OrganizationServiceI organizationService;
    private final UserServiceI userService;
    private final AdoptionFormServiceI adoptionFormService;

    @Autowired
    public InterestService(InterestRepository interestRepository, PetServiceI petService, OrganizationServiceI organizationService, UserServiceI userService, AdoptionFormServiceI adoptionFormService) {
        this.interestRepository = interestRepository;
        this.petService = petService;
        this.organizationService = organizationService;
        this.userService = userService;
        this.adoptionFormService = adoptionFormService;
    }

    @Override
    public List<InterestGetDto> getCurrentInterestsInOrganizationPets(Pageable pageable, String organizationId) throws OrganizationNotFoundException {
        Organization org = OrgConverter.toModel(organizationService.getOrganizationById(organizationId));
        List<Status> statusList = List.of(PENDING, FORM_REQUESTED, FORM_FILLED);
        Page<Interest> interests = interestRepository.findByOrganizationAndStatusIn(org, statusList, pageable);
        return interests.stream().map(InterestConverter::toDto).toList();
    }

    @Override
    public List<InterestGetDto> getInterestHistoryInOrganizationPets(int page, int size, String sortBy, String organizationId) throws OrganizationNotFoundException {
        Organization org = OrgConverter.toModel(organizationService.getOrganizationById(organizationId));
        List<Status> statusList = List.of(ACCEPTED, REJECTED);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Interest> interests = interestRepository.findByOrganizationAndStatusIn(org, statusList, pageRequest);
        return interests.stream().map(InterestConverter::toDto).toList();
    }

    @Override
    public List<InterestGetDto> getCurrentUserInterests(int page, int size, String sortBy, String userId) throws UserNotFoundException {
        User user = UserConverter.toModel(userService.getUserById(userId));
        List<Status> statusList = List.of(PENDING, FORM_REQUESTED, FORM_FILLED);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Interest> interests = interestRepository.findByUserAndStatusIn(user, statusList, pageRequest);
        return interests.stream().map(InterestConverter::toDto).toList();
    }

    @Override
    public List<InterestGetDto> getUserInterestHistory(int page, int size, String sortBy, String userId) throws UserNotFoundException {
        User user = UserConverter.toModel(userService.getUserById(userId));
        List<Status> statusList = List.of(ACCEPTED, REJECTED);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Interest> interests = interestRepository.findByUserAndStatusIn(user, statusList, pageRequest);
        return interests.stream().map(InterestConverter::toDto).toList();
    }

    @Override
    public InterestGetDto getInterestById(String id) throws InterestNotFoundException {
        return InterestConverter.toDto(findById(id));
    }

    @Override
    public InterestGetDto addNewInterest(InterestCreateDto dto) throws UserNotFoundException, PetNotFoundException, OrganizationNotFoundException {
        Interest interest = new Interest();
        interest.setUser(UserConverter.toModel(userService.getUserById(dto.userId())));
        interest.setPet(PetConverter.toModel(petService.getPetById(dto.petId())));
        interest.setOrganization(OrgConverter.toModel(organizationService.getOrganizationById(dto.organizationId())));
        interest.setStatus(PENDING);
        return InterestConverter.toDto(interestRepository.save(interest));
    }

    @Override
    public InterestGetDto updateInterest(String id, InterestUpdateDto dto) throws InterestNotFoundException, InvalidStatusChangeException, UserNotFoundException, PetNotFoundException {
        Interest interest = findById(id);
        Status status = convertStringToEnum(dto.status(), Status.class);
        verifyIfStatusChangeIsValid(interest.getStatus(), status);
        if (status == FORM_REQUESTED){
            createAndSaveAdoptionForm(interest);
        }
        interest.setStatus(status);
        interest.setReviewTimestamp(LocalDateTime.now());
        return InterestConverter.toDto(interestRepository.save(interest));
    }

    private void createAndSaveAdoptionForm(Interest interest) throws UserNotFoundException, PetNotFoundException {
        AdoptionFormGetDto newForm = adoptionFormService.addNewAdoptionForm(AdoptionFormCreateDto.builder()
                .userId(interest.getUser().getId())
                .petId(interest.getPet().getId())
                .userFamily(new Family())
                .petVacationHome("")
                .isResponsibleForPet(false)
                .otherNotes("")
                .petAddress(interest.getOrganization().getAddress())
                .build());
        AdoptionForm savedForm = AdoptionFormConverter.toModel(newForm);
        interest.setAdoptionForm(savedForm);
    }

    private void verifyIfStatusChangeIsValid(Status currentStatus, Status newStatus) throws InvalidStatusChangeException {
        if (currentStatus == newStatus) {
            throw new InvalidStatusChangeException(INVALID_STATUS_CHANGE_SAME + newStatus.getDescription());
        }
        if (newStatus == ACCEPTED && currentStatus != FORM_FILLED){
            throw new InvalidStatusChangeException(INVALID_STATUS_CHANGE);
        }
        if (currentStatus == ACCEPTED || currentStatus == REJECTED) {
            throw new InvalidStatusChangeException(INVALID_STATUS_CHANGE_FINAL);
        }
        switch (currentStatus) {
            case PENDING -> {
                if (newStatus != REJECTED && newStatus != FORM_REQUESTED) {
                    throw new InvalidStatusChangeException(INVALID_STATUS_CHANGE);
                }
            }
            case FORM_REQUESTED -> {
                if (newStatus != REJECTED && newStatus != FORM_FILLED) {
                    throw new InvalidStatusChangeException(INVALID_STATUS_CHANGE);
                }
            }
            case FORM_FILLED -> {
                if (newStatus != REJECTED && newStatus != ACCEPTED) {
                    throw new InvalidStatusChangeException(INVALID_STATUS_CHANGE);
                }
            }
        }
    }

    @Override
    public String deleteInterest(String id) throws InterestNotFoundException {
        findById(id);
        interestRepository.deleteById(id);
        return INTEREST_WITH_ID + id + DELETE_SUCCESS;
    }

    private Interest findById(String interestId) throws InterestNotFoundException {
        return interestRepository.findById(interestId).orElseThrow(
                () -> new InterestNotFoundException(INTEREST_WITH_ID + interestId + NOT_FOUND));
    }
}
