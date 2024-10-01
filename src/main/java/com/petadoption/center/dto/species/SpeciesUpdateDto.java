package com.petadoption.center.dto.species;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.ONLY_LETTERS;
@Builder
public record SpeciesUpdateDto(
        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        @Schema(description = "Specie name", example = "Cat")
        String name
) {}