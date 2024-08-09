package com.petadoption.center.service;

import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;

import java.util.List;

public interface OrganizationService {

    List<OrganizationGetDto> getAllOrganizations();

    OrganizationGetDto getOrganizationById(Long id);

    OrganizationGetDto addNewOrganization(OrganizationCreateDto organization);

    OrganizationGetDto updateOrganization(Long id, OrganizationUpdateDto organization);
}
