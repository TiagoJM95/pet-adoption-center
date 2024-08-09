package com.petadoption.center.dto.pet;

public record PetCreateDto(
        String name,
        Long petSpeciesId,
        Long primaryBreedId,
        Long secondaryBreedId,
        String pet_color_primary,
        String pet_color_secondary,
        String pet_color_tertiary,
        String gender,
        String coat,
        String size,
        String age,
        String description,
        String imageUrl,
        Boolean isAdopted,
        Boolean pet_sterilized,
        Boolean pet_vaccinated,
        Boolean pet_chipped,
        Boolean specialNeeds,
        Boolean houseTrained,
        Boolean goodWithKids,
        Boolean goodWithDogs,
        Boolean goodWithCats,
        Long organizationId
) {
}
