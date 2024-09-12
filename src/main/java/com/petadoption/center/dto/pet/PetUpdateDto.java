package com.petadoption.center.dto.pet;

import jakarta.validation.constraints.Pattern;

import static com.petadoption.center.util.Messages.ONLY_LETTERS;
import static com.petadoption.center.util.Messages.ONLY_NUMBERS;

public record PetUpdateDto(
        String size,
        String age,
        String description,
        String imageUrl,
        Boolean isAdopted,
        Boolean sterilized,
        Boolean vaccinated,
        Boolean chipped,
        Boolean specialNeeds,
        Boolean houseTrained,
        Boolean goodWithKids,
        Boolean goodWithDogs,
        Boolean goodWithCats,

        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        String organizationId
) {}