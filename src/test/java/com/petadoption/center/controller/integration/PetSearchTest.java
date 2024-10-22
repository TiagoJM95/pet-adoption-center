package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Attributes;
import com.petadoption.center.model.embeddable.SocialMedia;
import com.petadoption.center.specifications.PetSearchCriteria;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Stream;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestDtoFactory.otherOrganizationCreateDto;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PetSearchTest extends PetControllerTest {

    private SpeciesGetDto dog;
    private SpeciesGetDto cat;

    private BreedGetDto labrador;
    private BreedGetDto bulldog;
    private BreedGetDto corgi;
    private BreedGetDto mastiff;
    private BreedGetDto poodle;

    private BreedGetDto siamese;
    private BreedGetDto bengal;
    private BreedGetDto havana;
    private BreedGetDto persian;
    private BreedGetDto tuxedo;

    private ColorGetDto black;
    private ColorGetDto white;
    private ColorGetDto brown;
    private ColorGetDto orange;
    private ColorGetDto yellow;

    private OrganizationGetDto org1;
    private OrganizationGetDto org2;
    private OrganizationGetDto org3;

    private PetGetDto pet1;
    private PetGetDto pet2;
    private PetGetDto pet3;
    private PetGetDto pet4;
    private PetGetDto pet5;
    private PetGetDto pet6;
    private PetGetDto pet7;
    private PetGetDto pet8;
    private PetGetDto pet9;
    private PetGetDto pet10;

    void persistSpecies() throws Exception {
        dog = persistSpecies(speciesCreateDto());
        cat = persistSpecies(otherSpeciesCreateDto());
    }

    void persistDogBreeds() throws Exception {

        labrador = persistBreed(BreedCreateDto.builder()
                .name("Labrador")
                .speciesId(dog.id())
                .build());

        bulldog = persistBreed(BreedCreateDto.builder()
                .name("Bulldog")
                .speciesId(dog.id())
                .build());

        corgi = persistBreed(BreedCreateDto.builder()
                .name("Corgi")
                .speciesId(dog.id())
                .build());

        mastiff = persistBreed(BreedCreateDto.builder()
                .name("Mastiff")
                .speciesId(dog.id())
                .build());

        poodle = persistBreed(BreedCreateDto.builder()
                .name("Poodle")
                .speciesId(dog.id())
                .build());
    }

    void persistCatBreeds() throws Exception {

        siamese = persistBreed(BreedCreateDto.builder()
                .name("Siamese")
                .speciesId(cat.id())
                .build());

        bengal = persistBreed(BreedCreateDto.builder()
                .name("Bengal")
                .speciesId(cat.id())
                .build());

        havana = persistBreed(BreedCreateDto.builder()
                .name("Havana")
                .speciesId(cat.id())
                .build());

        persian = persistBreed(BreedCreateDto.builder()
                .name("Persian")
                .speciesId(cat.id())
                .build());

        tuxedo = persistBreed(BreedCreateDto.builder()
                .name("Tuxedo")
                .speciesId(cat.id())
                .build());
    }

    void persistColors() throws Exception {

        black = persistColor(new ColorCreateDto("Black"));
        white = persistColor(new ColorCreateDto("White"));
        brown = persistColor(new ColorCreateDto("Brown"));
        orange = persistColor(new ColorCreateDto("Orange"));
        yellow = persistColor(new ColorCreateDto("Yellow"));
    }

    void persistOrganization() throws Exception {
        org1 = persistOrganization(organizationCreateDto());
        org2 = persistOrganization(otherOrganizationCreateDto());
        org3 = persistOrganization(OrganizationCreateDto.builder()
                .name("Assossiacao Patinhas")
                .email("patinhas@email.com")
                .nif("999999999")
                .phoneNumber("999999999")
                .websiteUrl("www.patinhas.com")
                .address(Address.builder()
                        .street("Rua dos Patinhas, 666")
                        .city("Fanzeres")
                        .state("Porto")
                        .postalCode("4044-000")
                        .build())
                .socialMedia(SocialMedia.builder()
                        .facebook("www.facebook.com/patinhas")
                        .instagram("www.instagram.com/patinhas")
                        .twitter("www.twitter.com/patinhas")
                        .youtube("www.youtube.com/patinhas")
                        .build())
                .build());
    }

    void persistPets() throws Exception {

        pet1 = persistPet(PetCreateDto.builder()
                .name("Spike")
                .speciesId(dog.id())
                .primaryBreedId(labrador.id())
                .primaryColor(black.id())
                .gender("Male")
                .coat("Medium")
                .size("Extra Large")
                .age("Adult")
                .description("Spike is very very crazy")
                .imageUrl("https://spike.com")
                .attributes(Attributes.builder()
                        .sterilized(true)
                        .vaccinated(true)
                        .chipped(true)
                        .specialNeeds(false)
                        .houseTrained(false)
                        .goodWithKids(true)
                        .goodWithDogs(false)
                        .goodWithCats(true)
                        .build())
                .isAdopted(true)
                .organizationId(org1.id())
                .build());

        pet2 = persistPet(PetCreateDto.builder()
                .name("Bella")
                .speciesId(dog.id())
                .primaryBreedId(corgi.id())
                .secondaryBreedId(mastiff.id())
                .primaryColor(white.id())
                .secondaryColor(black.id())
                .gender("Female")
                .coat("Short")
                .size("Small")
                .age("Young")
                .description("Bella is a friendly and energetic corgi.")
                .imageUrl("https://bella.com")
                .attributes(Attributes.builder()
                        .sterilized(true)
                        .vaccinated(true)
                        .chipped(false)
                        .specialNeeds(false)
                        .houseTrained(true)
                        .goodWithKids(true)
                        .goodWithDogs(true)
                        .goodWithCats(false)
                        .build())
                .isAdopted(false)
                .organizationId(org2.id())
                .build());

        pet3 = persistPet(PetCreateDto.builder()
                .name("Simba")
                .speciesId(cat.id())
                .primaryBreedId(siamese.id())
                .secondaryBreedId(tuxedo.id())
                .primaryColor(orange.id())
                .secondaryColor(white.id())
                .gender("Male")
                .coat("Short")
                .size("Medium")
                .age("Young")
                .description("Simba is a playful Siamese kitten.")
                .imageUrl("https://simba.com")
                .attributes(Attributes.builder()
                        .sterilized(false)
                        .vaccinated(true)
                        .chipped(false)
                        .specialNeeds(false)
                        .houseTrained(true)
                        .goodWithKids(true)
                        .goodWithDogs(false)
                        .goodWithCats(true)
                        .build())
                .isAdopted(false)
                .organizationId(org3.id())
                .build());

        pet4 = persistPet(PetCreateDto.builder()
                .name("Milo")
                .speciesId(cat.id())
                .primaryBreedId(bengal.id())
                .primaryColor(brown.id())
                .gender("Male")
                .coat("Medium")
                .size("Large")
                .age("Senior")
                .description("Milo is a calm and gentle senior Bengal.")
                .imageUrl("https://milo.com")
                .attributes(Attributes.builder()
                        .sterilized(true)
                        .vaccinated(false)
                        .chipped(false)
                        .specialNeeds(true)
                        .houseTrained(true)
                        .goodWithKids(true)
                        .goodWithDogs(true)
                        .goodWithCats(true)
                        .build())
                .isAdopted(true)
                .organizationId(org1.id())
                .build());

        pet5 = persistPet(PetCreateDto.builder()
                .name("Luna")
                .speciesId(cat.id())
                .primaryBreedId(persian.id())
                .primaryColor(black.id())
                .gender("Female")
                .coat("Long")
                .size("Small")
                .age("Adult")
                .description("Luna is a beautiful and quiet Persian cat.")
                .imageUrl("https://luna.com")
                .attributes(Attributes.builder()
                        .sterilized(true)
                        .vaccinated(true)
                        .chipped(true)
                        .specialNeeds(false)
                        .houseTrained(true)
                        .goodWithKids(false)
                        .goodWithDogs(false)
                        .goodWithCats(true)
                        .build())
                .isAdopted(false)
                .organizationId(org2.id())
                .build());

        pet6 = persistPet(PetCreateDto.builder()
                .name("Max")
                .speciesId(dog.id())
                .primaryBreedId(bulldog.id())
                .primaryColor(brown.id())
                .secondaryColor(white.id())
                .gender("Male")
                .coat("Short")
                .size("Medium")
                .age("Adult")
                .description("Max is a loyal and protective Bulldog.")
                .imageUrl("https://max.com")
                .attributes(Attributes.builder()
                        .sterilized(false)
                        .vaccinated(true)
                        .chipped(true)
                        .specialNeeds(false)
                        .houseTrained(true)
                        .goodWithKids(true)
                        .goodWithDogs(true)
                        .goodWithCats(false)
                        .build())
                .isAdopted(true)
                .organizationId(org3.id())
                .build());

        pet7 = persistPet(PetCreateDto.builder()
                .name("Charlie")
                .speciesId(dog.id())
                .primaryBreedId(mastiff.id())
                .primaryColor(black.id())
                .gender("Male")
                .coat("Medium")
                .size("Extra Large")
                .age("Senior")
                .description("Charlie is a gentle giant Mastiff.")
                .imageUrl("https://charlie.com")
                .attributes(Attributes.builder()
                        .sterilized(true)
                        .vaccinated(false)
                        .chipped(true)
                        .specialNeeds(true)
                        .houseTrained(true)
                        .goodWithKids(true)
                        .goodWithDogs(true)
                        .goodWithCats(true)
                        .build())
                .isAdopted(false)
                .organizationId(org1.id())
                .build());

        pet8 = persistPet(PetCreateDto.builder()
                .name("Daisy")
                .speciesId(dog.id())
                .primaryBreedId(poodle.id())
                .primaryColor(white.id())
                .gender("Female")
                .coat("Long")
                .size("Medium")
                .age("Baby")
                .description("Daisy is a playful and affectionate Poodle puppy.")
                .imageUrl("https://daisy.com")
                .attributes(Attributes.builder()
                        .sterilized(false)
                        .vaccinated(false)
                        .chipped(false)
                        .specialNeeds(false)
                        .houseTrained(false)
                        .goodWithKids(true)
                        .goodWithDogs(true)
                        .goodWithCats(true)
                        .build())
                .isAdopted(false)
                .organizationId(org2.id())
                .build());

        pet9 = persistPet(PetCreateDto.builder()
                .name("Cleo")
                .speciesId(cat.id())
                .primaryBreedId(havana.id())
                .primaryColor(black.id())
                .gender("Female")
                .coat("Short")
                .size("Medium")
                .age("Baby")
                .description("Cleo is a curious Havana kitten.")
                .imageUrl("https://cleo.com")
                .attributes(Attributes.builder()
                        .sterilized(false)
                        .vaccinated(true)
                        .chipped(false)
                        .specialNeeds(false)
                        .houseTrained(true)
                        .goodWithKids(true)
                        .goodWithDogs(false)
                        .goodWithCats(true)
                        .build())
                .isAdopted(false)
                .organizationId(org3.id())
                .build());

        pet10 = persistPet(PetCreateDto.builder()
                .name("Oscar")
                .speciesId(dog.id())
                .primaryBreedId(labrador.id())
                .primaryColor(yellow.id())
                .gender("Male")
                .coat("Short")
                .size("Large")
                .age("Adult")
                .description("Oscar is a loving and friendly Labrador.")
                .imageUrl("https://oscar.com")
                .attributes(Attributes.builder()
                        .sterilized(true)
                        .vaccinated(true)
                        .chipped(true)
                        .specialNeeds(false)
                        .houseTrained(true)
                        .goodWithKids(true)
                        .goodWithDogs(true)
                        .goodWithCats(false)
                        .build())
                .isAdopted(true)
                .organizationId(org1.id())
                .build());
    }

    @BeforeAll
    void setup() throws Exception {
        persistSpecies();
        persistDogBreeds();
        persistCatBreeds();
        persistColors();
        persistOrganization();
        persistPets();
    }

    @AfterEach
    void reset() {}

    private Stream<Arguments> provideSpeciesFilter() {
        return Stream.of(
                arguments(PetSearchCriteria.builder()
                                .speciesNames(List.of("Cat"))
                                .build(),
                        List.of(pet3, pet4, pet5, pet9)),
                arguments(PetSearchCriteria.builder()
                            .speciesNames(List.of("Rat"))
                            .build(),
                        List.of()),
                arguments(PetSearchCriteria.builder()
                                .speciesNames(List.of("Dog"))
                                .build(),
                        List.of(pet1, pet2, pet6, pet7, pet8, pet10))
        );
    }

    @Test
    @DisplayName("Should return an empty list if pets table in the database is empty")
    void shouldReturnEmptyList_WhenSearchIsCalled_WhileDbIsEmpty() throws Exception {

        petRepository.deleteAll();

        MvcResult result = mockMvc.perform(post(SEARCH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)))
                .andReturn();

        List<PetGetDto> actualList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNotNull(actualList);
    }

    @Test
    @DisplayName("Should return a list with same size as Pets stored in pets table when filters are not provided")
    void shouldReturnListWithSameSizeAsPetsInDb_WhenSearchIsCalledWithNoFilters() throws Exception {

        List<PetGetDto> expectedList = List.of(pet1, pet2, pet3, pet4, pet5, pet6, pet7, pet8, pet9, pet10);

        MvcResult result = mockMvc.perform(post(SEARCH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<PetGetDto> actualList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @MethodSource("provideSpeciesFilter")
    @DisplayName("")
    void shouldReturnListOfPetsThatMatchFilters_WhenSearchIsCalled_WithSpeciesAsFilter(PetSearchCriteria filters, List<PetGetDto> expectedList) throws Exception {

        MvcResult result = mockMvc.perform(post(SEARCH)
                        .content(objectMapper.writeValueAsString(filters))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<PetGetDto> actualList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertEquals(expectedList, actualList);
    }
}