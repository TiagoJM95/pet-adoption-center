package com.petadoption.center.dto.breed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_LETTERS;

public record BreedCreateDto(
        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
        String name,

        @NotNull
        String speciesId
) {}