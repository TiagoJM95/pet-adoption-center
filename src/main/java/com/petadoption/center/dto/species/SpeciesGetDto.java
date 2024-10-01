package com.petadoption.center.dto.species;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record SpeciesGetDto(
        @Schema(description = "Species id", example = "12789-1234-1234-12345")
        String id,

        @Schema(description = "Specie name", example = "Dog")
        String name
) {}