package com.petadoption.center.dto.pet;

import com.petadoption.center.model.embeddable.Attributes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_LETTERS;

@Builder
public record PetCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        @Schema(description = "Pet name", example = "Rex")
        String name,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        @Schema(description = "Pet species id", example = "12789-1234-1234-12345")
        String petSpeciesId,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        @Schema(description = "Pet breed id", example = "12789-1234-1234-12345")
        String primaryBreedId,

        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        @Schema(description = "Pet secondary breed id", example = "12789-1234-1234-12345")
        String secondaryBreedId,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        @Schema(description = "Pet primary color id", example = "12789-1234-1234-12345")
        String primaryColor,

        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        @Schema(description = "Pet secondary color id", example = "12789-1234-1234-12345")
        String secondaryColor,

        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        @Schema(description = "Pet tertiary color id", example = "12789-1234-1234-12345")
        String tertiaryColor,

        @NotBlank(message = BLANK_FIELD)
        @Schema(description = "Pet gender", example = "Male")
        String gender,

        @NotBlank(message = BLANK_FIELD)
        @Schema(description = "Pet coat", example = "Short")
        String coat,

        @NotBlank(message = BLANK_FIELD)
        @Schema(description = "Pet size", example = "Medium")
        String size,

        @NotBlank(message = BLANK_FIELD)
        @Schema(description = "Pet age", example = "Adult")
        String age,

        @NotBlank(message = BLANK_FIELD)
        @Schema(description = "Pet description", example = "A friendly and loving dog.")
        String description,

        @NotBlank(message = BLANK_FIELD)
        @Schema(description = "Pet image url", example = "https://example.com/image.jpg")
        String imageUrl,
        @Schema(description = "Is adopted", example = "false")
        Boolean isAdopted,

        Attributes attributes,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        @Schema(description = "Pet organization id", example = "12789-1234-1234-12345")
        String organizationId
) {}