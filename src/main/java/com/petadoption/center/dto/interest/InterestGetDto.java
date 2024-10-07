package com.petadoption.center.dto.interest;

import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.enums.Status;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record InterestGetDto(
        String id,
        UserGetDto userDto,
        PetGetDto petDto,
        OrganizationGetDto organizationDto,
        Status status,
        LocalDateTime timestamp,
        LocalDateTime reviewTimestamp
) {}