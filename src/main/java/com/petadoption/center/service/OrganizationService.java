package com.petadoption.center.service;

import com.petadoption.center.converter.OrganizationConverter;
import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;
import com.petadoption.center.model.Organization;
import com.petadoption.center.repository.OrganizationRepository;
import com.petadoption.center.service.interfaces.OrganizationServiceI;
import com.petadoption.center.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.util.Messages.ORG_DELETE_MESSAGE;
import static com.petadoption.center.util.Messages.ORG_NOT_FOUND;
import static java.lang.String.format;

@Service
@CacheConfig(cacheNames = "organization")
public class OrganizationService implements OrganizationServiceI {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    @Cacheable
    public List<OrganizationGetDto> getAll(Pageable pageable) {
        return organizationRepository.findAll(pageable).stream().map(OrganizationConverter::toDto).toList();
    }

    @Override
    @Cacheable(key = "#id")
    public OrganizationGetDto getById(String id) {
        return OrganizationConverter.toDto(findById(id));
    }

    @Override
    public OrganizationGetDto create(OrganizationCreateDto dto) {
        return OrganizationConverter.toDto(organizationRepository.save(OrganizationConverter.toModel(dto)));
    }

    @Override
    @CachePut(key = "#id")
    public OrganizationGetDto update(String id, OrganizationUpdateDto dto) {
        Organization org = findById(id);
        updateFields(dto,org);
        return OrganizationConverter.toDto(organizationRepository.save(org));
    }

    @Override
    @CacheEvict(key = "#id")
    public String delete(String id) {
        findById(id);
        organizationRepository.deleteById(id);
        return format(ORG_DELETE_MESSAGE, id);
    }

    private Organization findById(String id) {
        return organizationRepository.findById(id).orElseThrow(
                () -> new OrganizationNotFoundException(format(ORG_NOT_FOUND, id)));
    }

    private void updateFields(OrganizationUpdateDto dto, Organization organization) {
        Utils.updateFields(dto.name(), organization.getName(), organization::setName);
        Utils.updateFields(dto.email(), organization.getEmail(), organization::setEmail);
        Utils.updateFields(dto.phoneNumber(), organization.getPhoneNumber(), organization::setPhoneNumber);
        Utils.updateFields(dto.websiteUrl(), organization.getWebsiteUrl(), organization::setWebsiteUrl);
        Utils.updateFields(dto.socialMedia(), organization.getSocialMedia(), organization::setSocialMedia);
        Utils.updateFields(dto.address(), organization.getAddress(), organization::setAddress);
    }
}