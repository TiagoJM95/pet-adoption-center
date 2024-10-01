package com.petadoption.center.dto.pet;

import com.petadoption.center.model.embeddable.Attributes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_NUMBERS;
@Builder
public record PetUpdateDto(

        @NotBlank(message = BLANK_FIELD)
        @Schema(
                description = "Pet size ",
                example = "Small")
        String size,

        @NotBlank(message = BLANK_FIELD)
        @Schema(
                description = "Pet age ",
                example = "Adult")
        String age,

        @NotBlank(message = BLANK_FIELD)
        @Schema(
                description = "Pet description ",
                example = "Cute and friendly")
        String description,

        @NotBlank(message = BLANK_FIELD)
        @Schema(
                description = "Pet image url ",
                example = "https://example.com/image.jpg")
        String imageUrl,

        @Schema(
                description = "Is pet adopted ",
                example = "true")
        boolean isAdopted,

        Attributes attributes,

        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        @Schema(
                description = "Pet organization id ",
                example = "12789-1234-1234-12345")
        String organizationId
) {}