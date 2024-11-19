package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrganizationServiceI {
    List<OrganizationGetDto> getAll(Pageable pageable);
    OrganizationGetDto getById(String id);
    OrganizationGetDto create(OrganizationCreateDto dto);
    OrganizationGetDto update(String id, OrganizationUpdateDto dto);
    String delete(String id);
}