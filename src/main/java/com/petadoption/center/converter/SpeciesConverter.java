package com.petadoption.center.converter;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.model.Species;

public class SpeciesConverter {

    public static Species fromSpeciesCreateDtoToModel(SpeciesCreateDto species) {
        return Species.builder()
                .name(species.name())
                .build();
    }

    public static SpeciesGetDto fromModelToSpeciesGetDto(Species species) {
        return new SpeciesGetDto(
                species.getId(),
                species.getName());
    }
}
