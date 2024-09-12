package com.petadoption.center.dto.breed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static com.petadoption.center.util.Messages.*;

public record BreedCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
        String name,

        @NotNull
        Long specieId
) {}