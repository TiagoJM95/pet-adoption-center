package com.petadoption.center.dto.breed;

import jakarta.validation.constraints.Pattern;

import static com.petadoption.center.util.Messages.ONLY_LETTERS;

public record BreedUpdateDto(

        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        String name
) {
}
