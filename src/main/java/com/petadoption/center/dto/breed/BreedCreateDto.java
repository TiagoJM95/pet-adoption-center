package com.petadoption.center.dto.breed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

import static com.petadoption.center.util.Messages.*;

@Builder
public record BreedCreateDto(
        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
        String name,

        @UUID(message = ONLY_UUID)
        String speciesId
) {}