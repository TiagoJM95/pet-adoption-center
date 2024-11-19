package com.petadoption.center.dto.species;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_LETTERS;
import static com.petadoption.center.util.Regex.ORG_NAME_REGEX;

@Builder(toBuilder = true)
public record SpeciesCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = ORG_NAME_REGEX, message = ONLY_LETTERS)
        String name
) {}