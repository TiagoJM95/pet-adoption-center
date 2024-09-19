package com.petadoption.center.dto.interest;

public record InterestCreateDto(
        String userId,
        String petId,
        String organizationId
) {}