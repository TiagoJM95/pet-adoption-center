package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.OrganizationConverter;
import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import com.petadoption.center.exception.organization.OrganizationException;
import com.petadoption.center.exception.organization.OrganizationNotFoundException;
import com.petadoption.center.model.Organization;
import com.petadoption.center.repository.OrganizationRepository;
import com.petadoption.center.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.converter.OrganizationConverter.fromModelToOrganizationGetDto;

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
    public OrganizationGetDto addNewOrganization(OrganizationCreateDto organization) {
        return null;
    }

    @Override
    public OrganizationGetDto updateOrganization(Long id, OrganizationUpdateDto organization) {
        return null;
    }

    private Organization findById(Long id) throws OrganizationNotFoundException {
        return organizationRepository.findById(id).orElseThrow(() -> new OrganizationNotFoundException("id"));
    }
}
