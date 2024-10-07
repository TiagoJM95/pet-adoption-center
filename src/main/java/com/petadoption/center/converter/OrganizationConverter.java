package com.petadoption.center.converter;

import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.model.Organization;


public class OrganizationConverter {

    public static Organization toModel(OrganizationCreateDto dto) {
        if (dto == null) return null;
        return Organization.builder().
                name(dto.name()).
                email(dto.email()).
                nif(dto.nif()).
                phoneNumber(dto.phoneNumber()).
                address(dto.address()).
                websiteUrl(dto.websiteUrl()).
                socialMedia(dto.socialMedia()).
                build();
    }

    public static Organization toModel(OrganizationGetDto dto) {
        if (dto == null) return null;
        return Organization.builder().
                id(dto.id()).
                name(dto.name()).
                email(dto.email()).
                nif(dto.nif()).
                phoneNumber(dto.phoneNumber()).
                address(dto.address()).
                websiteUrl(dto.websiteUrl()).
                socialMedia(dto.socialMedia()).
                build();
    }

    public static OrganizationGetDto toDto(Organization org) {
        if (org == null) return null;
        return new OrganizationGetDto(
                org.getId(),
                org.getName(),
                org.getEmail(),
                org.getNif(),
                org.getPhoneNumber(),
                org.getAddress(),
                org.getWebsiteUrl(),
                org.getSocialMedia()
        );
    }
}