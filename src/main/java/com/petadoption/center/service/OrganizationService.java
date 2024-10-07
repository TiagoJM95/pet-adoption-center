package com.petadoption.center.service;

import com.petadoption.center.converter.OrgConverter;
import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.organization.OrgUpdateDto;
import com.petadoption.center.exception.organization.OrganizationNotFoundException;
import com.petadoption.center.model.Organization;
import com.petadoption.center.repository.OrganizationRepository;
import com.petadoption.center.service.interfaces.OrganizationServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Utils.updateFields;

@Service
public class OrganizationService implements OrganizationServiceI {

    @Autowired
    private OrganizationRepository orgRepository;

    @Override
    public List<OrgGetDto> getAllOrganizations(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        return orgRepository.findAll(pageRequest).stream().map(OrgConverter::toDto).toList();
    }

    @Override
    public OrgGetDto getOrganizationById(String id) throws OrganizationNotFoundException {
        return OrgConverter.toDto(findOrgById(id));
    }

    @Override
    public OrgGetDto addNewOrganization(OrgCreateDto dto) {
        return OrgConverter.toDto(orgRepository.save(OrgConverter.toModel(dto)));
    }

    @Override
    public OrgGetDto updateOrganization(String id, OrgUpdateDto dto) throws OrganizationNotFoundException {
        Organization org = findOrgById(id);
        updateOrgFields(dto, org);
        return OrgConverter.toDto(orgRepository.save(org));
    }

    @Override
    public String deleteOrganization(String id) throws OrganizationNotFoundException {
        findOrgById(id);
        orgRepository.deleteById(id);
        return ORG_WITH_ID + id + DELETE_SUCCESS;
    }

    private Organization findOrgById(String id) throws OrganizationNotFoundException {
        return orgRepository.findById(id).orElseThrow(() -> new OrganizationNotFoundException(ORG_WITH_ID + id + NOT_FOUND));
    }

    private void updateOrgFields(OrgUpdateDto dto, Organization org) {
        updateFields(dto.name(), org.getName(), org::setName);
        updateFields(dto.email(), org.getEmail(), org::setEmail);
        updateFields(dto.phoneNumber(), org.getPhoneNumber(), org::setPhoneNumber);
        updateFields(dto.websiteUrl(), org.getWebsiteUrl(), org::setWebsiteUrl);
        updateFields(dto.socialMedia(), org.getSocialMedia(), org::setSocialMedia);
        updateFields(dto.address(), org.getAddress(), org::setAddress);
    }
}