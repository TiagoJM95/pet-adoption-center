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
import java.util.Optional;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Utils.*;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository orgRepository;

    @Override
    public List<OrgGetDto> getAllOrganizations(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        return orgRepository.findAll(pageRequest).stream().map(OrgConverter::toDto).toList();
    }

    @Override
    public OrgGetDto getOrganizationById(Long id) throws OrgNotFoundException {
        return OrgConverter.toDto(findOrgById(id));
    }

    @Override
    public OrgGetDto addNewOrganization(OrgCreateDto dto) {
        return OrgConverter.toDto(orgRepository.save(OrgConverter.toModel(dto)));
    }

    @Override
    public OrgGetDto updateOrganization(Long id, OrgUpdateDto dto) throws OrgNotFoundException {
        Organization org = findOrgById(id);
        updateOrgFields(dto, org);
        return OrgConverter.toDto(orgRepository.save(org));
    }

    @Override
    public String deleteOrganization(Long id) throws OrgNotFoundException {
        findOrgById(id);
        orgRepository.deleteById(id);
        return ORG_WITH_ID + id + DELETE_SUCCESS;
    }

    private Organization findOrgById(Long id) throws OrgNotFoundException {
        return orgRepository.findById(id).orElseThrow(() -> new OrgNotFoundException(id));
    }

    private void updateOrgFields(OrgUpdateDto dto, Organization org) {
        updateFields(dto.name(), org.getName(), org::setName);
        updateFields(dto.email(), org.getEmail(), org::setEmail);
        updateFields(dto.phoneNumber(), org.getPhoneNumber(), org::setPhoneNumber);
        updateFields(dto.websiteUrl(), org.getWebsiteUrl(), org::setWebsiteUrl);
        updateFields(createSocialMedia(dto), org.getSocialMedia(), org::setSocialMedia);
        updateFields(createAddress(dto), org.getAddress(), org::setAddress);
    }

    private Address createAddress(OrgDto dto) {
        return new Address(dto.street(), dto.city(), dto.state(), dto.postalCode());
    }

    private SocialMedia createSocialMedia(OrgDto dto) {
        return new SocialMedia(dto.facebook(), dto.instagram(), dto.twitter(), dto.youtube());
    }
}
