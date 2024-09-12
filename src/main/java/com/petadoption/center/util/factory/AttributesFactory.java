package com.petadoption.center.util.factory;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.model.embeddable.Attributes;

public class AttributesFactory {

    public static Attributes create(PetCreateDto pet) {
        return Attributes.builder()
                .sterilized(pet.petSterilized())
                .vaccinated(pet.petVaccinated())
                .chipped(pet.petChipped())
                .specialNeeds(pet.specialNeeds())
                .houseTrained(pet.houseTrained())
                .goodWithKids(pet.goodWithKids())
                .goodWithDogs(pet.goodWithDogs())
                .goodWithCats(pet.goodWithCats())
                .build();

    }

    public static Attributes create(PetUpdateDto pet) {
        return Attributes.builder()
                .sterilized(pet.sterilized())
                .vaccinated(pet.vaccinated())
                .chipped(pet.chipped())
                .specialNeeds(pet.specialNeeds())
                .houseTrained(pet.houseTrained())
                .goodWithKids(pet.goodWithKids())
                .goodWithDogs(pet.goodWithDogs())
                .goodWithCats(pet.goodWithCats())
                .build();

    }
}
