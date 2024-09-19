package com.petadoption.center.dto.adoptionForm;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import jakarta.validation.constraints.NotBlank;

import static com.petadoption.center.util.Messages.BLANK_FIELD;

public record AdoptionFormUpdateDto(
        @NotBlank(message = BLANK_FIELD)
        Family userFamily,

        @NotBlank(message = BLANK_FIELD)
        String petVacationHome,

        @NotBlank(message = BLANK_FIELD)
        Boolean isResponsibleForPet,

        String otherNotes,

        @NotBlank(message = BLANK_FIELD)
        Address petAddress
) {
}
