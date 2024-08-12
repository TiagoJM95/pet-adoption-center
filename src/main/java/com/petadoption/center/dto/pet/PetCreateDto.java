package com.petadoption.center.dto.pet;

public record PetCreateDto(
        String name,
        Long petSpeciesId,
        Long primaryBreedId,
        Long secondaryBreedId,
        Long primaryColor,
        Long secondaryColor,
        Long tertiaryColor,
        String gender,
        String coat,
        String size,
        String age,
        String description,
        String imageUrl,
        Boolean isAdopted,
        Boolean petSterilized,
        Boolean petVaccinated,
        Boolean petChipped,
        Boolean specialNeeds,
        Boolean houseTrained,
        Boolean goodWithKids,
        Boolean goodWithDogs,
        Boolean goodWithCats,
        Long organizationId
) {
}
