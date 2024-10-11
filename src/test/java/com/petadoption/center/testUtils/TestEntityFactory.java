package com.petadoption.center.testUtils;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.*;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Attributes;
import com.petadoption.center.model.embeddable.Family;
import com.petadoption.center.model.embeddable.SocialMedia;
import com.petadoption.center.specifications.PetSearchCriteria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestEntityFactory {

    public static Species createSpecies() {
        return Species.builder()
                .id("111111-11111111-1111")
                .name("Dog")
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Species createSpeciesWithoutId() {
        return Species.builder()
                .name("Dog")
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Breed createBreed(Species species) {
        return Breed.builder()
                .id("222222-22222222-2222")
                .name("Labrador")
                .species(species)
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Breed createPrimaryBreed(Species species) {
        return Breed.builder()
                .id("222222-22222222-2222")
                .name("Labrador")
                .species(species)
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Breed createSecondaryBreed(Species species) {
        return Breed.builder()
                .id("333333-33333333-3333")
                .name("Golden Retriever")
                .species(species)
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Color createPrimaryColor() {
        return Color.builder()
                .id("444444-44444444-4444")
                .name("Black")
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Color createPrimaryColorWithoutId() {
        return Color.builder()
                .name("Black")
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Color createSecondaryColor() {
        return Color.builder()
                .id("555555-55555555-5555")
                .name("White")
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Color createSecondaryColorWithoutId() {
        return Color.builder()
                .name("White")
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Color createTertiaryColor() {
        return Color.builder()
                .id("666666-66666666-6666")
                .name("Brown")
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Color createTertiaryColorWithoutId() {
        return Color.builder()
                .name("Brown")
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Address createAddress() {
        return Address.builder()
                .street("Rua de Santo Antonio, 123")
                .city("Gondomar")
                .state("Porto")
                .postalCode("4444-444")
                .build();
    }

    public static Address otherCreateAddress() {
        return Address.builder()
                .street("Rua Diferente da Outra, 321")
                .city("Rio Tinto")
                .state("Porto")
                .postalCode("4435-100")
                .build();
    }

    public static Address updateAddress() {
        return Address.builder()
                .street("Rua das Gaivotas, 456")
                .city("Vila Nova de Gaia")
                .state("Porto")
                .postalCode("4410-000")
                .build();
    }

    public static SocialMedia createSocialMedia() {
        return SocialMedia.builder()
                .facebook("https://www.facebook.com")
                .instagram("https://www.instagram.com")
                .twitter("https://www.twitter.com")
                .youtube("https://www.youtube.com")
                .build();
    }

    public static SocialMedia otherCreateSocialMedia() {
        return SocialMedia.builder()
                .facebook("https://www.facebook.com/outro")
                .instagram("https://www.instagram.com/outro")
                .twitter("https://www.twitter.com/outro")
                .youtube("https://www.youtube.com/outro")
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

    public static Organization createOrganization() {
        return Organization.builder()
                .id("777777-77777777-7777")
                .name("Pet Adoption Center")
                .email("org@email.com")
                .nif("123456789")
                .phoneNumber("123456789")
                .address(createAddress())
                .websiteUrl("https://www.org.com")
                .socialMedia(createSocialMedia())
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Organization createOrganizationWithoutId() {
        return Organization.builder()
                .name("Pet Adoption Center")
                .email("org@email.com")
                .nif("123456789")
                .phoneNumber("123456789")
                .address(createAddress())
                .websiteUrl("https://www.org.com")
                .socialMedia(createSocialMedia())
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static User createUser() {
        return User.builder()
                .id("999999-99999999-9999")
                .firstName("John")
                .lastName("Doe")
                .email("user@email.com")
                .nif("987654321")
                .phoneNumber("987654321")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address(createAddress())
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static User createUserWithoutId() {
        return User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("user@email.com")
                .nif("987654321")
                .phoneNumber("987654321")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address(createAddress())
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Pet createPet() {
        return Pet.builder()
                .id("888888-88888888-8888")
                .name("Max")
                .species(createSpecies())
                .primaryBreed(createPrimaryBreed(createSpecies()))
                .secondaryBreed(createSecondaryBreed(createSpecies()))
                .primaryColor(createPrimaryColor())
                .secondaryColor(createSecondaryColor())
                .tertiaryColor(createTertiaryColor())
                .gender(Genders.MALE)
                .coat(Coats.SHORT)
                .size(Sizes.MEDIUM)
                .age(Ages.ADULT)
                .description("Max is a very friendly dog")
                .imageUrl("https://www.dogimages.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organization(createOrganization())
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Pet createPetWithoutId() {
        return Pet.builder()
                .name("Max")
                .gender(Genders.MALE)
                .coat(Coats.SHORT)
                .size(Sizes.MEDIUM)
                .age(Ages.ADULT)
                .description("Max is a very friendly dog")
                .imageUrl("https://www.dogimages.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static Family createFamily() {
        return Family.builder()
                .familyCount(4)
                .likesPets(true)
                .hasOtherPets(true)
                .numberOfPets(2)
                .familyPets(List.of("DOG", "PARROT"))
                .build();
    }

    public static AdoptionForm createAdoptionForm() {
        return AdoptionForm.builder()
                .id("101010-10101010-1010")
                .user(createUser())
                .pet(createPet())
                .userFamily(createFamily())
                .petVacationHome("Neighbour")
                .isResponsibleForPet(true)
                .otherNotes("Notes")
                .petAddress(createAddress())
                .createdAt(LocalDateTime.of(2024, 1, 1, 1, 1))
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    public static PetSearchCriteria petSearchCriteria(){
        return PetSearchCriteria.builder()
                .nameLike("M")
                .species(createSpecies())
                .breed(createBreed(createSpecies()))
                .color(createPrimaryColor())
                .gender("Male")
                .size("Medium")
                .coat("Short")
                .age("Adult")
                .isAdopted(false)
                .isSterilized(false)
                .isVaccinated(false)
                .isChipped(false)
                .isSpecialNeeds(false)
                .isHouseTrained(false)
                .goodWithCats(false)
                .goodWithDogs(false)
                .goodWithKids(false)
                .state("Porto")
                .city("Gondomar")
                .build();
    }
}
