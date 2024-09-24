package com.petadoption.center.dto.color;

import lombok.Builder;

@Builder
public record ColorGetDto(
        String id,
        String name
) {}