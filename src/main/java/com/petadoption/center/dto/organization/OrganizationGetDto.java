package com.petadoption.center.dto.organization;

import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;

import java.util.List;

public record OrganizationGetDto(

        Long id,

        String name,

        String email,

        String phoneNumber,

        Address address,

        String websiteUrl,

        SocialMedia socialMedia
) {
}
