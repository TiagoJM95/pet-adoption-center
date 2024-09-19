package com.petadoption.center.dto.pet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petadoption.center.model.embeddable.Attributes;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_LETTERS;

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

        Boolean isAdopted,

        @Valid
        Attributes attributes,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String organizationId
) {}