package com.petadoption.center.dto.pet;

import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.model.embeddable.PetAttribute;

import java.time.LocalDate;
import java.util.Set;

public record PetGetDto(
        Long id,
        String name,
        String petSpecies,
        String primaryBreed,
        String secondaryBreed,
        String color,
        String gender,
        String coat,
        String size,
        String age,
        String description,
        String imageUrl,
        Boolean isAdopted,
        PetAttribute attributes,
        LocalDate dateAdded,
        OrganizationGetDto organization,
        Set<AdoptionFormGetDto> adoptionForms
) {
}
