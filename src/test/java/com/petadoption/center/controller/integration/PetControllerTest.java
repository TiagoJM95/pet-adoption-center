package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.converter.PetConverter;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.exception.not_found.PetNotFoundException;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.embeddable.Attributes;
import com.petadoption.center.repository.PetRepository;
import com.petadoption.center.specifications.PetSearchCriteria;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.createAttributes;
import static com.petadoption.center.util.Messages.PET_NOT_FOUND;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PetRepository petRepository;

    private final String URL = "/api/v1/pet/";

    private SpeciesGetDto speciesGetDto1;
    private SpeciesGetDto speciesGetDto2;

    private BreedGetDto primaryBreedGetDto1;
    private BreedGetDto primaryBreedGetDto2;

    private BreedGetDto secondaryBreedGetDto1;
    private BreedGetDto secondaryBreedGetDto2;

    private ColorGetDto primaryColorGetDto;
    private ColorGetDto secondaryColorGetDto;
    private ColorGetDto tertiaryColorGetDto;

    private OrganizationGetDto organizationGetDto1;
    private OrganizationGetDto organizationGetDto2;

    private static PetCreateDto petCreateDto;
    private static PetGetDto expectedGetDto;

    private PetSearchCriteria petSearchCriteria;


    @BeforeAll
    void setup() throws Exception {

        speciesGetDto1 = persistSpecies(speciesCreateDto());
        speciesGetDto2 = persistSpecies(otherSpeciesCreateDto());

        primaryBreedGetDto1 = persistBreed(primaryBreedCreateDto(speciesGetDto1.id()));
        primaryBreedGetDto2 = persistBreed(otherPrimaryBreedCreateDto(speciesGetDto2.id()));

        secondaryBreedGetDto1 = persistBreed(secondaryBreedCreateDto(speciesGetDto1.id()));
        secondaryBreedGetDto2 = persistBreed(otherSecondaryBreedCreateDto(speciesGetDto2.id()));

        primaryColorGetDto = persistColor(primaryColorCreateDto());
        secondaryColorGetDto = persistColor(secondaryColorCreateDto());
        tertiaryColorGetDto = persistColor(tertiaryColorCreateDto());

        organizationGetDto1 = persistOrganization(organizationCreateDto());
        organizationGetDto2 = persistOrganization(otherOrganizationCreateDto());

        Attributes attributes = createAttributes();

        petCreateDto = PetCreateDto.builder()
                .name("Max")
                .speciesId(speciesGetDto1.id())
                .primaryBreedId(primaryBreedGetDto1.id())
                .secondaryBreedId(secondaryBreedGetDto1.id())
                .primaryColor(primaryColorGetDto.id())
                .secondaryColor(secondaryColorGetDto.id())
                .tertiaryColor(tertiaryColorGetDto.id())
                .gender("Male")
                .coat("Medium")
                .age("Adult")
                .size("Large")
                .description("Max is friendly dog!")
                .imageUrl("https://dog.com")
                .isAdopted(false)
                .attributes(attributes)
                .organizationId(organizationGetDto1.id())
                .build();

        expectedGetDto = PetGetDto.builder()
                .name("Max")
                .speciesDto(speciesGetDto1)
                .primaryBreedDto(primaryBreedGetDto1)
                .secondaryBreedDto(secondaryBreedGetDto1)
                .primaryColorDto(primaryColorGetDto)
                .secondaryColorDto(secondaryColorGetDto)
                .tertiaryColorDto(tertiaryColorGetDto)
                .gender(Genders.MALE)
                .coat(Coats.MEDIUM)
                .size(Sizes.LARGE)
                .age(Ages.ADULT)
                .description("Max is friendly dog!")
                .imageUrl("https://dog.com")
                .isAdopted(false)
                .attributes(attributes)
                .organizationDto(organizationGetDto1)
                .build();
    }

    /*void method(){
        createAllFields = PetMutableDto.builder()
                .name("Max")
                .speciesId(speciesId)
                .primaryBreedId(primaryBreedId)
                .secondaryBreedId(secondaryBreedId)
                .primaryColor(primaryColorId)
                .secondaryColor(secondaryColorId)
                .tertiaryColor(tertiaryColorId)
                .gender("Male")
                .coat("Medium")
                .age("Adult")
                .size("Large")
                .description("Max is friendly dog!")
                .imageUrl("https://dog.com")
                .isAdopted(false)
                .attributes(attributes)
                .organizationId(organizationId)
                .build();

        createNullFields = PetMutableDto.builder()
                .name("Max")
                .speciesId(speciesId)
                .primaryBreedId(primaryBreedId)
                .primaryColor(primaryColorId)
                .gender("Male")
                .coat("Medium")
                .age("Adult")
                .size("Large")
                .description("Max is friendly dog!")
                .imageUrl("https://dog.com")
                .isAdopted(false)
                .attributes(attributes)
                .organizationId(organizationId)
                .build();

        expectedAllFields = PetMutableDto.builder()
                .name("Max")
                .speciesDto(speciesGetDto)
                .primaryBreedDto(primaryBreedGetDto)
                .secondaryBreedDto(secondaryBreedGetDto)
                .primaryColorDto(primaryColorGetDto)
                .secondaryColorDto(secondaryColorGetDto)
                .tertiaryColorDto(tertiaryColorGetDto)
                .genders(Genders.MALE)
                .coats(Coats.MEDIUM)
                .sizes(Sizes.LARGE)
                .ages(Ages.ADULT)
                .description("Max is friendly dog!")
                .imageUrl("https://dog.com")
                .isAdopted(false)
                .attributes(attributes)
                .organizationDto(organizationGetDto)
                .build();

        expectedNullFields = PetMutableDto.builder()
                .name("Max")
                .speciesDto(speciesGetDto)
                .primaryBreedDto(primaryBreedGetDto)
                .primaryColorDto(primaryColorGetDto)
                .genders(Genders.MALE)
                .coats(Coats.MEDIUM)
                .sizes(Sizes.LARGE)
                .ages(Ages.ADULT)
                .description("Max is friendly dog!")
                .imageUrl("https://dog.com")
                .isAdopted(false)
                .attributes(attributes)
                .organizationDto(organizationGetDto)
                .build();
    }*/

    @AfterEach
    void reset(){
        petRepository.deleteAll();
    }

    private SpeciesGetDto persistSpecies(SpeciesCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/species/")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), SpeciesGetDto.class);
    }

    private BreedGetDto persistBreed(BreedCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/breed/")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto.class);
    }

    private ColorGetDto persistColor(ColorCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/color/")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto.class);
    }

    private OrganizationGetDto persistOrganization(OrganizationCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/organization/")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), OrganizationGetDto.class);
    }

    private PetGetDto persistPet(PetCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post(URL + "addSingle")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), PetGetDto.class);
    }

    private static Stream<Arguments> provideCreateDtosWithAllPropsAndNullProps() {
        return Stream.of(
                arguments(petCreateDto, expectedGetDto),
                arguments(petCreateDto.toBuilder()
                                .secondaryBreedId(null)
                                .build(),
                        expectedGetDto.toBuilder()
                                .secondaryBreedDto(null)
                                .build()),
                arguments(petCreateDto.toBuilder()
                                .secondaryBreedId(null)
                                .secondaryColor(null)
                                .tertiaryColor(null)
                                .build(),
                        expectedGetDto.toBuilder()
                                .secondaryBreedDto(null)
                                .secondaryColorDto(null)
                                .tertiaryColorDto(null)
                                .build())
        );
    }

    private static Stream<Arguments> provideCreateDtosForDupesCheck(){
        return Stream.of(
                arguments(petCreateDto.toBuilder()
                        .imageUrl("https://dif.com")
                        .build()),
                arguments(petCreateDto.toBuilder()
                        .name("Fifi")
                        .build())
        );
    }


    // create


    @ParameterizedTest
    @MethodSource("provideCreateDtosWithAllPropsAndNullProps")
    @DisplayName("Should persist a new Pet and return a PetGetDto with correct fields when a valid PetCreateDto is provided")
    void shouldCreatePetAndReturnCorrectPetGetDto_whenPetIsValid(PetCreateDto createDto, PetGetDto expected) throws Exception {

        PetGetDto persistedPetDto = persistPet(createDto);

        assertThat(persistedPetDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(expected);
        assertNotNull(persistedPetDto.createdAt());
        assertTrue(persistedPetDto.id().matches("^[0-9a-fA-F-]{36}$"));
    }

    @ParameterizedTest
    @MethodSource("provideCreateDtosWithAllPropsAndNullProps")
    @DisplayName("Persisted Pet should match the Pet retrieved from the database")
    void shouldMatchPersistedPetWithRetrievedPet_whenPetIsValid(PetCreateDto createDto) throws Exception {

        PetGetDto persistedPetDto = persistPet(createDto);

        Pet retrievedPet = petRepository.findById(persistedPetDto.id())
                .orElseThrow(() -> new PetNotFoundException(format(PET_NOT_FOUND, persistedPetDto.id())));
        PetGetDto retrievedPetDto = PetConverter.toDto(retrievedPet);

        assertThat(persistedPetDto)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(retrievedPetDto);
    }

    @ParameterizedTest
    @MethodSource("provideCreateDtosForDupesCheck")
    @DisplayName("Should throw DataIntegrityViolationException when creating a Pet with a constraint that already exists")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldThrowDataIntegrityViolationException_WhenCreatingPet_WhenConstraintAlreadyExists(PetCreateDto dto) throws Exception {

        // Save the first
        persistPet(dto);
        // Verify the second one fails
        MvcResult result = mockMvc.perform(post(URL + "addSingle")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
        // Assert that the exception message includes the constraint name
        // Assert that only one pet exists in the DB

    }

    @Test
    @DisplayName("Should throw HttpMessageNotReadableException when create is called with no request body")
    void shouldThrowHttpMessageNotReadableException_WhenCreateIsCalledWithNoBody() throws Exception {}

    @Test
    @DisplayName("Should throw MethodArgumentNotValidException when create is called with an invalid PetCreateDto")
    void shouldThrowMethodArgumentNotValidException_WhenCreateIsCalledWithInvalidPetCreateDto() throws Exception {}


    // getById


    @Test
    @DisplayName("Returns a PetGetDto when petService.getById() is called with a valid Id")
    void shouldReturnPetGetDto_WhenGetByIdIsCalledWithValidId() throws Exception {
    }

    @Test
    @DisplayName("Throws PetNotFoundException when petService.getById() is called with an invalid Id")
    void shouldThrowPetNotFoundExceptionWhenGetByIdIsCalledWithInvalidId() throws Exception {

    }


    // update


    @Test
    @DisplayName("Should update Pet and return a PetGetDto with correct fields when a valid ID and UpdateDto are provided")
    void shouldUpdatePetAndReturnCorrectPetGetDto_whenPetIdAndUpdateDtoAreValid() throws Exception {}

    @Test
    @DisplayName("Check If pet was updated in db")
    void test() throws Exception {}

    @Test
    @DisplayName("PetNotFound")
    void test1() throws Exception {}

    @Test
    @DisplayName("InvalidDto")
    void test2() throws Exception {}

    @Test
    @DisplayName("No Body")
    void test3() throws Exception {}


    // delete


    @Test
    @DisplayName("Delete returns string")
    void test4() throws Exception {}

    @Test
    @DisplayName("Check if pet was deleted in db")
    void test5() throws Exception {}

    @Test
    @DisplayName("PetNotFound")
    void test6() throws Exception {}


    // add list


    @Test
    @DisplayName("Should persist a list Pet and return a String")
    void test7() throws Exception {}

    @Test
    @DisplayName("Confirm all the pets were added")
    void test8() throws Exception {}

    @Test
    @DisplayName("DataIntegrityViolationException in any element")
    void test9() throws Exception {}

    @Test
    @DisplayName("InvalidDto")
    void test10() throws Exception {}

    @Test
    @DisplayName("No Body")
    void test11() throws Exception {}


    //search

    @Test
    @DisplayName("Returns Empty List if no Pets")
    void test12() throws Exception {}

    @Test
    @DisplayName("Returns list of X amount of pets")
    void test13() throws Exception {}

    @Test
    @DisplayName("Works without PetSearchCriteria")
    void test14() throws Exception {}

    @Test
    @DisplayName("Returns Empty List if no Pets match PetSearchCriteria")
    void test15() throws Exception {}

    @Test
    @DisplayName("Check if number of pages match")
    void test16() throws Exception {}

    @Test
    @DisplayName("Check if number of elements match size number")
    void test17() throws Exception {}

    @Test
    @DisplayName("Check if sort works for every property asc or desc")
    void test18() throws Exception {}

    @Test
    @DisplayName("Test filters")
    void test19() throws Exception {}

}