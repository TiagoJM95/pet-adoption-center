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
                nipc(dto.nipc()).
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
                nipc(dto.nipc()).
                phoneNumber(dto.phoneNumber()).
                address(dto.address()).
                websiteUrl(dto.websiteUrl()).
                socialMedia(dto.socialMedia()).
                createdAt(dto.createdAt()).
                build();
    }

    public static OrganizationGetDto toDto(Organization org) {
        if (org == null) return null;
        return OrganizationGetDto.builder()
                .id(org.getId())
                .name(org.getName())
                .email(org.getEmail())
                .nipc(org.getNipc())
                .phoneNumber(org.getPhoneNumber())
                .address(org.getAddress())
                .websiteUrl(org.getWebsiteUrl())
                .socialMedia(org.getSocialMedia())
                .createdAt(org.getCreatedAt())
                .build();
    }
}