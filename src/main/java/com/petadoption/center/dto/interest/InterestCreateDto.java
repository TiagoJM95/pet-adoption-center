package com.petadoption.center.dto.interest;

import lombok.Builder;

@Builder
public record InterestCreateDto(
        String userId,
        String petId,
        String organizationId
) {}