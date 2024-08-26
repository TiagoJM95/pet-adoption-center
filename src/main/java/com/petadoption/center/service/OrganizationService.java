package com.petadoption.center.service;

import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import com.petadoption.center.exception.organization.*;
import com.petadoption.center.exception.user.UserEmailDuplicateException;
import com.petadoption.center.exception.user.UserPhoneNumberDuplicateException;

import java.util.List;

public interface OrganizationService {

    List<OrganizationGetDto> getAllOrganizations();

    OrganizationGetDto getOrganizationById(Long id) throws OrganizationNotFoundException;

    OrganizationGetDto addNewOrganization(OrganizationCreateDto organization) throws OrganizationDuplicateSocialMediaException, OrganizationDuplicatePhoneNumberException, OrganizationDuplicateAddressException, OrganizationDuplicateWebsiteException, OrganizationDuplicateEmailException;

    OrganizationGetDto updateOrganization(Long id, OrganizationUpdateDto organization) throws OrganizationNotFoundException, OrganizationDuplicateSocialMediaException, OrganizationDuplicatePhoneNumberException, OrganizationDuplicateAddressException, OrganizationDuplicateWebsiteException, OrganizationDuplicateEmailException, UserEmailDuplicateException, UserPhoneNumberDuplicateException;
}
