package com.petadoption.center.dto.adoptionForm;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.*;

@Builder
public record AdoptionFormCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        @Schema(description = "User id", example = "12789-1234-1234-12345")
        String userId,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        @Schema(description = "Pet id", example = "12789-1234-1234-12345")
        String petId,

        @NotNull(message = BLANK_FIELD)
        Family userFamily,

        @NotBlank(message = BLANK_FIELD)
        @Schema(description = "Pet vacation home", example = "Neighbour home")
        String petVacationHome,

        @NotNull(message = BLANK_FIELD)
        @Schema(description = "Is responsible for pet", example = "true")
        Boolean isResponsibleForPet,

        @Schema(description = "Other notes", example = "More additional information")
        String otherNotes,

        @NotNull(message = BLANK_FIELD)
        Address petAddress
) {}