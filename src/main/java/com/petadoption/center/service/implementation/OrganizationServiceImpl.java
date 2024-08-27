package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.OrganizationConverter;
import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import com.petadoption.center.exception.organization.*;
import com.petadoption.center.exception.user.UserEmailDuplicateException;
import com.petadoption.center.exception.user.UserPhoneNumberDuplicateException;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import com.petadoption.center.repository.OrganizationRepository;
import com.petadoption.center.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.petadoption.center.converter.OrganizationConverter.fromModelToOrganizationGetDto;
import static com.petadoption.center.converter.OrganizationConverter.fromOrganizationCreateDtoToModel;
import static com.petadoption.center.util.FieldUpdater.updateIfChanged;
import static com.petadoption.center.util.FieldUpdater.updateIfChangedCheckDuplicates;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<OrganizationGetDto> getAllOrganizations() {
        return organizationRepository.findAll().stream().map(OrganizationConverter::fromModelToOrganizationGetDto).toList();
    }

    @Override
    public OrganizationGetDto getOrganizationById(Long id) throws OrganizationNotFoundException {
        return fromModelToOrganizationGetDto(findById(id));
    }

    @Override
    public OrganizationGetDto addNewOrganization(OrganizationCreateDto organization) throws OrganizationDuplicateSocialMediaException, OrganizationDuplicatePhoneNumberException, OrganizationDuplicateAddressException, OrganizationDuplicateWebsiteException, OrganizationDuplicateEmailException {
        checkIfOrganizationExists(organization);
        return fromModelToOrganizationGetDto(organizationRepository.save(fromOrganizationCreateDtoToModel(organization)));
    }

    @Override
    public OrganizationGetDto updateOrganization(Long id, OrganizationUpdateDto organization) throws OrganizationNotFoundException, OrganizationDuplicateSocialMediaException, OrganizationDuplicatePhoneNumberException, OrganizationDuplicateAddressException, OrganizationDuplicateWebsiteException, OrganizationDuplicateEmailException, UserEmailDuplicateException, UserPhoneNumberDuplicateException {
        Organization organizationToUpdate = findById(id);
        updateWhenCheckedForDuplicates(organization, organizationToUpdate);
        return fromModelToOrganizationGetDto(organizationRepository.save(organizationToUpdate));
    }

    private void updateWhenCheckedForDuplicates(OrganizationUpdateDto organization, Organization organizationToUpdate) throws OrganizationDuplicateEmailException, OrganizationDuplicatePhoneNumberException, OrganizationDuplicateAddressException, OrganizationDuplicateWebsiteException, OrganizationDuplicateSocialMediaException, UserEmailDuplicateException, UserPhoneNumberDuplicateException {
        updateIfChangedCheckDuplicates(organization::email, organizationToUpdate::getEmail, organizationToUpdate::setEmail, checkIfOrganizationExistsByEmail(organization.email()));
        updateIfChangedCheckDuplicates(organization::phoneNumber, organizationToUpdate::getPhoneNumber, organizationToUpdate::setPhoneNumber,  checkIfOrganizationExistsByPhoneNumber(organization.phoneNumber()));
        updateIfChangedCheckDuplicates(() -> new Address(organization.street(), organization.city(),organization.state(), organization.postalCode()), organizationToUpdate::getAddress, organizationToUpdate::setAddress, checkIfOrganizationExistsByAddress(organization.street(), organization.postalCode()));
        updateIfChangedCheckDuplicates(organization::websiteUrl, organizationToUpdate::getWebsiteUrl, organizationToUpdate::setWebsiteUrl,  checkIfOrganizationExistsByWebsiteUrl(organization.websiteUrl()));
        updateIfChangedCheckDuplicates(() -> new SocialMedia(organization.facebook(), organization.instagram(), organization.twitter(), organization.youtube()),organizationToUpdate::getSocialMedia, organizationToUpdate::setSocialMedia, checkIfOrganizationExistsBySocialMedia(organization.facebook(), organization.instagram(), organization.twitter(), organization.youtube()));
        updateIfChanged(organization::name, organizationToUpdate::getName, organizationToUpdate::setName);
    }

    private void checkIfOrganizationExists(OrganizationDto organization) throws OrganizationDuplicateEmailException, OrganizationDuplicatePhoneNumberException, OrganizationDuplicateAddressException, OrganizationDuplicateWebsiteException, OrganizationDuplicateSocialMediaException {
        checkIfOrganizationExistsByEmail(organization.email());
        checkIfOrganizationExistsByPhoneNumber(organization.phoneNumber());
        checkIfOrganizationExistsByAddress(organization.street(), organization.postalCode());
        checkIfOrganizationExistsByWebsiteUrl(organization.websiteUrl());
        checkIfOrganizationExistsBySocialMedia(organization.facebook(), organization.instagram(), organization.twitter(), organization.youtube());
    }


    private Organization findById(Long id) throws OrganizationNotFoundException {
        return organizationRepository.findById(id).orElseThrow(() -> new OrganizationNotFoundException("id"));
    }

    private Runnable checkIfOrganizationExistsByEmail(String email) throws OrganizationDuplicateEmailException {
        if(organizationRepository.findByEmail(email).isPresent()) {
            throw new OrganizationDuplicateEmailException("Email already exists");
        }
        return null;
    }

    private Runnable checkIfOrganizationExistsByPhoneNumber(String phoneNumber) throws OrganizationDuplicatePhoneNumberException {
        if(organizationRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new OrganizationDuplicatePhoneNumberException("Phone number already exists");
        }
        return null;
    }

    private Runnable checkIfOrganizationExistsByAddress(String street, String postalCode) throws OrganizationDuplicateAddressException {
        if (organizationRepository.findByAddress_StreetAndAddress_PostalCode(street, postalCode).isPresent()) {
            throw new OrganizationDuplicateAddressException("Address already exists");
        }
        return null;
    }

    private Runnable checkIfOrganizationExistsByWebsiteUrl(String websiteUrl) throws OrganizationDuplicateWebsiteException {
        if(organizationRepository.findByWebsiteUrl(websiteUrl).isPresent()) {
            throw new OrganizationDuplicateWebsiteException("Website already exists");
        }
        return null;
    }

    private Runnable checkIfOrganizationExistsBySocialMedia(String facebook, String instagram, String twitter, String youtube) throws OrganizationDuplicateSocialMediaException {
        if(organizationRepository.findBySocialMedia_Facebook(facebook).isPresent()) {
            throw new OrganizationDuplicateSocialMediaException("Facebook already exists");
        }
        if(organizationRepository.findBySocialMedia_Instagram(instagram).isPresent()) {
            throw new OrganizationDuplicateSocialMediaException("Instagram already exists");
        }
        if(organizationRepository.findBySocialMedia_Twitter(twitter).isPresent()) {
            throw new OrganizationDuplicateSocialMediaException("Twitter already exists");
        }
        if(organizationRepository.findBySocialMedia_Youtube(youtube).isPresent()) {
            throw new OrganizationDuplicateSocialMediaException("Youtube already exists");
        }
        return null;
    }







}
