package com.petadoption.center.dto.breed;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_LETTERS;

@Builder
public record BreedCreateDto(
        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
        @Schema(
                description = "Breed name",
                example = "Husky")
        String name,

        @NotNull
        @Schema(
                description = "Species id",
                example = "12789-1234-1234-12345")
        String speciesId
) {}