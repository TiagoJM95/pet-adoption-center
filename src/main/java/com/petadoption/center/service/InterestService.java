package com.petadoption.center.service;

import com.petadoption.center.converter.*;
import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.enums.Status;
import com.petadoption.center.exception.not_found.InterestNotFoundException;
import com.petadoption.center.exception.InvalidStatusChangeException;
import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.model.Interest;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Family;
import com.petadoption.center.repository.InterestRepository;
import com.petadoption.center.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<InterestGetDto> getCurrentByOrganizationId(Pageable pageable, String organizationId) {
        Organization org = OrganizationConverter.toModel(organizationService.getById(organizationId));
        List<Status> statusList = List.of(PENDING, FORM_REQUESTED, FORM_FILLED);
        Page<Interest> interests = interestRepository.findByOrganizationAndStatusIn(org, statusList, pageable);
        return interests.stream().map(InterestConverter::toDto).toList();
    }

    @Override
    public List<InterestGetDto> getHistoryByOrganizationId(Pageable pageable, String organizationId) {
        Organization org = OrganizationConverter.toModel(organizationService.getById(organizationId));
        List<Status> statusList = List.of(ACCEPTED, REJECTED);
        Page<Interest> interests = interestRepository.findByOrganizationAndStatusIn(org, statusList, pageable);
        return interests.stream().map(InterestConverter::toDto).toList();
    }

    @Override
    public List<InterestGetDto> getCurrentByUserId(Pageable pageable, String userId) {
        User user = UserConverter.toModel(userService.getById(userId));
        List<Status> statusList = List.of(PENDING, FORM_REQUESTED, FORM_FILLED);
        Page<Interest> interests = interestRepository.findByUserAndStatusIn(user, statusList, pageable);
        return interests.stream().map(InterestConverter::toDto).toList();
    }

    @Override
    public List<InterestGetDto> getHistoryByUserId(Pageable pageable, String userId) {
        User user = UserConverter.toModel(userService.getById(userId));
        List<Status> statusList = List.of(ACCEPTED, REJECTED);
        Page<Interest> interests = interestRepository.findByUserAndStatusIn(user, statusList, pageable);
        return interests.stream().map(InterestConverter::toDto).toList();
    }

    @Override
    public InterestGetDto getById(String id) {
        return InterestConverter.toDto(findById(id));
    }

    @Override
    public InterestGetDto create(InterestCreateDto dto) {
        Interest interest = new Interest();
        interest.setUser(UserConverter.toModel(userService.getById(dto.userId())));
        interest.setPet(PetConverter.toModel(petService.getById(dto.petId())));
        interest.setOrganization(OrganizationConverter.toModel(organizationService.getById(dto.organizationId())));
        interest.setStatus(PENDING);
        return InterestConverter.toDto(interestRepository.save(interest));
    }

    @Override
    public InterestGetDto update(String id, InterestUpdateDto dto) {
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

    private void createAndSaveAdoptionForm(Interest interest) {
        AdoptionFormGetDto newForm = adoptionFormService.create(AdoptionFormCreateDto.builder()
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

    private void verifyIfStatusChangeIsValid(Status currentStatus, Status newStatus) {
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
    public String delete(String id) {
        findById(id);
        interestRepository.deleteById(id);
        return INTEREST_WITH_ID + id + DELETE_SUCCESS;
    }

    private Interest findById(String interestId) {
        return interestRepository.findById(interestId).orElseThrow(
                () -> new InterestNotFoundException(INTEREST_WITH_ID + interestId + NOT_FOUND));
    }
}