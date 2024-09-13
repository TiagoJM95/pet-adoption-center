package com.petadoption.center.dto.pet;

import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.model.embeddable.Attributes;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PetGetDto(
        String id,
        String name,
        String species,
        String primaryBreed,
        String secondaryBreed,
        String primaryColor,
        String secondaryColor,
        String tertiaryColor,
        String gender,
        String coat,
        String size,
        String age,
        String description,
        String imageUrl,
        Boolean isAdopted,
        Attributes attributes,
        LocalDate dateAdded,
        OrgGetDto organization
) {}