package com.petadoption.center.dto.pet;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.embeddable.Attributes;
import com.petadoption.center.validator.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Messages.INVALID_AGE;

@Builder
public record PetUpdateDto(

        @EnumValidator(enumClass = Sizes.class, message = SIZE_INVALID)
        String size,

        @EnumValidator(enumClass = Ages.class, message = AGE_INVALID)
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