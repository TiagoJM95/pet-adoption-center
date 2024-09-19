package com.petadoption.center.converter;

import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.model.Interest;
import com.petadoption.center.service.aggregator.InterestCreateContext;
import com.petadoption.center.service.aggregator.InterestGetContext;

public class InterestConverter {

    public static InterestGetDto toDto (Interest interest) {
        return InterestGetDto.builder()
                .id(interest.getId())
                .userDto(UserConverter.toDto(interest.getUser()))
                .petDto(PetConverter.toDto(interest.getPet()))
                .organizationDto(OrgConverter.toDto(interest.getOrganization()))
                .status(interest.getStatus())
                .timestamp(interest.getTimestamp())
                .reviewTimestamp(interest.getReviewTimestamp())
                .build();
    }
}