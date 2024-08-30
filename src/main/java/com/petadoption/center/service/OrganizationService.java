package com.petadoption.center.service;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.organization.OrgUpdateDto;
import com.petadoption.center.exception.organization.*;

import java.util.List;

public interface OrganizationService {

    List<OrgGetDto> getAllOrganizations(int page, int size, String sortBy);

    OrgGetDto getOrganizationById(Long id) throws OrgNotFoundException;

    OrgGetDto addNewOrganization(OrgCreateDto organization) throws OrgDuplicateSocialMediaException, OrgDuplicatePhoneNumberException, OrgDuplicateAddressException, OrgDuplicateWebsiteException, OrgDuplicateEmailException;

    OrgGetDto updateOrganization(Long id, OrgUpdateDto organization) throws OrgNotFoundException, OrgDuplicateSocialMediaException, OrgDuplicatePhoneNumberException, OrgDuplicateAddressException, OrgDuplicateWebsiteException, OrgDuplicateEmailException;
}
