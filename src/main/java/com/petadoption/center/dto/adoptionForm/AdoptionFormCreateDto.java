package com.petadoption.center.dto.adoptionForm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_NUMBERS;

public record AdoptionFormCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        Long userId,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        Long petId
) {
}
