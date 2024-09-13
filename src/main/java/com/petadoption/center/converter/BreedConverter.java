package com.petadoption.center.converter;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;

public class BreedConverter {

    public static Breed toModel(BreedCreateDto dto, Species species) {
        if (dto == null || species == null) return null;
        return Breed.builder()
                .name(dto.name())
                .species(species)
                .build();
    }

    public static Breed toModel(BreedGetDto dto, Species species) {
        if (dto == null || species == null) return null;
        return Breed.builder()
                .id(dto.id())
                .name(dto.name())
                .species(species)
                .build();
    }

    public static BreedGetDto toDto(Breed breed) {
        if (breed == null) return null;
        return new BreedGetDto(
                breed.getId(),
                breed.getName(),
                breed.getSpecies().getName()
        );
    }
}