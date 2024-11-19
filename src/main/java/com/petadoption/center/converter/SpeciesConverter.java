package com.petadoption.center.converter;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.model.Species;

public class SpeciesConverter {

    public static Species toModel(SpeciesCreateDto dto) {
        if (dto == null) return null;
        return Species.builder()
                .name(dto.name())
                .build();
    }

    public static Species toModel(SpeciesGetDto dto) {
        if (dto == null) return null;
        return Species.builder()
                .id(dto.id())
                .name(dto.name())
                .createdAt(dto.createdAt())
                .build();
    }

    public static SpeciesGetDto toDto(Species species) {
        if (species == null) return null;
        return SpeciesGetDto.builder()
                .id(species.getId())
                .name(species.getName())
                .createdAt(species.getCreatedAt())
                .build();
    }
}