package com.petadoption.center.dto.species;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SpeciesGetDto(
        String id,
        String name,
        LocalDateTime createdAt
) {}