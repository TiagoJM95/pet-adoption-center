package com.petadoption.center.converter;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;

public class BreedConverter {

    public static Breed toModel(BreedCreateDto breed, Species species) {
        return Breed.builder()
                .name(breed.name())
                .species(species)
                .build();
    }

    public static Breed toModel(BreedGetDto breed, Species species) {
        return Breed.builder()
                .id(breed.id())
                .name(breed.name())
                .species(species)
                .build();
    }

    public static BreedGetDto toDto(Breed breed) {
        return new BreedGetDto(
                breed.getId(),
                breed.getName(),
                breed.getSpecies().getName()
        );
    }
}