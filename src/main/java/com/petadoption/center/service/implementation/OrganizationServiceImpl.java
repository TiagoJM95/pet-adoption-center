package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationPatchDto;
import com.petadoption.center.repository.OrganizationRepository;
import com.petadoption.center.service.OrganizationService;

import java.util.List;

public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }


    @Override
    public List<OrganizationGetDto> getAllOrganizations() {
        return List.of();
    }

    @Override
    public OrganizationGetDto getOrganizationById(Long organizationId) {
        return null;
    }

    @Override
    public OrganizationGetDto addNewOrganization(OrganizationCreateDto organizationCreateDto) {
        return null;
    }

    @Override
    public OrganizationGetDto updateOrganization(Long organizationId, OrganizationPatchDto organizationPatchDto) {
        return null;
    }
}
