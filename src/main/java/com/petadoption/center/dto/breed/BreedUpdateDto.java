package com.petadoption.center.dto.breed;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.ONLY_LETTERS;

@Builder
public record BreedUpdateDto(
        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        @Schema(description = "Breed name", example = "Poodle")
        String name
) {}