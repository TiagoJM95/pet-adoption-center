package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.petadoption.center.aspect.Error;


import static com.petadoption.center.testUtils.ConstantsURL.*;
import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.util.Messages.BREED_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BreedControllerTest extends AbstractIntegrationTest {

    private BreedCreateDto breedCreateDto;
    private BreedCreateDto anotherBreedCreateDto;
    private BreedUpdateDto breedUpdateDto;


    @BeforeAll
    void setUp() throws Exception {
        String speciesId = persistSpecies(speciesCreateDto()).id();
        breedCreateDto = primaryBreedCreateDto(speciesId);
        breedUpdateDto = breedUpdateDto();
        anotherBreedCreateDto = otherPrimaryBreedCreateDto(speciesId);
    }

    @AfterEach
    void tearDown() {
        breedRepository.deleteAll();
        clearRedisCache();
    }


    @Test
    @DisplayName("Test if create breed return BreedGetDto of the created Breed")
    void createBreedAndReturnBreedGetDto() throws Exception {

        BreedGetDto breedCreatedGetDto = persistBreed(breedCreateDto);

        var result = mockMvc.perform(get(BREED_GET_BY_ID_URL, breedCreatedGetDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BreedGetDto breedGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto.class);

        assertThat(breedGetDto)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(breedCreatedGetDto);
    }

    @Test
    @DisplayName("Test if create breed with duplicate fields of another breed throws DataIntegrityViolationException")
    void createBreedThrowsDataIntegrityViolationExceptionIfDuplicateFields() throws Exception {

        persistBreed(breedCreateDto);

        var result = mockMvc.perform(post(BREED_GET_ALL_OR_CREATE_URL)
                        .content(objectMapper.writeValueAsString(breedCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error.constraint()).isEqualTo("uniquebreedname");
    }

    @Test
    @DisplayName("Test if create breed with no body throw HttpMessageNotReadableException")
    void shouldThrowHttpMessageNotReadableException_WhenCreateIsCalledWithNoBody() throws Exception {

        var result = mockMvc.perform(post(BREED_GET_ALL_OR_CREATE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertTrue(error.message().contains("Required request body is missing"));
    }

    @Test
    @DisplayName("Test if get all breeds return a list of BreedGetDto")
    void getAllReturnListOfBreedGetDto() throws Exception {

        BreedGetDto breedCreatedGetDto = persistBreed(breedCreateDto);

        var result = mockMvc.perform(get(BREED_GET_ALL_OR_CREATE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BreedGetDto[] breedGetDtoArray = objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto[].class);
        assertThat(breedGetDtoArray).hasSize(1);
        assertThat(breedGetDtoArray[0])
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(breedCreatedGetDto);
    }

    @Test
    @DisplayName("Test if get all breeds pagination return the number of elements requested")
    void getAllBreedsReturnNumberOfElementsOfRequest() throws Exception {

        BreedGetDto firstBreed = persistBreed(breedCreateDto);

        persistBreed(anotherBreedCreateDto);

        var result = mockMvc.perform(get(BREED_GET_ALL_OR_CREATE_URL)
                        .param("page", "0")
                        .param("size", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BreedGetDto[] breedGetDtoArray = objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto[].class);
        assertThat(breedGetDtoArray).hasSize(1);
        assertThat(breedGetDtoArray[0])
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(firstBreed);
    }

    @Test
    @DisplayName("Test if get all return empty list if no breeds in database")
    void getAllReturnEmptyList() throws Exception {

        var result = mockMvc.perform(get(BREED_GET_ALL_OR_CREATE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BreedGetDto[] breedGetDtoArray = objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto[].class);
        assertThat(breedGetDtoArray).isEmpty();
    }

    @Test
    @DisplayName("Test if get breed by id return the BreedGetDto of the requested Breed")
    void getByIdReturnBreedGetDtoOfRequestedBreed() throws Exception {

        BreedGetDto breedCreatedGetDto = persistBreed(breedCreateDto);

        var result = mockMvc.perform(get(BREED_GET_BY_ID_URL, breedCreatedGetDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BreedGetDto breedGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto.class);
        assertThat(breedGetDto)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(breedCreatedGetDto);
    }

    @Test
    @DisplayName("Test if get breed by id throws breed not found exception if breed does not exist")
    void getByIdThrowsBreedNotFoundException() throws Exception {

        mockMvc.perform(get(BREED_GET_BY_ID_URL, "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test if update breed change fields of Breed requested and return the updated BreedGetDto")
    void updateBreedChangeFieldsAndReturnUpdatedBreedGetDto() throws Exception {

        BreedGetDto expectedUpdatedBreedGetDto = BreedGetDto.builder()
                .name(breedUpdateDto.name())
                .build();

        BreedGetDto breedToUpdate = persistBreed(breedCreateDto);

        var result = mockMvc.perform(put(BREED_UPDATE_URL,breedToUpdate.id())
                        .content(objectMapper.writeValueAsString(breedUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BreedGetDto actualUpdatedBreedGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto.class);
        assertThat(actualUpdatedBreedGetDto)
                .usingRecursiveComparison()
                .ignoringFields("id", "speciesDto")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(expectedUpdatedBreedGetDto);


    }

    @Test
    @DisplayName("Test if update breed throws DataIntegrityViolationException if duplicate fields of an existing breed")
    void updateThrowsDataIntegrityViolationExceptionIfDuplicateFields() throws Exception {

        persistBreed(breedCreateDto);
        BreedGetDto breedToUpdate = persistBreed(anotherBreedCreateDto);

        BreedUpdateDto dto = BreedUpdateDto.builder()
                .name(breedCreateDto.name())
                .build();

        var result = mockMvc.perform(put(BREED_UPDATE_URL, breedToUpdate.id())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error.constraint()).isEqualTo("uniquebreedname");

    }

    @Test
    @DisplayName("Test if update breed throws breed not found exception")
    void updateThrowsBreedNotFoundException() throws Exception {

        mockMvc.perform(put(BREED_UPDATE_URL, "11111-11111-1111-1111")
                        .content(objectMapper.writeValueAsString(breedUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test if delete breed removes Breed from database and returns message of success")
    void deleteRemovesBreedAndReturnsMessage() throws Exception {

        BreedGetDto breedToDelete = persistBreed(breedCreateDto);

        mockMvc.perform(MockMvcRequestBuilders.delete(BREED_DELETE_URL,breedToDelete.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(BREED_DELETE_MESSAGE, breedToDelete.id())));
    }

    @Test
    @DisplayName("Test if delete breed throws breed not found exception if breed does not exist")
    void deleteThrowsBreedNotFoundExceptionIfBreedDoesNotExist() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(BREED_DELETE_URL, "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
