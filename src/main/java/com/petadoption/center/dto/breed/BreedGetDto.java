package com.petadoption.center.dto.breed;

import com.petadoption.center.dto.species.SpeciesGetDto;

public record BreedGetDto(
        String id,
        String name,
        SpeciesGetDto speciesDto
) {}