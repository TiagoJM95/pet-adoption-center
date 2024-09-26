package com.petadoption.center.dto.interest;

import lombok.Builder;

@Builder
public record InterestUpdateDto(
        String status
) {}