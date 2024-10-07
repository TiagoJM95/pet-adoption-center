package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.exception.not_found.InterestNotFoundException;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;
import com.petadoption.center.exception.not_found.PetNotFoundException;
import com.petadoption.center.exception.not_found.InvalidStatusChangeException;
import com.petadoption.center.exception.not_found.UserNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InterestServiceI {
    List<InterestGetDto> getCurrentByOrganizationId(Pageable pageable, String organizationId);
    List<InterestGetDto> getHistoryByOrganizationId(Pageable pageable, String organizationId);
    List<InterestGetDto> getCurrentByUserId(Pageable pageable, String userId);
    List<InterestGetDto> getHistoryByUserId(Pageable pageable, String userId);
    InterestGetDto getById(String id);
    InterestGetDto create(InterestCreateDto dto);
    InterestGetDto update(String id, InterestUpdateDto dto);
    String delete(String id);
}