package com.petadoption.center.service;

import com.petadoption.center.converter.InterestConverter;
import com.petadoption.center.converter.OrgConverter;
import com.petadoption.center.converter.PetConverter;
import com.petadoption.center.converter.UserConverter;
import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.enums.Status;
import com.petadoption.center.exception.InvalidStatusException;
import com.petadoption.center.exception.interest.InterestNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.model.Interest;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.User;
import com.petadoption.center.repository.InterestRepository;
import com.petadoption.center.service.interfaces.InterestServiceI;
import com.petadoption.center.service.interfaces.OrganizationServiceI;
import com.petadoption.center.service.interfaces.PetServiceI;
import com.petadoption.center.service.interfaces.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.petadoption.center.enums.Status.*;
import static com.petadoption.center.util.Messages.*;

@Service
public class InterestService implements InterestServiceI {

    private final InterestRepository interestRepository;
    private final PetServiceI petService;
    private final OrganizationServiceI organizationService;
    private final UserServiceI userService;

    @Autowired
    public InterestService(InterestRepository interestRepository, PetServiceI petService, OrganizationServiceI organizationService, UserServiceI userService) {
        this.interestRepository = interestRepository;
        this.petService = petService;
        this.organizationService = organizationService;
        this.userService = userService;
    }

    @Override
    public List<InterestGetDto> getCurrentInterestsInOrganizationPets(int page, int size, String sortBy, String organizationId) throws OrgNotFoundException {
        Organization org = OrgConverter.toModel(organizationService.getOrganizationById(organizationId));
        List<Status> statusList = List.of(PENDING, FORM_REQUESTED, FORM_FILLED);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Interest> interests = interestRepository.findByOrganizationAndStatusIn(org, statusList, pageRequest);
        return interests.stream().map(InterestConverter::toDto).toList();
    }

    @Override
    public List<InterestGetDto> getInterestHistoryInOrganizationPets(int page, int size, String sortBy, String organizationId) throws OrgNotFoundException {
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
    public InterestGetDto addNewInterest(InterestCreateDto dto) throws UserNotFoundException, PetNotFoundException, OrgNotFoundException {
        Interest interest = new Interest();
        interest.setUser(UserConverter.toModel(userService.getUserById(dto.userId())));
        interest.setPet(PetConverter.toModel(petService.getPetById(dto.petId())));
        interest.setOrganization(OrgConverter.toModel(organizationService.getOrganizationById(dto.organizationId())));
        interest.setStatus(PENDING);
        interest.setTimestamp(LocalDateTime.now());
        return InterestConverter.toDto(interestRepository.save(interest));
    }

    @Override
    public InterestGetDto updateInterest(String id, InterestUpdateDto dto) throws InterestNotFoundException, InvalidStatusException {
        Interest interest = findById(id);
        Status status = getStatusByDescription(dto.status());
        interest.setStatus(status);
        return InterestConverter.toDto(interestRepository.save(interest));
    }

    @Override
    public String deleteInterest(String id) throws InterestNotFoundException {
        findById(id);
        interestRepository.deleteById(id);
        return INTEREST_WITH_ID + id + DELETE_SUCCESS;
    }

    private Interest findById(String interestId) throws InterestNotFoundException {
        return interestRepository.findById(interestId).orElseThrow(
                () -> new InterestNotFoundException(interestId));
    }
}
