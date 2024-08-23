package com.petadoption.center.service;

import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import com.petadoption.center.exception.organization.OrganizationNotFoundException;

import java.util.List;

public interface OrganizationService {

    List<OrganizationGetDto> getAllOrganizations();

    OrganizationGetDto getOrganizationById(Long id) throws OrganizationNotFoundException;

    OrganizationGetDto addNewOrganization(OrganizationCreateDto organization);

    OrganizationGetDto updateOrganization(Long id, OrganizationUpdateDto organization);
}
