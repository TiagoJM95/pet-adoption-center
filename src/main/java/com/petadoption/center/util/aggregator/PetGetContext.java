package com.petadoption.center.util.aggregator;

import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import lombok.Builder;

@Builder
public record PetGetContext(
        OrgGetDto organization,
        SpeciesGetDto species,
        BreedGetDto primaryBreed,
        BreedGetDto secondaryBreed,
        ColorGetDto primaryColor,
        ColorGetDto secondaryColor,
        ColorGetDto tertiaryColor
) {}