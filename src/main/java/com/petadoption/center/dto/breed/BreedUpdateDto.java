package com.petadoption.center.dto.breed;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.ONLY_LETTERS;
import static com.petadoption.center.util.Regex.ORG_NAME_REGEX;

@Builder
public record BreedUpdateDto(
        @Pattern(regexp = ORG_NAME_REGEX, message = ONLY_LETTERS)
        String name
) {}