package com.petadoption.center.dto.petSearchCriteria;

import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Color;

public record PetSearchCriteria(
        String nameLike,
        Breed breed,
        Color color,
        String gender,
        String coat,
        String size,
        String age,
        Boolean isAdopted,
        Boolean isSterilized,
        Boolean isVaccinated,
        Boolean isChipped,
        Boolean isSpecialNeeds,
        Boolean isHouseTrained,
        Boolean goodWithKids,
        Boolean goodWithDogs,
        Boolean goodWithCats
) {}