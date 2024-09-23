package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.exception.status.InvalidStatusChangeException;
import com.petadoption.center.exception.status.InvalidStatusException;
import com.petadoption.center.exception.interest.InterestNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.user.UserNotFoundException;

import java.util.List;

public interface InterestServiceI {
    List<InterestGetDto> getCurrentInterestsInOrganizationPets(int page, int size, String sortBy, String organizationId) throws OrgNotFoundException;

    List<InterestGetDto> getInterestHistoryInOrganizationPets(int page, int size, String sortBy, String organizationId) throws OrgNotFoundException;

    List<InterestGetDto> getCurrentUserInterests(int page, int size, String sortBy, String userId) throws UserNotFoundException;

    List<InterestGetDto> getUserInterestHistory(int page, int size, String sortBy, String userId) throws UserNotFoundException;

    InterestGetDto getInterestById(String id) throws InterestNotFoundException;

    InterestGetDto addNewInterest(InterestCreateDto dto) throws UserNotFoundException, PetNotFoundException, OrgNotFoundException;

    InterestGetDto updateInterest(String id, InterestUpdateDto dto) throws InterestNotFoundException, InvalidStatusException, InvalidStatusChangeException, UserNotFoundException, PetNotFoundException;

    String deleteInterest(String id) throws InterestNotFoundException;
}