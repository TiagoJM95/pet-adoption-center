package com.petadoption.center.converter;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.model.Organization;

import static com.petadoption.center.util.factory.AddressFactory.createAddress;
import static com.petadoption.center.util.factory.SocialMediaFactory.createSocialMedia;

public class OrgConverter {

    public static Organization toModel(OrgCreateDto dto) {
        if (dto == null) return null;
        return Organization.builder().
                name(dto.name()).
                email(dto.email()).
                nif(dto.nif()).
                phoneNumber(dto.phoneNumber()).
                address(createAddress(dto)).
                websiteUrl(dto.websiteUrl()).
                socialMedia(createSocialMedia(dto)).
                build();
    }

    public static Organization toModel(OrgGetDto dto) {
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

    public static OrgGetDto toDto(Organization org) {
        if (org == null) return null;
        return new OrgGetDto(
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