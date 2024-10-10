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
import com.petadoption.center.dto.pet.PetUpdateDto;
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
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Stream;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.*;
import static com.petadoption.center.util.Messages.NOT_FOUND;
import static com.petadoption.center.util.Messages.PET_WITH_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    private SpeciesGetDto speciesGetDto;
    private String speciesId;
    private BreedGetDto primaryBreedGetDto;
    private String primaryBreedId;
    private BreedGetDto secondaryBreedGetDto;
    private String secondaryBreedId;
    private ColorGetDto primaryColorGetDto;
    private String primaryColorId;
    private ColorGetDto secondaryColorGetDto;
    private String secondaryColorId;
    private ColorGetDto tertiaryColorGetDto;
    private String tertiaryColorId;
    private OrganizationGetDto organizationGetDto;
    private String organizationId;
    private String petId;
    private PetGetDto petGetDto;
    private static PetCreateDto petCreateDtoAllFields;
    private static PetCreateDto petCreateDtoNullFields;
    private static PetGetDto expectedAllFieldsDto;
    private static PetGetDto expectedNullFieldsDto;
    private PetUpdateDto petUpdateDto;
    private PetSearchCriteria petSearchCriteria;
    private final String URL = "/api/v1/pet/";


    @BeforeAll
    void setup() throws Exception {

        persistSpecies();
        persistPrimaryBreed();
        persistSecondaryBreed();
        persistPrimaryColor();
        persistSecondaryColor();
        persistTertiaryColor();
        persistOrganization();

        Attributes attributes = createAttributes();

        petUpdateDto = PetUpdateDto.builder()
                .size("Extra Large")
                .age("Senior")
                .build();

        petCreateDtoAllFields = PetCreateDto.builder()
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

        petCreateDtoNullFields = PetCreateDto.builder()
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

        expectedAllFieldsDto = PetGetDto.builder()
                .name("Max")
                .speciesDto(speciesGetDto)
                .primaryBreedDto(primaryBreedGetDto)
                .secondaryBreedDto(secondaryBreedGetDto)
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
                .organizationDto(organizationGetDto)
                .build();

        expectedNullFieldsDto = PetGetDto.builder()
                .name("Max")
                .speciesDto(speciesGetDto)
                .primaryBreedDto(primaryBreedGetDto)
                .primaryColorDto(primaryColorGetDto)
                .gender(Genders.MALE)
                .coat(Coats.MEDIUM)
                .size(Sizes.LARGE)
                .age(Ages.ADULT)
                .description("Max is friendly dog!")
                .imageUrl("https://dog.com")
                .isAdopted(false)
                .attributes(attributes)
                .organizationDto(organizationGetDto)
                .build();
    }

    private void persistSpecies() throws Exception {
        SpeciesCreateDto speciesCreateDto = speciesCreateDto();

        MvcResult result = mockMvc.perform(post("/api/v1/species/")
                        .content(objectMapper.writeValueAsString(speciesCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        speciesGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), SpeciesGetDto.class);
        speciesId = speciesGetDto.id();
    }

    private void persistPrimaryBreed() throws Exception {
        BreedCreateDto breedCreateDto = primaryBreedCreateDto(speciesId);

        MvcResult result = mockMvc.perform(post("/api/v1/breed/")
                        .content(objectMapper.writeValueAsString(breedCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        primaryBreedGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto.class);
        primaryBreedId = primaryBreedGetDto.id();
    }

    private void persistSecondaryBreed() throws Exception {
        BreedCreateDto breedCreateDto = secondaryBreedCreateDto(speciesId);

        MvcResult result = mockMvc.perform(post("/api/v1/breed/")
                        .content(objectMapper.writeValueAsString(breedCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        secondaryBreedGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto.class);
        secondaryBreedId = secondaryBreedGetDto.id();
    }

    private void persistPrimaryColor() throws Exception {
        ColorCreateDto colorCreateDto = primaryColorCreateDto();

        MvcResult result = mockMvc.perform(post("/api/v1/color/")
                        .content(objectMapper.writeValueAsString(colorCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        primaryColorGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto.class);
        primaryColorId = primaryColorGetDto.id();
    }

    private void persistSecondaryColor() throws Exception {
        ColorCreateDto colorCreateDto = secondaryColorCreateDto();

        MvcResult result = mockMvc.perform(post("/api/v1/color/")
                        .content(objectMapper.writeValueAsString(colorCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        secondaryColorGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto.class);
        secondaryColorId = secondaryColorGetDto.id();
    }

    private void persistTertiaryColor() throws Exception {
        ColorCreateDto colorCreateDto = tertiaryColorCreateDto();

        MvcResult result = mockMvc.perform(post("/api/v1/color/")
                        .content(objectMapper.writeValueAsString(colorCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        tertiaryColorGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto.class);
        tertiaryColorId = tertiaryColorGetDto.id();
    }

    private void persistOrganization() throws Exception {
        OrganizationCreateDto organizationCreateDto = organizationCreateDto();

        MvcResult result = mockMvc.perform(post("/api/v1/organization/")
                        .content(objectMapper.writeValueAsString(organizationCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        organizationGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), OrganizationGetDto.class);
        organizationId = organizationGetDto.id();
    }

    private static Stream<Arguments> provideDtos() {
        return Stream.of(
                arguments(petCreateDtoAllFields, expectedAllFieldsDto),
                arguments(petCreateDtoNullFields, expectedNullFieldsDto)
        );
    }

    private MvcResult persistPet(PetCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post(URL + "addSingle")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        petGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), PetGetDto.class);
        petId = petGetDto.id();
        return result;
    }


    // create


    @ParameterizedTest
    @MethodSource("provideDtos")
    @DisplayName("Should persist a new Pet and return a PetGetDto with correct fields when a valid PetCreateDto is provided")
    void shouldCreatePetAndReturnCorrectPetGetDto_whenPetIsValid(PetCreateDto petCreateDto, PetGetDto expected) throws Exception {

        persistPet(petCreateDto);

        assertThat(petGetDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(expected);
        assertNotNull(petGetDto.createdAt());
        assertTrue(petGetDto.id().matches("^[0-9a-fA-F-]{36}$"));
    }

    @ParameterizedTest
    @MethodSource("provideDtos")
    @DisplayName("Persisted Pet should match the Pet retrieved from the database")
    void shouldMatchPersistedPetWithRetrievedPet_whenPetIsValid(PetCreateDto petCreateDto) throws Exception {

        persistPet(petCreateDto);

        Pet savedPet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException(PET_WITH_ID + petId + NOT_FOUND));
        PetGetDto savedPetDto = PetConverter.toDto(savedPet);

        assertThat(petGetDto)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(savedPetDto);
    }

    @Test
    @DisplayName("Should throw DataIntegrityViolationException when trying to save an existing Pet")
    void shouldThrowDataIntegrityViolationException_WhenTryingToSaveExistingPet() throws Exception {

        persistPet(petCreateDtoAllFields);

        MvcResult result = mockMvc.perform(post(URL + "addSingle")
                        .content(objectMapper.writeValueAsString(petCreateDtoAllFields))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
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

        persistPet(petCreateDtoAllFields);

        MvcResult result = mockMvc.perform(get(URL + "id/{id}", petId))
                .andExpect(status().isOk())
                .andReturn();

        PetGetDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), PetGetDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(expectedAllFieldsDto);
        assertNotNull(actual.createdAt());
        assertTrue(petGetDto.id().matches("^[0-9a-fA-F-]{36}$"));
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