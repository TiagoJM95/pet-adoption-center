package com.petadoption.center.converter;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;

public class BreedConverter {

    public static Breed fromBreedCreateDtoToModel(BreedCreateDto breed, Species species) {
        return Breed.builder()
                .name(breed.name())
                .species(species)
                .build();
    }

    public static BreedGetDto fromModelToBreedGetDto(Breed breed) {
        return new BreedGetDto(
                breed.getId(),
                breed.getName(),
                breed.getSpecies().getName()
        );
    }
}
