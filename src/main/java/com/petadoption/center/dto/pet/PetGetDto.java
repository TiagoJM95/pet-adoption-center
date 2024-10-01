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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PetGetDto(
        @Schema(
                description = "Pet id",
                example = "12789-1234-1234-12345")
        String id,

        @Schema(
                description = "Pet name",
                example = "Rex")
        String name,

        @Schema(
                description = "Pet's species information",
                example = "{ \"id\": \"12789-1234-1234-12345\", \"name\": \"Dog\" }",
                type = "SpeciesGetDto")
        SpeciesGetDto speciesDto,

        @Schema(
                description = "Primary breed information",
                example = "{ \"id\": \"12789-1234-1234-12345\", \"name\": \"Husky\" }",
                type = "BreedGetDto")
        BreedGetDto primaryBreedDto,

        @Schema(
                description = "Secondary breed information",
                example = "{ \"id\": \"12789-1234-1234-12345\", \"name\": \"German Shepherd\" }",
                type = "BreedGetDto")
        BreedGetDto secondaryBreedDto,

        @Schema(
                description = "Primary color information",
                example = "{ \"id\": \"12789-1234-1234-12345\", \"name\": \"Black\" }",
                type = "ColorGetDto")
        ColorGetDto primaryColorDto,

        @Schema(
                description = "Secondary color information",
                example = "{ \"id\": \"12789-1234-1234-12345\", \"name\": \"White\" }",
                type = "ColorGetDto")
        ColorGetDto secondaryColorDto,

        @Schema(
                description = "Tertiary color information",
                example = "{ \"id\": \"12789-1234-1234-12345\", \"name\": \"Brown\" }",
                type = "ColorGetDto")
        ColorGetDto tertiaryColorDto,

        @Schema(
                description = "Pet gender",
                example = "Male")
        Genders gender,

        @Schema(
                description = "Pet coat",
                example = "Short")
        Coats coat,

        @Schema(
                description = "Pet size",
                example = "Small")
        Sizes size,

        @Schema(
                description = "Pet age",
                example = "Adult")
        Ages age,

        @Schema(
                description = "Pet description",
                example = "Cute and friendly")
        String description,

        @Schema(
                description = "Pet image url",
                example = "https://www.example.com")
        String imageUrl,

        @Schema(
                description = "Pet is adopted",
                example = "true")
        Boolean isAdopted,

        Attributes attributes,

        @Schema(
                description = "Pet creation date",
                example = "2022-01-01T00:00:00" 
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Pet organization details",
                example = "{ \"organizationId\": \"12789-1234-1234-12345\", \"name\": \"Animal Rescue\", \"email\": \"email@email.com\", \"nif\": \"123456789\", \"phoneNumber\": \"918765432\", \"websiteUrl\": \"https://www.example.com\", \"address\": { \"street\": \"Rua dos gatos, 132\", \"city\": \"Gondomar\", \"postalCode\": \"4400-111\", \"state\": \"Porto\" }, \"socialMedia\": { \"facebook\": \"https://www.facebook.com\", \"instagram\": \"animalRescue\", \"twitter\": \"animalRescue\", \"youtube\": \"https://www.youtube.com\" } }",
                type = "OrgGetDto"
        )
        OrgGetDto organizationDto
) {}