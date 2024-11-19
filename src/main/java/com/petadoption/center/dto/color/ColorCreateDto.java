package com.petadoption.center.dto.color;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_LETTERS;
import static com.petadoption.center.util.Regex.USER_NAME_REGEX;

@Builder
public record ColorCreateDto(
        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = USER_NAME_REGEX, message = ONLY_LETTERS)
        String name
) {}