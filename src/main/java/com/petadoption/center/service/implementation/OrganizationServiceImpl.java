package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.OrgConverter;
import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.organization.OrgUpdateDto;
import com.petadoption.center.exception.organization.*;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import com.petadoption.center.repository.OrganizationRepository;
import com.petadoption.center.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.converter.OrgConverter.fromModelToOrgGetDto;
import static com.petadoption.center.converter.OrgConverter.fromOrgCreateDtoToModel;
import static com.petadoption.center.util.FieldUpdater.updateIfChanged;
import static com.petadoption.center.util.Messages.*;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository orgRepository;

    @Override
    public Organization findOrgById(Long id) throws OrgNotFoundException {
        return orgRepository.findById(id).orElseThrow(() -> new OrgNotFoundException(id));
    }

    @Override
    public List<OrgGetDto> getAllOrganizations(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        return orgRepository.findAll(pageRequest).stream().map(OrgConverter::fromModelToOrgGetDto).toList();
    }

    @Override
    public OrgGetDto getOrganizationById(Long id) throws OrgNotFoundException {
        return fromModelToOrgGetDto(findOrgById(id));
    }

    @Override
    public OrgGetDto addNewOrganization(OrgCreateDto org) throws OrgDuplicateSocialMediaException, OrgDuplicatePhoneNumberException, OrgDuplicateAddressException, OrgDuplicateWebsiteException, OrgDuplicateEmailException {
        checkOrgDuplicatesOrExists(org, null);
        return fromModelToOrgGetDto(orgRepository.save(fromOrgCreateDtoToModel(org)));
    }

    @Override
    public OrgGetDto updateOrganization(Long id, OrgUpdateDto org) throws OrgNotFoundException, OrgDuplicateSocialMediaException, OrgDuplicatePhoneNumberException, OrgDuplicateAddressException, OrgDuplicateWebsiteException, OrgDuplicateEmailException {
        Organization orgToUpdate = findOrgById(id);
        checkOrgDuplicatesOrExists(org, orgToUpdate);
        updateOrgFields(org, orgToUpdate);
        return fromModelToOrgGetDto(orgRepository.save(orgToUpdate));
    }

    @Override
    public String deleteOrganization(Long id) throws OrgNotFoundException {
        findOrgById(id);
        orgRepository.deleteById(id);
        return ORG_WITH_ID + id + DELETE_SUCCESS;
    }

    private void checkOrgDuplicatesOrExists(OrgDto org, Organization orgToUpdate) throws OrgDuplicateEmailException, OrgDuplicatePhoneNumberException, OrgDuplicateAddressException, OrgDuplicateWebsiteException, OrgDuplicateSocialMediaException {

        if (orgToUpdate == null || !org.email().equals(orgToUpdate.getEmail())) {
            checkIfOrgExistsByEmail(org.email());
        }
        if (orgToUpdate == null || !org.phoneNumber().equals(orgToUpdate.getPhoneNumber())) {
            checkIfOrgExistsByPhoneNumber(org.phoneNumber());
        }
        if (orgToUpdate == null || !org.street().equals(orgToUpdate.getAddress().getStreet()) &&
                !org.postalCode().equals(orgToUpdate.getAddress().getPostalCode())) {

            checkIfOrgExistsByAddress(org.street(), org.postalCode());
        }
        if (orgToUpdate == null || !org.websiteUrl().equals(orgToUpdate.getWebsiteUrl())) {
            checkIfOrgExistsByWebsiteUrl(org.websiteUrl());
        }
        if (orgToUpdate == null ||
                !org.facebook().equals(orgToUpdate.getSocialMedia().getFacebook()) ||
                !org.instagram().equals(orgToUpdate.getSocialMedia().getInstagram()) ||
                !org.twitter().equals(orgToUpdate.getSocialMedia().getTwitter()) ||
                !org.youtube().equals(orgToUpdate.getSocialMedia().getYoutube())) {

            checkIfOrgExistsBySocialMedia(org.facebook(), org.instagram(), org.twitter(), org.youtube());
        }
    }

    private void updateOrgFields(OrgUpdateDto org, Organization orgToUpdate) {
        updateIfChanged(org::name, orgToUpdate::getName, orgToUpdate::setName);
        updateIfChanged(org::email, orgToUpdate::getEmail, orgToUpdate::setEmail);
        updateIfChanged(org::phoneNumber, orgToUpdate::getPhoneNumber, orgToUpdate::setPhoneNumber);
        updateIfChanged(org::websiteUrl, orgToUpdate::getWebsiteUrl, orgToUpdate::setWebsiteUrl);
        updateIfChanged(() -> new SocialMedia(org.facebook(), org.instagram(), org.twitter(), org.youtube()),
                orgToUpdate::getSocialMedia, orgToUpdate::setSocialMedia);
        updateIfChanged(() -> new Address(org.street(), org.city(), org.state(), org.postalCode()),
                orgToUpdate::getAddress, orgToUpdate::setAddress);
    }

    private void checkIfOrgExistsByEmail(String email) throws OrgDuplicateEmailException {
        if(orgRepository.findByEmail(email).isPresent()) {
            throw new OrgDuplicateEmailException(email);
        }
    }

    private void checkIfOrgExistsByPhoneNumber(String phoneNumber) throws OrgDuplicatePhoneNumberException {
        if(orgRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new OrgDuplicatePhoneNumberException(phoneNumber);
        }
    }

    private void checkIfOrgExistsByAddress(String street, String postalCode) throws OrgDuplicateAddressException {
        if (orgRepository.findByAddress_StreetAndAddress_PostalCode(street, postalCode).isPresent()) {
            throw new OrgDuplicateAddressException(street, postalCode);
        }
    }

    private void checkIfOrgExistsByWebsiteUrl(String websiteUrl) throws OrgDuplicateWebsiteException {
        if(orgRepository.findByWebsiteUrl(websiteUrl).isPresent()) {
            throw new OrgDuplicateWebsiteException(websiteUrl);
        }
    }

    private void checkIfOrgExistsBySocialMedia(String facebook, String instagram, String twitter, String youtube) throws OrgDuplicateSocialMediaException {
        if(orgRepository.findBySocialMedia_Facebook(facebook).isPresent()) {
            throw new OrgDuplicateSocialMediaException(FACEBOOK, facebook);
        }
        if(orgRepository.findBySocialMedia_Instagram(instagram).isPresent()) {
            throw new OrgDuplicateSocialMediaException(INSTAGRAM, instagram);
        }
        if(orgRepository.findBySocialMedia_Twitter(twitter).isPresent()) {
            throw new OrgDuplicateSocialMediaException(TWITTER, twitter);
        }
        if(orgRepository.findBySocialMedia_Youtube(youtube).isPresent()) {
            throw new OrgDuplicateSocialMediaException(YOUTUBE, youtube);
        }
    }

}
