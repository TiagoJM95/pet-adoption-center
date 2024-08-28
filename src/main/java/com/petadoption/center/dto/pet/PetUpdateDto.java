package com.petadoption.center.dto.pet;

public record PetUpdateDto(
        String name,
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
        Long organizationId
) {}
