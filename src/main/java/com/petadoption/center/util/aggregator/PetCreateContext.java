package com.petadoption.center.util.aggregator;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Color;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.Species;
import com.petadoption.center.model.embeddable.Attributes;
import lombok.Builder;

@Builder
public record PetCreateContext(
        Species species,
        Breed primaryBreed,
        Breed secondaryBreed,
        Color primaryColor,
        Color secondaryColor,
        Color tertiaryColor,
        Organization organization,
        Genders gender,
        Coats coat,
        Sizes size,
        Ages age,
        Attributes attributes
) {}