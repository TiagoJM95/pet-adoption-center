package com.petadoption.center.dto.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.petadoption.center.util.Messages.*;

public record PetCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        String name,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        Long petSpeciesId,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        Long primaryBreedId,

        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        Long secondaryBreedId,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        Long primaryColor,

        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        Long secondaryColor,

        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        Long tertiaryColor,

        @NotBlank(message = BLANK_FIELD)
        String gender,

        @NotBlank(message = BLANK_FIELD)
        String coat,

        @NotBlank(message = BLANK_FIELD)
        String size,

        @NotBlank(message = BLANK_FIELD)
        String age,

        @NotBlank(message = BLANK_FIELD)
        String description,

        @NotBlank(message = BLANK_FIELD)
        String imageUrl,

        @NotBlank(message = BLANK_FIELD)
        Boolean isAdopted,

        @NotBlank(message = BLANK_FIELD)
        Boolean petSterilized,

        @NotBlank(message = BLANK_FIELD)
        Boolean petVaccinated,

        @NotBlank(message = BLANK_FIELD)
        Boolean petChipped,

        @NotBlank(message = BLANK_FIELD)
        Boolean specialNeeds,

        @NotBlank(message = BLANK_FIELD)
        Boolean houseTrained,

        @NotBlank(message = BLANK_FIELD)
        Boolean goodWithKids,

        @NotBlank(message = BLANK_FIELD)
        Boolean goodWithDogs,

        @NotBlank(message = BLANK_FIELD)
        Boolean goodWithCats,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        Long organizationId
) {}
