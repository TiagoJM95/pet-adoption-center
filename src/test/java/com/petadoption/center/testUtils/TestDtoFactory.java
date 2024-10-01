package com.petadoption.center.testUtils;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.petadoption.center.testUtils.TestEntityFactory.*;

public class TestDtoFactory {

    // GET DTOs

    public static SpeciesGetDto createSpeciesGetDto() {
        return SpeciesGetDto.builder()
                .id("111111-11111111-1111")
                .name("Dog")
                .build();
    }

    public static BreedGetDto createBreedGetDto(SpeciesGetDto speciesGetDto) {
        return BreedGetDto.builder()
                .id("2222-2222-3333")
                .name("Golden Retriever")
                .speciesDto(speciesGetDto)
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

    public static PetCreateDto createPetCreateDto(String name) {
        return PetCreateDto.builder()
                .name(name)
                .petSpeciesId("111111-11111111-1111")
                .primaryBreedId("222222-22222222-2222")
                .secondaryBreedId("333333-33333333-3333")
                .primaryColor("444444-44444444-4444")
                .secondaryColor("555555-55555555-5555")
                .tertiaryColor("666666-66666666-6666")
                .gender("Male")
                .coat("Short")
                .size("Medium")
                .age("Adult")
                .description("Max is a very friendly dog")
                .imageUrl("https://www.dogimages.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationId("777777-77777777-7777")
                .build();
    }

    public static PetUpdateDto createPetUpdateDto(){
        return PetUpdateDto.builder()
                .size("Large")
                .age("Senior")
                .description("Max is an updated dog")
                .imageUrl("https://www.updatedimages.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationId("777777-77777777-7777")
                .build();
    }

    // CREATE DTOs

    public static BreedCreateDto createBreedCreateDto(String speciesId){
        return BreedCreateDto.builder()
                .name("Golden Retriever")
                .speciesId(speciesId)
                .build();
    }


    public static ColorCreateDto createColorCreateDto(){
        return ColorCreateDto.builder()
                .name("Black")
                .build();
    }



    public static SpeciesCreateDto createSpeciesCreateDto(){
        return SpeciesCreateDto.builder()
                .name("Dog")
                .build();
    }

    public static UserCreateDto createUserCreateDto(){
        return UserCreateDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("user@email.com")
                .nif("987654321")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address(createAddress())
                .phoneNumber("987654321")
                .build();
    }


    public static BreedCreateDto breedCreateDto(String speciesId){
        return BreedCreateDto.builder()
                .name("Golden Retriever")
                .speciesId(speciesId)
                .build();
    }

    public static ColorCreateDto colorCreateDto(){
        return ColorCreateDto.builder()
                .name("Black")
                .build();
    }

    public static OrgCreateDto orgCreateDto(){
        return OrgCreateDto.builder()
                .name("Pet Adoption Center")
                .email("org@email.com")
                .nif("123456789")
                .phoneNumber("123456789")
                .address(createAddress())
                .websiteUrl("https://www.org.com")
                .socialMedia(createSocialMedia())
                .build();
    }

    // UPDATE DTOs

    public static BreedUpdateDto createBreedUpdateDto(){
        return BreedUpdateDto.builder()
                .name("Weimaraner")
                .build();
    }

    public static SpeciesUpdateDto createSpeciesUpdateDto(){
        return SpeciesUpdateDto.builder()
                .name("Cat")
                .build();
    }

    public static UserUpdateDto createUserUpdateDto(){
        return UserUpdateDto.builder()
                .firstName("Tiago")
                .lastName("Moreira")
                .email("tm@email.com")
                .address(updateAddress())
                .phoneNumber("934587967")
                .build();
    }

    public static BreedUpdateDto breedUpdateDto(){
        return BreedUpdateDto.builder()
                .name("Weimaraner")
                .build();
    }


}
