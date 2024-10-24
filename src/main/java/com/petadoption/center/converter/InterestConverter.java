package com.petadoption.center.converter;

import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.model.Interest;

public class InterestConverter {

    public static InterestGetDto toDto (Interest interest) {
        if (interest == null) return null;
        return InterestGetDto.builder()
                .id(interest.getId())
                .userDto(UserConverter.toDto(interest.getUser()))
                .petDto(PetConverter.toDto(interest.getPet()))
                .organizationDto(OrganizationConverter.toDto(interest.getOrganization()))
                .status(interest.getStatus())
                .createdAt(interest.getCreatedAt())
                .reviewTimestamp(interest.getReviewTimestamp())
                .build();
    }
}