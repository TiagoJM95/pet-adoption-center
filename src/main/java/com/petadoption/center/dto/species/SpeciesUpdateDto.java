package com.petadoption.center.dto.species;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.ONLY_LETTERS;
@Builder
public record SpeciesUpdateDto(
        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        String name
) {}