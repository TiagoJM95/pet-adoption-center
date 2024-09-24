package com.petadoption.center.testUtils;

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
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Color;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.Species;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Attributes;
import com.petadoption.center.model.embeddable.SocialMedia;

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

    public static Organization createOrganization() {
        return Organization.builder()
                .id("777777-77777777-7777")
                .name("Pet Adoption Center")
                .email("org@email.com")
                .nif("123456789")
                .phoneNumber("123456789")
                .address(createAddress())
                .websiteUrl("https://www.google.com")
                .socialMedia(createSocialMedia())
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
                .websiteUrl("https://www.google.com")
                .socialMedia(createSocialMedia())
                .build();
    }

    private static SocialMedia createSocialMedia() {
        return SocialMedia.builder()
                .facebook("https://www.facebook.com")
                .instagram("https://www.instagram.com")
                .twitter("https://www.twitter.com")
                .youtube("https://www.youtube.com")
                .build();
    }

    private static Address createAddress() {
        return Address.builder()
                .street("Rua de Santo António, 123")
                .city("Gondomar")
                .state("Porto")
                .postalCode("4444-444")
                .build();
    }

    public static Attributes createAttributes(){
        return Attributes.builder()
                .chipped(true)
                .houseTrained(true)
                .specialNeeds(false)
                .vaccinated(true)
                .goodWithCats(true)
                .goodWithDogs(true)
                .goodWithKids(true)
                .sterilized(true)
                .build();
    }

    public static PetCreateDto createPetCreateDto() {
        return PetCreateDto.builder()
                .name("Max")
                .petSpeciesId("111111-11111111-1111")
                .primaryBreedId("222222-22222222-2222")
                .secondaryBreedId("333333-33333333-3333")
                .primaryColor("444444-44444444-4444")
                .secondaryColor("555555-55555555-5555")
                .tertiaryColor("666666-66666666-6666")
                .gender("Male")
                .coat("Short")
                .size("Medium")
                .age("Young")
                .description("Max is a very friendly dog")
                .imageUrl("https://www.dogimages.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationId("777777-77777777-7777")
                .build();
    }

    public static PetGetDto createPetGetDto() {
        Genders genders = null;
        Coats coats = null;
        Sizes sizes = null;
        Ages ages = null;

        try{
            genders = Genders.getGenderByDescription("Male");
            coats = Coats.getCoatByDescription("Short");
            sizes = Sizes.getSizeByDescription("Medium");
            ages = Ages.getAgeByDescription("Young");
        } catch (PetDescriptionException e){
            e.printStackTrace();
        }

        return PetGetDto.builder()
                .id("88888-88888888-8888")
                .name("Max")
                .speciesDto(createSpeciesGetDto())
                .primaryBreedDto(createPrimaryBreedDto(createSpeciesGetDto()))
                .secondaryBreedDto(createSecondaryBreedDto(createSpeciesGetDto()))
                .primaryColorDto(createPrimaryColorDto())
                .secondaryColorDto(createSecondaryColorDto())
                .tertiaryColorDto(createTertiaryColorDto())
                .gender(genders)
                .coat(coats)
                .size(sizes)
                .age(ages)
                .description("Max is a very friendly dog")
                .imageUrl("https://www.dogimages.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationDto(createOrgGetDto())
                .build();
    }

}
