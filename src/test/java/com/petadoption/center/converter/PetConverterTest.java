package com.petadoption.center.converter;

import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.exception.pet.PetDescriptionException;
import com.petadoption.center.model.*;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Attributes;
import com.petadoption.center.model.embeddable.SocialMedia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
public class PetConverterTest {

    Species species;
    Breed primaryBreed;
    Breed secondaryBreed;
    Color primaryColor;
    Color secondaryColor;
    Color tertiaryColor;
    Attributes attributes;
    Address address;
    SocialMedia socialMedia;
    Organization organization;
    String name;
    String gender;
    String coat;
    String size;
    String age;
    String description;
    String imageUrl;
    String petId;
    SpeciesGetDto speciesGetDto;
    BreedGetDto primaryBreedDto;
    BreedGetDto secondaryBreedDto;
    ColorGetDto primaryColorDto;
    ColorGetDto secondaryColorDto;
    ColorGetDto tertiaryColorDto;
    OrgGetDto organizationDto;

    @BeforeEach
    void setUp(){
        species = Species.builder()
                .id("111111-11111111-1111")
                .name("Dog")
                .build();

        primaryBreed = Breed.builder()
                .id("222222-22222222-2222")
                .name("Labrador")
                .species(species)
                .build();

        secondaryBreed = Breed.builder()
                .id("333333-33333333-3333")
                .name("Golden Retriever")
                .species(species)
                .build();

        primaryColor = Color.builder()
                .id("444444-44444444-4444")
                .name("Black")
                .build();

        secondaryColor = Color.builder()
                .id("555555-55555555-5555")
                .name("White")
                .build();

        tertiaryColor = Color.builder()
                .id("666666-66666666-6666")
                .name("Brown")
                .build();

        attributes = Attributes.builder()
                .chipped(true)
                .houseTrained(true)
                .specialNeeds(false)
                .vaccinated(true)
                .goodWithCats(true)
                .goodWithDogs(true)
                .goodWithKids(true)
                .sterilized(true)
                .build();

        address = Address.builder()
                .street("Rua de Santo António, 123")
                .city("Gondomar")
                .state("Porto")
                .postalCode("4444-444")
                .build();

        socialMedia = SocialMedia.builder()
                .facebook("https://www.facebook.com")
                .instagram("https://www.instagram.com")
                .twitter("https://www.twitter.com")
                .youtube("https://www.youtube.com")
                .build();

        organization = Organization.builder()
                .id("777777-77777777-7777")
                .name("Pet Adoption Center")
                .email("org@email.com")
                .nif("123456789")
                .phoneNumber("123456789")
                .address(address)
                .websiteUrl("https://www.org.com")
                .socialMedia(socialMedia)
                .build();

        name = "Max";
        gender = "Male";
        coat = "Short";
        size = "Medium";
        age = "Young";
        description = "Max is a very friendly dog";
        imageUrl = "https://www.dogimages.com";
        petId = "88888-88888888-8888";

        speciesGetDto = SpeciesGetDto.builder()
                .id("111111-11111111-1111")
                .name("Dog")
                .build();

        primaryBreedDto = BreedGetDto.builder()
                .id("222222-22222222-2222")
                .name("Labrador")
                .speciesDto(speciesGetDto)
                .build();

        secondaryBreedDto = BreedGetDto.builder()
                .id("333333-33333333-3333")
                .name("Golden Retriever")
                .speciesDto(speciesGetDto)
                .build();

        primaryColorDto = ColorGetDto.builder()
                .id("444444-44444444-4444")
                .name("Black")
                .build();

        secondaryColorDto = ColorGetDto.builder()
                .id("555555-55555555-5555")
                .name("White")
                .build();

        tertiaryColorDto = ColorGetDto.builder()
                .id("666666-66666666-6666")
                .name("Brown")
                .build();

        organizationDto = OrgGetDto.builder()
                .id("777777-77777777-7777")
                .name("Pet Adoption Center")
                .email("org@email.com")
                .nif("123456789")
                .phoneNumber("123456789")
                .address(address)
                .websiteUrl("https://www.org.com")
                .socialMedia(socialMedia)
                .build();
    }


    @Test
    @DisplayName("Test if PetCreateDto to Pet model is working correctly")
    void testIfPetCreateDtoToPetModelIsWorkingCorrectly() {
        PetCreateDto petCreateDto = PetCreateDto.builder()
                .name(name)
                .petSpeciesId(species.getId())
                .primaryBreedId(primaryBreed.getId())
                .secondaryBreedId(secondaryBreed.getId())
                .primaryColor(primaryColor.getId())
                .secondaryColor(secondaryColor.getId())
                .tertiaryColor(tertiaryColor.getId())
                .gender(gender)
                .coat(coat)
                .size(size)
                .age(age)
                .description(description)
                .imageUrl(imageUrl)
                .isAdopted(false)
                .attributes(attributes)
                .organizationId(organization.getId())
                .build();

        Pet pet = PetConverter.toModel(petCreateDto);

        assertEquals(name, pet.getName());
        assertEquals(description, pet.getDescription());
        assertEquals(imageUrl, pet.getImageUrl());
        assertFalse(pet.isAdopted());
        assertEquals(attributes, pet.getAttributes());
    }

