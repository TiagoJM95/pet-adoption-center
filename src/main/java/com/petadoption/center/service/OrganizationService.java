package com.petadoption.center.service;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.organization.OrgUpdateDto;
import com.petadoption.center.exception.organization.*;
import com.petadoption.center.model.Organization;

import java.util.List;

public interface OrganizationService {

    List<OrgGetDto> getAllOrganizations(int page, int size, String sortBy);

    OrgGetDto getOrganizationById(Long id) throws OrgNotFoundException;

    OrgGetDto addNewOrganization(OrgCreateDto organization) throws OrganizationDuplicateException;

    OrgGetDto updateOrganization(Long id, OrgUpdateDto organization) throws OrgNotFoundException, OrganizationDuplicateException;

    String deleteOrganization(Long id) throws OrgNotFoundException;
}
