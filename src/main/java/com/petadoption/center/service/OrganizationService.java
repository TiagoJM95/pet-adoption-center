package com.petadoption.center.service;

import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationPatchDto;

import java.util.List;

public interface OrganizationService {

    List<OrganizationGetDto> getAllOrganizations();

    OrganizationGetDto getOrganizationById(Long organizationId);

    OrganizationGetDto addNewOrganization(OrganizationCreateDto organizationCreateDto);

    OrganizationGetDto updateOrganization(Long organizationId, OrganizationPatchDto organizationPatchDto);
}