    @Test
    @DisplayName("Test if PetGetDto to Pet model is working correctly")
    void testIfPetGetDtoToPetModelIsWorkingCorrectly() {
        PetGetDto petGetDto = PetGetDto.builder()
                .id(petId)
                .name(name)
                .speciesDto(speciesGetDto)
                .primaryBreedDto(primaryBreedDto)
                .secondaryBreedDto(secondaryBreedDto)
                .primaryColorDto(primaryColorDto)
                .secondaryColorDto(secondaryColorDto)
                .tertiaryColorDto(tertiaryColorDto)
                .gender(Genders.MALE)
                .coat(Coats.SHORT)
                .size(Sizes.MEDIUM)
                .age(Ages.YOUNG)
                .description(description)
                .imageUrl(imageUrl)
                .isAdopted(false)
                .attributes(attributes)
                .organizationDto(organizationDto)
                .build();

        Pet pet = PetConverter.toModel(petGetDto);

        assertEquals(petId, pet.getId());
        assertEquals(species, pet.getSpecies());
        assertEquals(primaryBreed, pet.getPrimaryBreed());
        assertEquals(secondaryBreed, pet.getSecondaryBreed());
        assertEquals(primaryColor, pet.getPrimaryColor());
        assertEquals(secondaryColor, pet.getSecondaryColor());
        assertEquals(tertiaryColor, pet.getTertiaryColor());
        assertEquals(Genders.MALE, pet.getGender());
        assertEquals(Coats.SHORT, pet.getCoat());
        assertEquals(Sizes.MEDIUM, pet.getSize());
        assertEquals(Ages.YOUNG, pet.getAge());
        assertEquals(description, pet.getDescription());
        assertEquals(imageUrl, pet.getImageUrl());
        assertFalse(pet.isAdopted());
        assertEquals(attributes, pet.getAttributes());
        assertEquals(organization, pet.getOrganization());
    }

    @Test
    @DisplayName("Test if Pet model to PetGetDto is working correctly")
    void testIfPetModelToPetGetDtoIsWorkingCorrectly() {

        Pet pet = Pet.builder()
                .id(petId)
                .name(name)
                .species(species)
                .primaryBreed(primaryBreed)
                .secondaryBreed(secondaryBreed)
                .primaryColor(primaryColor)
                .secondaryColor(secondaryColor)
                .tertiaryColor(tertiaryColor)
                .gender(Genders.MALE)
                .coat(Coats.SHORT)
                .size(Sizes.MEDIUM)
                .age(Ages.YOUNG)
                .description(description)
                .imageUrl(imageUrl)
                .isAdopted(false)
                .attributes(attributes)
                .organization(organization)
                .build();

        PetGetDto petGetDto = PetConverter.toDto(pet);

        assertEquals(petId, petGetDto.id());
        assertEquals(name, petGetDto.name());
        assertEquals(speciesGetDto, petGetDto.speciesDto());
        assertEquals(primaryBreedDto, petGetDto.primaryBreedDto());
        assertEquals(secondaryBreedDto, petGetDto.secondaryBreedDto());
        assertEquals(primaryColorDto, petGetDto.primaryColorDto());
        assertEquals(secondaryColorDto, petGetDto.secondaryColorDto());
        assertEquals(tertiaryColorDto, petGetDto.tertiaryColorDto());
        assertEquals(Genders.MALE, petGetDto.gender());
        assertEquals(Coats.SHORT, petGetDto.coat());
        assertEquals(Sizes.MEDIUM, petGetDto.size());
        assertEquals(Ages.YOUNG, petGetDto.age());
        assertEquals(description, petGetDto.description());
        assertEquals(imageUrl, petGetDto.imageUrl());
        assertFalse(petGetDto.isAdopted());
        assertEquals(attributes, petGetDto.attributes());
        assertEquals(organizationDto, petGetDto.organizationDto());
    }

    @Test
    @DisplayName("Test if passing null to any of the PetConverter methods returns null")
    void testIfPassingNullToAnyPetConverterMethodReturnsNull() {

        Pet pet = null;
        PetGetDto petGetDto = null;

        assertEquals(pet, PetConverter.toModel((PetCreateDto) null));
        assertEquals(pet, PetConverter.toModel((PetGetDto) null));
        assertEquals(petGetDto, PetConverter.toDto((Pet) null));
    }
}