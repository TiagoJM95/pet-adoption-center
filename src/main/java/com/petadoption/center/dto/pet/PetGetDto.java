package com.petadoption.center.dto.pet;

import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.embeddable.Attributes;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record PetGetDto(
        String id,
        String name,
        SpeciesGetDto speciesDto,
        BreedGetDto primaryBreedDto,
        BreedGetDto secondaryBreedDto,
        ColorGetDto primaryColorDto,
        ColorGetDto secondaryColorDto,
        ColorGetDto tertiaryColorDto,
        Genders gender,
        Coats coat,
        Sizes size,
        Ages age,
        String description,
        String imageUrl,
        Boolean isAdopted,
        Attributes attributes,
        LocalDateTime createdAt,
        OrgGetDto organizationDto
) {}