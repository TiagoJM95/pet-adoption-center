package com.petadoption.center.dto.breed;

import com.petadoption.center.dto.species.SpeciesGetDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record BreedGetDto(
        @Schema(description = "Breed id", example = "12789-1234-1234-12345")
        String id,
        @Schema(description = "Breed name", example = "Huskey")
        String name,
        @Schema(description = "Specie information", example = "id = 12789-1234-1234-12345, name = Dog", type = "SpeciesGetDto" )
        SpeciesGetDto speciesDto
) {}