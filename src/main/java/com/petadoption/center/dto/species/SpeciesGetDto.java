package com.petadoption.center.dto.species;

import lombok.Builder;

@Builder
public record SpeciesGetDto(
        String id,
        String name
) {}