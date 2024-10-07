package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.organization.OrgUpdateDto;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;

import java.util.List;

public interface OrganizationServiceI {
    List<OrgGetDto> getAllOrganizations(int page, int size, String sortBy);
    OrgGetDto getOrganizationById(String id) throws OrganizationNotFoundException;
    OrgGetDto addNewOrganization(OrgCreateDto organization);
    OrgGetDto updateOrganization(String id, OrgUpdateDto organization) throws OrganizationNotFoundException;
    String deleteOrganization(String id) throws OrganizationNotFoundException;
}