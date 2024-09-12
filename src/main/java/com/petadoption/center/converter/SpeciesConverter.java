package com.petadoption.center.converter;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.model.Species;

public class SpeciesConverter {

    public static Species toModel(SpeciesCreateDto species) {
        return Species.builder()
                .name(species.name())
                .build();
    }

    public static Species toModel(SpeciesGetDto species) {
        return Species.builder()
                .id(species.id())
                .name(species.name())
                .build();
    }

    public static SpeciesGetDto toDto(Species species) {
        return new SpeciesGetDto(
                species.getId(),
                species.getName());
    }
}
