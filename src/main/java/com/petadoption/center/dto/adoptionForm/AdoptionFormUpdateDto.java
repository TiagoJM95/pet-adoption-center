package com.petadoption.center.dto.adoptionForm;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static com.petadoption.center.util.Messages.BLANK_FIELD;

@Builder
public record AdoptionFormUpdateDto(
        @NotNull(message = BLANK_FIELD)
        Family userFamily,

        @NotBlank(message = BLANK_FIELD)
        @Schema(
                description = "Vacation home for the pet",
                example = "House in the countryside")
        String petVacationHome,

        @NotNull(message = BLANK_FIELD)
        @Schema(
                description = "Is the user responsible for the pet?",
                example = "true")
        Boolean isResponsibleForPet,

        @Schema(
                description = "Any additional notes",
                example = "The pet is a little shy around strangers.")
        String otherNotes,

        @NotNull(message = BLANK_FIELD)
        Address petAddress
) {
}
