package com.petadoption.center.testUtils;

import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Color;
import com.petadoption.center.model.Species;

public class TestDataFactory {

    public static Species createSpecies() {
        return Species.builder()
                .id("111111-11111111-1111")
                .name("Dog")
                .build();
    }

    public static SpeciesGetDto createSpeciesGetDto() {
        return SpeciesGetDto.builder()
                .id("111111-11111111-1111")
                .name("Dog")
                .build();
    }

    public static Breed createPrimaryBreed(Species species) {
        return Breed.builder()
                .id("222222-22222222-2222")
                .name("Labrador")
                .species(species)
                .build();
    }

    public static BreedGetDto createPrimaryBreedDto(SpeciesGetDto speciesGetDto) {
        return BreedGetDto.builder()
                .id("222222-22222222-2222")
                .name("Labrador")
                .speciesDto(speciesGetDto)
                .build();
    }

    public static Breed createSecondaryBreed(Species species) {
        return Breed.builder()
                .id("333333-33333333-3333")
                .name("Golden Retriever")
                .species(species)
                .build();
    }

    public static BreedGetDto createSecondaryBreedDto(SpeciesGetDto speciesGetDto) {
        return BreedGetDto.builder()
                .id("333333-33333333-3333")
                .name("Golden Retriever")
                .speciesDto(speciesGetDto)
                .build();
    }

    public static Color createPrimaryColor() {
        return Color.builder()
                .id("444444-44444444-4444")
                .name("Black")
                .build();
    }

    public static ColorGetDto createPrimaryColorDto() {
        return ColorGetDto.builder()
                .id("444444-44444444-4444")
                .name("Black")
                .build();
    }

    public static Color createSecondaryColor() {
        return Color.builder()
                .id("555555-55555555-5555")
                .name("White")
                .build();
    }

    public static ColorGetDto createSecondaryColorDto() {
        return ColorGetDto.builder()
                .id("555555-55555555-5555")
                .name("White")
                .build();
    }

    public static Color createTertiaryColor() {
        return Color.builder()
                .id("666666-66666666-6666")
                .name("Brown")
                .build();
    }

    public static ColorGetDto createTertiaryColorDto() {
        return ColorGetDto.builder()
                .id("666666-66666666-6666")
                .name("Brown")
                .build();
    }

    public static PetCreateDto creatPetCreateDto() {
        return PetCreateDto.builder()
                .name("Max")
                .petSpeciesId("111111-11111111-1111")
                .primaryBreedId("222222-22222222-2222")
                .secondaryBreedId("333333-33333333-3333")
                .build();
    }

}
