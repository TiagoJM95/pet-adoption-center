package com.petadoption.center.dto.adoptionForm;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_UUID;

@Builder(toBuilder = true)
public record AdoptionFormCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @UUID(message = ONLY_UUID)
        String userId,

        @NotBlank(message = BLANK_FIELD)
        @UUID(message = ONLY_UUID)
        String petId,

        @NotNull(message = BLANK_FIELD)
        Family userFamily,

        @NotBlank(message = BLANK_FIELD)
        String petVacationHome,

        @NotNull(message = BLANK_FIELD)
        Boolean isResponsibleForPet,

        String otherNotes,

        @NotNull(message = BLANK_FIELD)
        Address petAddress
) {}