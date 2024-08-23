package com.petadoption.center.converter;

import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;

import java.util.List;

public class OrganizationConverter {

    public static Organization fromOrganizationCreateDtoToModel(OrganizationCreateDto organization) {
        Address address = new Address(
                organization.street(),
                organization.city(),
                organization.state(),
                organization.postalCode()
        );

        SocialMedia socialMedia = new SocialMedia(
                organization.facebook(),
                organization.instagram(),
                organization.twitter(),
                organization.youtube()
                );

        return Organization.builder().
                name(organization.name()).
                email(organization.email()).
                phoneNumber(organization.phoneNumber()).
                address(address).
                websiteUrl(organization.websiteUrl()).
                socialMedia(socialMedia).
                build();
    }

    public static OrganizationGetDto fromModelToOrganizationGetDto(Organization organization) {
        return new OrganizationGetDto(
                organization.getId(),
                organization.getName(),
                organization.getEmail(),
                organization.getPhoneNumber(),
                organization.getAddress(),
                organization.getWebsiteUrl(),
                organization.getSocialMedia()
        );
    }
}
