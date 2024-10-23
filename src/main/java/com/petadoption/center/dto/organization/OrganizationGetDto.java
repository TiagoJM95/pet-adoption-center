package com.petadoption.center.dto.organization;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record OrganizationGetDto(
        String id,
        String name,
        String email,
        String nipc,
        String phoneNumber,
        Address address,
        String websiteUrl,
        SocialMedia socialMedia,
        LocalDateTime createdAt
) implements Serializable {}