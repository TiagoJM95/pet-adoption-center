package com.petadoption.center.dto.color;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_LETTERS;

@Builder
public record ColorCreateDto(
        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        @Schema(
                description = "Color name",
                example = "Black")
        String name
) {}