package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.OrganizationConverter;
import com.petadoption.center.converter.PetConverter;
import com.petadoption.center.converter.SpeciesConverter;
import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.model.Organization;
import com.petadoption.center.repository.OrganizationRepository;
import com.petadoption.center.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<OrganizationGetDto> getAllOrganizations() {
        List <Organization> organizations = organizationRepository.findAll();
        return OrganizationConverter.fromModelToOrganizationGetDto(organizations, );
    }

    @Override
    public OrganizationGetDto getOrganizationById(Long id) {
        return null;
    }

    @Override
    public OrganizationGetDto addNewOrganization(OrganizationCreateDto organization) {
        return null;
    }

    @Override
    public OrganizationGetDto updateOrganization(Long id, OrganizationUpdateDto organization) {
        return null;
    }
}
