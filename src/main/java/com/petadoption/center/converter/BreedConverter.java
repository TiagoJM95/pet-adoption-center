package com.petadoption.center.converter;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.model.Breed;

public class BreedConverter {

    public static Breed toModel(BreedCreateDto dto) {
        if (dto == null) return null;
        return Breed.builder()
                .name(dto.name())
                .build();
    }

    public static Breed toModel(BreedGetDto dto) {
        if (dto == null) return null;
        return Breed.builder()
                .id(dto.id())
                .name(dto.name())
                .species(SpeciesConverter.toModel(dto.speciesDto()))
                .build();
    }

    public static BreedGetDto toDto(Breed breed) {
        if (breed == null) return null;
        return BreedGetDto.builder()
                .id(breed.getId())
                .name(breed.getName())
                .speciesDto(SpeciesConverter.toDto(breed.getSpecies()))
                .build();
    }
}