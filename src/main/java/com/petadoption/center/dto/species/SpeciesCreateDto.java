package com.petadoption.center.dto.species;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.petadoption.center.util.Messages.*;

public record SpeciesCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        String name
) {
}
