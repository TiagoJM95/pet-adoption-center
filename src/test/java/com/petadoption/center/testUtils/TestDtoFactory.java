package com.petadoption.center.testUtils;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.petadoption.center.enums.Ages.ADULT;
import static com.petadoption.center.enums.Coats.SHORT;
import static com.petadoption.center.enums.Genders.MALE;
import static com.petadoption.center.enums.Sizes.MEDIUM;
import static com.petadoption.center.testUtils.TestEntityFactory.*;

public class TestDtoFactory {

    // GET DTOs

    public static SpeciesGetDto speciesGetDto() {
        return SpeciesGetDto.builder()
                .id("111111-11111111-1111")
                .name("Dog")
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static BreedGetDto primaryBreedGetDto(SpeciesGetDto speciesGetDto) {
        return BreedGetDto.builder()
                .id("222222-22222222-2222")
                .name("Labrador")
                .speciesDto(speciesGetDto)
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static BreedGetDto secondaryBreedGetDto(SpeciesGetDto speciesGetDto) {
        return BreedGetDto.builder()
                .id("333333-33333333-3333")
                .name("Golden Retriever")
                .speciesDto(speciesGetDto)
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static ColorGetDto primaryColorGetDto() {
        return ColorGetDto.builder()
                .id("444444-44444444-4444")
                .name("Black")
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static ColorGetDto secondaryColorGetDto() {
        return ColorGetDto.builder()
                .id("555555-55555555-5555")
                .name("White")
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static ColorGetDto tertiaryColorGetDto() {
        return ColorGetDto.builder()
                .id("666666-66666666-6666")
                .name("Brown")
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static OrganizationGetDto orgGetDto() {
        return OrganizationGetDto.builder()
                .id("777777-77777777-7777")
                .name("Pet Adoption Center")
                .email("org@email.com")
                .nipc("523456789")
                .phoneNumber("923456789")
                .address(createAddress())
                .websiteUrl("https://www.org.com")
                .socialMedia(createSocialMedia())
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static UserGetDto userGetDto() {
        return UserGetDto.builder()
                .id("999999-99999999-9999")
                .firstName("John")
                .lastName("Doe")
                .email("user@email.com")
                .nif("287654321")
                .phoneNumber("917654321")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address(createAddress())
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static PetGetDto petGetDto() {
        return PetGetDto.builder()
                .id("888888-88888888-8888")
                .name("Max")
                .speciesDto(speciesGetDto())
                .primaryBreedDto(primaryBreedGetDto(speciesGetDto()))
                .secondaryBreedDto(secondaryBreedGetDto(speciesGetDto()))
                .primaryColorDto(primaryColorGetDto())
                .secondaryColorDto(secondaryColorGetDto())
                .tertiaryColorDto(tertiaryColorGetDto())
                .gender(MALE)
                .coat(SHORT)
                .size(MEDIUM)
                .age(ADULT)
                .description("Max is a very friendly dog")
                .imageUrl("https://www.dogimages.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationDto(orgGetDto())
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static AdoptionFormGetDto adoptionFormGetDto() {
        return AdoptionFormGetDto.builder()
                .id("101010-10101010-1010")
                .user(userGetDto())
                .pet(petGetDto())
                .userFamily(createFamily())
                .petVacationHome("Pet Hotel")
                .isResponsibleForPet(true)
                .otherNotes("Notes")
                .petAddress(createAddress())
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static InterestGetDto interestGetDto() {
        return InterestGetDto.builder()
                .id("1111-2222-3333")
                .userDto(userGetDto())
                .petDto(petGetDto())
                .organizationDto(orgGetDto())
                .status(Status.PENDING)
                .createdAt(LocalDateTime.of(2024, 1, 1, 1 ,1))
                .build();

    }

    public static InterestGetDto interestGetDtoUpdated() {
        return InterestGetDto.builder()
                .id("1111-2222-3333")
                .userDto(userGetDto())
                .petDto(petGetDto())
                .organizationDto(orgGetDto())
                .status(Status.ACCEPTED)
                .createdAt(LocalDateTime.of(2024, 1, 1, 1 ,1))
                .reviewTimestamp(LocalDateTime.of(2024, 1, 1, 2, 1))
                .build();

    }

    // CREATE DTOs

    public static ColorCreateDto primaryColorCreateDto() {
        return ColorCreateDto.builder()
                .name("Black")
                .build();
    }

    public static ColorCreateDto secondaryColorCreateDto() {
        return ColorCreateDto.builder()
                .name("White")
                .build();
    }

    public static ColorCreateDto tertiaryColorCreateDto() {
        return ColorCreateDto.builder()
                .name("Brown")
                .build();
    }

    public static PetCreateDto petCreateDto(String name) {
        return PetCreateDto.builder()
                .name(name)
                .speciesId("111111-11111111-1111")
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

    public static SpeciesCreateDto speciesCreateDto(){
        return SpeciesCreateDto.builder()
                .name("Dog")
                .build();
    }

    public static SpeciesCreateDto otherSpeciesCreateDto(){
        return SpeciesCreateDto.builder()
                .name("Cat")
                .build();
    }

    public static UserCreateDto userCreateDto(){
        return UserCreateDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("user@email.com")
                .nif("287654321")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address(createAddress())
                .phoneNumber("917654321")
                .build();
    }

    public static UserCreateDto otherUserCreateDto(){
        return UserCreateDto.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("user1@email.com")
                .nif("387654322")
                .dateOfBirth(LocalDate.of(1991, 1, 1))
                .address(otherCreateAddress())
                .phoneNumber("927654322")
                .build();
    }

    public static BreedCreateDto primaryBreedCreateDto(String speciesId){
        return BreedCreateDto.builder()
                .name("Golden Retriever")
                .speciesId(speciesId)
                .build();
    }

    public static BreedCreateDto otherPrimaryBreedCreateDto(String speciesId){
        return BreedCreateDto.builder()
                .name("Siamese")
                .speciesId(speciesId)
                .build();
    }

    public static BreedCreateDto secondaryBreedCreateDto(String speciesId){
        return BreedCreateDto.builder()
                .name("Labrador")
                .speciesId(speciesId)
                .build();
    }

    public static BreedCreateDto otherSecondaryBreedCreateDto(String speciesId){
        return BreedCreateDto.builder()
                .name("Persian")
                .speciesId(speciesId)
                .build();
    }

    public static OrganizationCreateDto organizationCreateDto(){
        return OrganizationCreateDto.builder()
                .name("Pet Adoption Center")
                .email("org@email.com")
                .nipc("523456789")
                .phoneNumber("923456789")
                .address(createAddress())
                .websiteUrl("https://www.org.com")
                .socialMedia(createSocialMedia())
                .build();
    }


    public static OrganizationCreateDto otherOrganizationCreateDto(){
        return OrganizationCreateDto.builder()
                .name("AdoptPet")
                .email("adoptpet@email.com")
                .nipc("533456789")
                .phoneNumber("913456789")
                .address(otherCreateAddress())
                .websiteUrl("https://www.adoptpet.com")
                .socialMedia(otherCreateSocialMedia())
                .build();
    }

    public static AdoptionFormCreateDto adoptionFormCreateDto() {
        return AdoptionFormCreateDto.builder()
                .userId("999999-99999999-9999")
                .petId("101010-10101010-1010")
                .userFamily(createFamily())
                .petVacationHome("Pet Hotel")
                .isResponsibleForPet(true)
                .otherNotes("Notes")
                .petAddress(createAddress())
                .build();
    }

    public static InterestCreateDto interestCreateDto() {
        return InterestCreateDto.builder()
                .userId(userGetDto().id())
                .petId(petGetDto().id())
                .organizationId(orgGetDto().id())
                .build();

    }

    // UPDATE DTOs

    public static BreedUpdateDto breedUpdateDto(){
        return BreedUpdateDto.builder()
                .name("Weimaraner")
                .build();
    }

    public static SpeciesUpdateDto speciesUpdateDto(){
        return SpeciesUpdateDto.builder()
                .name("Cat")
                .build();
    }

    public static UserUpdateDto userUpdateDto(){
        return UserUpdateDto.builder()
                .firstName("Tiago")
                .lastName("Moreira")
                .email("tm@email.com")
                .address(updateAddress())
                .phoneNumber("934587967")
                .build();
    }

    public static UserUpdateDto otherUserUpdateDto(){
        return UserUpdateDto.builder()
                .firstName("Maria")
                .lastName("Silva")
                .email("ms@email.com")
                .address(otherCreateAddress())
                .phoneNumber("934587968")
                .build();
    }

    public static PetUpdateDto petUpdateDto(){
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

    public static AdoptionFormUpdateDto adoptionFormUpdateDto() {
        return AdoptionFormUpdateDto.builder()
                .userFamily(createFamily())
                .petVacationHome("Pet Hotel")
                .isResponsibleForPet(true)
                .otherNotes("Notes")
                .petAddress(createAddress())
                .build();
    }

    public static InterestUpdateDto interestUpdateDtoToRejected() {
        return InterestUpdateDto.builder()
                .status("rejected")
                .build();
    }

    public static InterestUpdateDto interestUpdateDtoToFormRequested() {
        return InterestUpdateDto.builder()
                .status("FORM_REQUESTED")
                .build();
    }
  
    public static OrganizationUpdateDto orgUpdateDto(){
        return OrganizationUpdateDto.builder()
                .name("Pet Adoption Center")
                .email("email@email.com")
                .phoneNumber("923456789")
                .address(createAddress())
                .websiteUrl("https://www.org.com")
                .socialMedia(createSocialMedia())
                .build();
    }
}
