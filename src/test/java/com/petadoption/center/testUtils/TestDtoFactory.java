package com.petadoption.center.testUtils;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.Color;

import java.time.LocalDate;

import static com.petadoption.center.testUtils.TestEntityFactory.createAddress;
import static com.petadoption.center.testUtils.TestEntityFactory.createSocialMedia;

public class TestDtoFactory {

    public static SpeciesGetDto createSpeciesGetDto() {
        return SpeciesGetDto.builder()
                .id("111111-11111111-1111")
                .name("Dog")
                .build();
    }

    public static BreedGetDto createPrimaryBreedDto(SpeciesGetDto speciesGetDto) {
        return BreedGetDto.builder()
                .id("222222-22222222-2222")
                .name("Labrador")
                .speciesDto(speciesGetDto)
                .build();
    }

    public static BreedGetDto createSecondaryBreedDto(SpeciesGetDto speciesGetDto) {
        return BreedGetDto.builder()
                .id("333333-33333333-3333")
                .name("Golden Retriever")
                .speciesDto(speciesGetDto)
                .build();
    }

    public static ColorGetDto createPrimaryColorDto() {
        return ColorGetDto.builder()
                .id("444444-44444444-4444")
                .name("Black")
                .build();
    }

    public static ColorGetDto createSecondaryColorDto() {
        return ColorGetDto.builder()
                .id("555555-55555555-5555")
                .name("White")
                .build();
    }

    public static ColorGetDto createTertiaryColorDto() {
        return ColorGetDto.builder()
                .id("666666-66666666-6666")
                .name("Brown")
                .build();
    }

    public static OrgGetDto createOrgGetDto() {
        return OrgGetDto.builder()
                .id("777777-77777777-7777")
                .name("Pet Adoption Center")
                .email("org@email.com")
                .nif("123456789")
                .phoneNumber("123456789")
                .address(createAddress())
                .websiteUrl("https://www.org.com")
                .socialMedia(createSocialMedia())
                .build();
    }

    public static UserGetDto createUserGetDto() {
        return UserGetDto.builder()
                .id("999999-99999999-9999")
                .firstName("John")
                .lastName("Doe")
                .email("user@email.com")
                .nif("987654321")
                .phoneNumber("987654321")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address(createAddress())
                .build();
    }
}
