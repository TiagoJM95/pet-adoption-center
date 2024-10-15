package com.petadoption.center.dto.breed;

import com.petadoption.center.dto.species.SpeciesGetDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BreedGetDto(
        String id,
        String name,
        SpeciesGetDto speciesDto,
        LocalDateTime createdAt
) {}