package com.petadoption.center.dto.breed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Regex.ORG_NAME_REGEX;

@Builder
public record BreedCreateDto(
        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = ORG_NAME_REGEX, message = ONLY_LETTERS)
        String name,

        @NotBlank(message = BLANK_FIELD)
        @UUID(message = ONLY_UUID)
        String speciesId
) {}