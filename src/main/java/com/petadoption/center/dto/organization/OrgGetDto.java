package com.petadoption.center.dto.organization;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;

public record OrgGetDto(
        Long id,
        String name,
        String email,
        String phoneNumber,
        Address address,
        String websiteUrl,
        SocialMedia socialMedia
) {}
