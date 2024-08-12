package com.petadoption.center.dto.pet;

import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.model.embeddable.Attributes;

import java.time.LocalDate;
import java.util.Set;

public record PetGetDto(
        Long id,
        String name,
        SpeciesGetDto species,
        BreedGetDto primaryBreed,
        BreedGetDto secondaryBreed,
        ColorGetDto primaryColor,
        ColorGetDto secondaryColor,
        ColorGetDto tertiaryColor,
        String gender,
        String coat,
        String size,
        String age,
        String description,
        String imageUrl,
        Boolean isAdopted,
        Attributes attributes,
        LocalDate dateAdded,
        OrganizationGetDto organization
) {
}
