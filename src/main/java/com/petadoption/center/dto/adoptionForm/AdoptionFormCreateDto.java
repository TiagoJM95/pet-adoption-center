package com.petadoption.center.dto.adoptionForm;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_NUMBERS;

@Builder
public record AdoptionFormCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        String userId,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        String petId,

        @NotBlank(message = BLANK_FIELD)
        Family userFamily,

        @NotBlank(message = BLANK_FIELD)
        String petVacationHome,

        @NotBlank(message = BLANK_FIELD)
        Boolean isResponsibleForPet,

        String otherNotes,

        @NotBlank(message = BLANK_FIELD)
        Address petAddress
) {}