package com.petadoption.center.converter;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;

public class OrgConverter {

    public static Organization toModel(OrgCreateDto org) {
        Address address = new Address(
                org.street(),
                org.city(),
                org.state(),
                org.postalCode()
        );

        SocialMedia socialMedia = new SocialMedia(
                org.facebook(),
                org.instagram(),
                org.twitter(),
                org.youtube()
                );

        return Organization.builder().
                name(org.name()).
                email(org.email()).
                phoneNumber(org.phoneNumber()).
                address(address).
                websiteUrl(org.websiteUrl()).
                socialMedia(socialMedia).
                build();
    }

    public static OrgGetDto toDto(Organization org) {
        return new OrgGetDto(
                org.getId(),
                org.getName(),
                org.getEmail(),
                org.getPhoneNumber(),
                org.getAddress(),
                org.getWebsiteUrl(),
                org.getSocialMedia()
        );
    }
}
