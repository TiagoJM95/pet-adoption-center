package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.petadoption.center.aspect.Error;
import com.petadoption.center.converter.PetConverter;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.exception.not_found.PetNotFoundException;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.embeddable.Attributes;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.petadoption.center.testUtils.ConstantsURL.*;
import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.createAttributes;
import static com.petadoption.center.util.Messages.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PetControllerTest extends AbstractIntegrationTest {

    private SpeciesGetDto speciesGetDto1;
    private SpeciesGetDto speciesGetDto2;

    private BreedGetDto primaryBreedGetDto1;
    private BreedGetDto primaryBreedGetDto2;

    private BreedGetDto secondaryBreedGetDto1;
    private BreedGetDto secondaryBreedGetDto2;

    private ColorGetDto primaryColorGetDto;

    private OrganizationGetDto organizationGetDto1;
    private OrganizationGetDto organizationGetDto2;

    private static PetCreateDto petCreateDto;
    private static PetCreateDto petCreateDto2;
    private static PetGetDto expectedGetDto;
    private static PetUpdateDto updateDto;

    @BeforeAll
    void setup() throws Exception {

        speciesGetDto1 = persistSpecies(speciesCreateDto());
        speciesGetDto2 = persistSpecies(otherSpeciesCreateDto());

        primaryBreedGetDto1 = persistBreed(primaryBreedCreateDto(speciesGetDto1.id()));
        primaryBreedGetDto2 = persistBreed(otherPrimaryBreedCreateDto(speciesGetDto2.id()));

        secondaryBreedGetDto1 = persistBreed(secondaryBreedCreateDto(speciesGetDto1.id()));
        secondaryBreedGetDto2 = persistBreed(otherSecondaryBreedCreateDto(speciesGetDto2.id()));

        primaryColorGetDto = persistColor(primaryColorCreateDto());
        ColorGetDto secondaryColorGetDto = persistColor(secondaryColorCreateDto());
        ColorGetDto tertiaryColorGetDto = persistColor(tertiaryColorCreateDto());

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
                .attributes(createAttributes())
                .organizationId(organizationGetDto1.id())
                .build();

        petCreateDto2 = PetCreateDto.builder()
                .name("Spike")
                .speciesId(speciesGetDto1.id())
                .primaryBreedId(secondaryBreedGetDto1.id())
                .primaryColor(tertiaryColorGetDto.id())
                .gender("Male")
                .coat("Long")
                .age("Adult")
                .size("Large")
                .description("Spike is friendly dog!")
                .imageUrl("https://spike.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationId(organizationGetDto2.id())
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

        updateDto = PetUpdateDto.builder()
                .age("Senior")
                .imageUrl("https://www.updatedimages.com")
                .isAdopted(true)
                .build();
    }

    @AfterEach
    void reset(){
        petRepository.deleteAll();
    }


    private Stream<Arguments> provideCreateDtosWithAllPropsAndNullProps() {
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

    private Stream<Arguments> provideCreateDtosForDupesCheck() {
        PetCreateDto dto = PetCreateDto.builder()
                .name("Bobby")
                .speciesId(speciesGetDto2.id())
                .primaryBreedId(primaryBreedGetDto2.id())
                .secondaryBreedId(secondaryBreedGetDto2.id())
                .primaryColor(primaryColorGetDto.id())
                .gender("Female")
                .coat("Short")
                .age("Baby")
                .size("Medium")
                .description("Bobby is a cat!")
                .imageUrl("https://cat.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationId(organizationGetDto2.id())
                .build();
        return Stream.of(
                arguments(dto.toBuilder()
                        .name("Max")
                        .speciesId(speciesGetDto1.id())
                        .primaryBreedId(primaryBreedGetDto1.id())
                        .secondaryBreedId(secondaryBreedGetDto1.id())
                        .organizationId(organizationGetDto1.id())
                        .build(), "uniquepetnamespeciesorg"),
                arguments(dto.toBuilder()
                        .imageUrl("https://dog.com")
                        .build(), "uniquepetimage"));
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

    @ParameterizedTest(name = "Check for constraint {1}")
    @MethodSource("provideCreateDtosForDupesCheck")
    @DisplayName("Should throw DataIntegrityViolationException when creating a Pet with a constraint that already exists")
    void shouldThrowDataIntegrityViolationException_WhenCreatingPet_WhenConstraintAlreadyExists(PetCreateDto dto, String constraintName) throws Exception {

        persistPet(petCreateDto);
        MvcResult result = mockMvc.perform(post(PET_ADD_SINGLE_URL)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);

        assertEquals(constraintName, error.constraint());
        assertEquals(petRepository.findAll().size(), 1);
    }

    @Test
    @DisplayName("Should throw HttpMessageNotReadableException when create is called with no request body")
    void shouldThrowHttpMessageNotReadableException_WhenCreateIsCalledWithNoBody() throws Exception {

        MvcResult result = mockMvc.perform(post(PET_ADD_SINGLE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);

        assertTrue(error.message().contains("Required request body is missing"));
    }

    @Test
    @DisplayName("Should throw MethodArgumentNotValidException when create is called with an invalid PetCreateDto")
    void shouldThrowMethodArgumentNotValidException_WhenCreateIsCalledWithInvalidPetCreateDto() throws Exception {

        PetCreateDto invalidDto = PetCreateDto.builder()
                .name("Bobby")
                .speciesId(speciesGetDto2.id())
                .primaryBreedId(primaryBreedGetDto2.id())
                .primaryColor(primaryColorGetDto.id())
                .coat("Short")
                .age("Baby")
                .size("Medium")
                .description("Bobby is a cat!")
                .imageUrl("https://cat.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationId(organizationGetDto2.id())
                .build();

        MvcResult result = mockMvc.perform(post(PET_ADD_SINGLE_URL)
                        .content(objectMapper.writeValueAsString(invalidDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);

        assertEquals(Map.of("gender", REQUIRED_FIELD), error.validationIssue());
    }


    // getById


    @Test
    @DisplayName("Returns a PetGetDto when petService.getById() is called with a valid Id")
    void shouldReturnPetGetDto_WhenGetByIdIsCalledWithValidId() throws Exception {

        PetGetDto persistedPetDto = persistPet(petCreateDto);

        MvcResult result = mockMvc.perform(get(PET_GET_BY_ID_URL,persistedPetDto.id())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PetGetDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), PetGetDto.class);

        assertThat(persistedPetDto)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(actual);
    }

    @Test
    @DisplayName("Throws PetNotFoundException when petService.getById() is called with an invalid Id")
    void shouldThrowPetNotFoundExceptionWhenGetByIdIsCalledWithInvalidId() throws Exception {

        MvcResult result = mockMvc.perform(get(PET_GET_BY_ID_URL,"InvalidId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);

        assertEquals(format(PET_NOT_FOUND, "InvalidId"), error.message());
    }


    // update


    @Test
    @DisplayName("Should update Pet and return a PetGetDto with correct fields when a valid ID and UpdateDto are provided")
    void shouldUpdatePetAndReturnCorrectPetGetDto_whenPetIdAndUpdateDtoAreValid() throws Exception {

        PetGetDto persistedPetDto = persistPet(petCreateDto);
        String petId = persistedPetDto.id();

        MvcResult result = mockMvc.perform(put(PET_UPDATE_URL,petId)
                .content(objectMapper.writeValueAsString(updateDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PetGetDto updatedPet = objectMapper.readValue(result.getResponse().getContentAsString(), PetGetDto.class);

        assertEquals(Ages.SENIOR, updatedPet.age());
        assertEquals("https://www.updatedimages.com", updatedPet.imageUrl());
        assertTrue(updatedPet.isAdopted());
    }

    @Test
    @DisplayName("Check If pet was updated in db")
    void test() throws Exception {

        PetGetDto persistedPetDto = persistPet(petCreateDto);
        String petId = persistedPetDto.id();

        mockMvc.perform(put(PET_UPDATE_URL,petId)
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new PetNotFoundException(format(PET_NOT_FOUND, petId)));

        assertEquals(Ages.SENIOR, pet.getAge());
        assertEquals("https://www.updatedimages.com", pet.getImageUrl());
        assertTrue(pet.isAdopted());
    }

    @Test
    @DisplayName("PetNotFound")
    void test1() throws Exception {

        MvcResult result = mockMvc.perform(put(PET_UPDATE_URL, "InvalidId")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);

        assertEquals(format(PET_NOT_FOUND, "InvalidId"), error.message());
    }

    @Test
    @DisplayName("InvalidDto")
    void test2() throws Exception {

        PetUpdateDto invalidDto = PetUpdateDto.builder()
                .age("INVALID")
                .build();

        PetGetDto persistedPetDto = persistPet(petCreateDto);
        String petId = persistedPetDto.id();

        MvcResult result = mockMvc.perform(put(PET_UPDATE_URL, petId)
                        .content(objectMapper.writeValueAsString(invalidDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);

        assertEquals(Map.of("age", MessageFormat.format(AGE_INVALID, "INVALID")), error.validationIssue());
    }

    @Test
    @DisplayName("No Body")
    void test3() throws Exception {

        PetGetDto persistedPetDto = persistPet(petCreateDto);
        String petId = persistedPetDto.id();

        MvcResult result = mockMvc.perform(put(PET_UPDATE_URL,petId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);

        assertTrue(error.message().contains("Required request body is missing"));
    }


    // delete


    @Test
    @DisplayName("Delete returns string")
    void test4() throws Exception {

        PetGetDto persistedPetDto = persistPet(petCreateDto);
        String petId = persistedPetDto.id();

        mockMvc.perform(delete(PET_DELETE_URL, petId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(PET_DELETE_MESSAGE, petId)));
    }

    @Test
    @DisplayName("Check if pet was deleted in db")
    void test5() throws Exception {

        PetGetDto persistedPetDto = persistPet(petCreateDto);
        String petId = persistedPetDto.id();

        mockMvc.perform(delete(PET_DELETE_URL, petId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(Optional.empty(), petRepository.findById(petId));
    }

    @Test
    @DisplayName("PetNotFound")
    void test6() throws Exception {

        MvcResult result = mockMvc.perform(delete(PET_DELETE_URL,"InvalidId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);

        assertEquals(format(PET_NOT_FOUND, "InvalidId"), error.message());
    }


    // add list


    @Test
    @DisplayName("Should persist a list Pet and return a String")
    void test7() throws Exception {

        List<PetCreateDto> dtoList = List.of(petCreateDto, petCreateDto2);

        mockMvc.perform(post(PET_ADD_LIST_URL)
                        .content(objectMapper.writeValueAsString(dtoList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(PET_LIST_ADDED_SUCCESS));
    }

    @Test
    @DisplayName("Confirm all the pets were added")
    void test8() throws Exception {

        List<PetCreateDto> dtoList = List.of(petCreateDto, petCreateDto2);

        mockMvc.perform(post(PET_ADD_LIST_URL)
                        .content(objectMapper.writeValueAsString(dtoList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        assertEquals(2, petRepository.findAll().size());
    }

    @Test
    @DisplayName("DataIntegrityViolationException in any element")
    void test9() throws Exception {

        List<PetCreateDto> dtoList = List.of(petCreateDto, petCreateDto2, petCreateDto);

        MvcResult result = mockMvc.perform(post(PET_ADD_LIST_URL)
                        .content(objectMapper.writeValueAsString(dtoList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);

        assertEquals("uniquepetnamespeciesorg", error.constraint());
        assertEquals(0, petRepository.findAll().size());
    }

    @Test
    @DisplayName("InvalidDto")
    void test10() throws Exception {

        PetCreateDto invalidDto = PetCreateDto.builder()
                .name("Fifas")
                .speciesId(speciesGetDto2.id())
                .primaryBreedId(primaryBreedGetDto2.id())
                .primaryColor(primaryColorGetDto.id())
                .gender("Male")
                .coat("Medium")
                .age("INVALID")
                .size("Medium")
                .description("Max is friendly dog!")
                .imageUrl("https://cat.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationId(organizationGetDto1.id())
                .build();

        List<PetCreateDto> dtoList = List.of(petCreateDto, petCreateDto2, invalidDto);

        MvcResult result = mockMvc.perform(post(PET_ADD_LIST_URL)
                        .content(objectMapper.writeValueAsString(dtoList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);

        assertEquals(Map.of("age", MessageFormat.format(AGE_INVALID, "INVALID")), error.validationIssue());
        assertEquals(0, petRepository.findAll().size());
    }

    @Test
    @DisplayName("No Body")
    void test11() throws Exception {

        MvcResult result = mockMvc.perform(post(PET_ADD_LIST_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);

        assertTrue(error.message().contains("Required request body is missing"));
    }


    @Test
    @DisplayName("Should return an empty list if pets table in the database is empty")
    void shouldReturnEmptyList_WhenSearchIsCalled_WhileDbIsEmpty() throws Exception {

        MvcResult result = mockMvc.perform(post(PET_SEARCH_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)))
                .andReturn();

        List<PetGetDto> actualList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNotNull(actualList);
    }
}