package com.petadoption.center.specifications;

import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Color;
import com.petadoption.center.model.Species;

public record PetSearchCriteria(
        String nameLike,
        Species species,
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
        Boolean goodWithCats,
        Boolean isPureBreed,
        String state,
        String city
) {}