package com.petadoption.center.dto.pet;

import com.petadoption.center.model.embeddable.Attributes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_NUMBERS;
@Builder
public record PetUpdateDto(

        @NotBlank(message = BLANK_FIELD)
        String size,

        @NotBlank(message = BLANK_FIELD)
        String age,

        @NotBlank(message = BLANK_FIELD)
        String description,

        @NotBlank(message = BLANK_FIELD)
        String imageUrl,

        boolean isAdopted,

        Attributes attributes,

        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        String organizationId
) {}