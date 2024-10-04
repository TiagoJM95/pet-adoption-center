package com.petadoption.center.dto.pet;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.embeddable.Attributes;
import com.petadoption.center.validator.EnumValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.*;

@Builder
public record PetCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        String name,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String petSpeciesId,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String primaryBreedId,

        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String secondaryBreedId,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String primaryColor,

        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String secondaryColor,

        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String tertiaryColor,

        @EnumValidator(enumClass = Genders.class, message = GENDER_INVALID)
        String gender,

        @EnumValidator(enumClass = Coats.class, message = COAT_INVALID)
        String coat,

        @EnumValidator(enumClass = Sizes.class, message = SIZE_INVALID)
        String size,

        @EnumValidator(enumClass = Ages.class, message = AGE_INVALID)
        String age,

        @NotBlank(message = BLANK_FIELD)
        String description,

        @NotBlank(message = BLANK_FIELD)
        String imageUrl,

        Boolean isAdopted,

        Attributes attributes,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String organizationId
) {}