package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.petadoption.center.aspect.Error;


import static com.petadoption.center.testUtils.ConstantsURL.*;
import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.util.Messages.SPECIES_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SpeciesControllerTest extends AbstractIntegrationTest {

    private SpeciesCreateDto speciesCreateDto;
    private SpeciesUpdateDto speciesUpdateDto;

    @BeforeEach
    void setUp() {
        speciesCreateDto = speciesCreateDto();
        speciesUpdateDto = speciesUpdateDto();
    }

    @AfterEach
    void tearDown() {
        speciesRepository.deleteAll();
        clearRedisCache();
    }

    @Test
    @DisplayName("Test if create species returns the created species SpeciesGetDto")
    void createSpeciesReturnsTheCreatedSpeciesSpeciesGetDto() throws Exception {

        SpeciesGetDto expectedSpeciesGetDto = SpeciesGetDto.builder()
                .name(speciesCreateDto.name())
                .build();

        SpeciesGetDto createdSpeciesDto = persistSpecies(speciesCreateDto);

        assertThat(createdSpeciesDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(expectedSpeciesGetDto);

        assertNotNull(createdSpeciesDto.createdAt());
        assertTrue(createdSpeciesDto.id().matches("^[0-9a-fA-F-]{36}$"));
    }

    @Test
    @DisplayName("Test if create species with duplicate fields of an existing species throws DataIntegrityViolationException")
    void createSpeciesThrowsDataIntegrityViolationExceptionIfSpeciesFieldsAlreadyExists() throws Exception {
        persistSpecies(speciesCreateDto);

        var result = mockMvc.perform(post(SPECIES_GET_ALL_OR_CREATE_URL)
                        .content(objectMapper.writeValueAsString(speciesCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error.constraint()).isEqualTo("uniquespeciesname");
    }

    @Test
    @DisplayName("Test if create species with empty request body throws HttpMessageNotReadableException")
    void shouldThrowHttpMessageNotReadableException_WhenCreateIsCalledWithNoBody() throws Exception {

        var result = mockMvc.perform(post(SPECIES_GET_ALL_OR_CREATE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertTrue(error.message().contains("Required request body is missing"));
    }

    @Test
    @DisplayName("Test if get all species returns list of SpeciesGetDto")
    void getAllSpeciesReturnsListOfSpeciesGetDto() throws Exception {

        SpeciesGetDto createdSpeciesDto = persistSpecies(speciesCreateDto);

        var result =mockMvc.perform(get(SPECIES_GET_ALL_OR_CREATE_URL)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        SpeciesGetDto[] speciesGetDtoArray = objectMapper.readValue(result.getResponse().getContentAsString(), SpeciesGetDto[].class);
        assertThat(speciesGetDtoArray).hasSize(1);
        assertThat(speciesGetDtoArray[0])
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(createdSpeciesDto);

    }

    @Test
    @DisplayName("Test if get all species pagination returns the number of elements requested")
    void getAllSpeciesReturnNumberOfElementsOfRequest() throws Exception {

        SpeciesGetDto firstSpecies = persistSpecies(speciesCreateDto);
        persistSpecies(otherSpeciesCreateDto());

        var result = mockMvc.perform(get(SPECIES_GET_ALL_OR_CREATE_URL)
                        .param("page", "0")
                        .param("size", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        SpeciesGetDto[] speciesGetDtoArray = objectMapper.readValue(result.getResponse().getContentAsString(), SpeciesGetDto[].class);
        assertThat(speciesGetDtoArray).hasSize(1);
        assertThat(speciesGetDtoArray[0])
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(firstSpecies);
    }

    @Test
    @DisplayName("Test if get all return a empty list if no species in database")
    void getAllReturnEmptyList() throws Exception {

       var result = mockMvc.perform(get(SPECIES_GET_ALL_OR_CREATE_URL)
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andReturn();

       SpeciesGetDto[] speciesGetDtoArray = objectMapper.readValue(result.getResponse().getContentAsString(), SpeciesGetDto[].class);
       assertThat(speciesGetDtoArray).isEmpty();
    }

    @Test
    @DisplayName("Test if get species by id return SpeciesGetDto of the requested species")
    void getSpeciesByIdReturnsTheRequestedSpeciesSpeciesGetDto() throws Exception {

        SpeciesGetDto createdSpeciesDto = persistSpecies(speciesCreateDto);

        var result = mockMvc.perform(get(SPECIES_GET_BY_ID_URL, createdSpeciesDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        SpeciesGetDto getResultDto = objectMapper.readValue(result.getResponse().getContentAsString(), SpeciesGetDto.class);
        assertThat(getResultDto)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(createdSpeciesDto);

    }

    @Test
    @DisplayName("Test if get species by id throws not found exception if species does not exist")
    void getSpeciesByIdThrowsNotFoundExceptionIfSpeciesDoesNotExist() throws Exception {

        mockMvc.perform(get(SPECIES_GET_BY_ID_URL, "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test if update species changes the fields of the requested species and returns the updated SpeciesGetDto")
    void updateSpeciesReturnsTheUpdatedSpeciesSpeciesGetDto() throws Exception {

        SpeciesGetDto expectedUpdatedSpecies = SpeciesGetDto.builder()
                .name(speciesUpdateDto.name())
                .build();

        SpeciesGetDto speciesToUpdate = persistSpecies(speciesCreateDto);

        var result = mockMvc.perform(put(SPECIES_UPDATE_URL, speciesToUpdate.id())
                        .content(objectMapper.writeValueAsString(speciesUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        SpeciesGetDto updateResultDto = objectMapper.readValue(result.getResponse().getContentAsString(), SpeciesGetDto.class);

        assertThat(updateResultDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(expectedUpdatedSpecies);

        assertNotNull(updateResultDto.createdAt());
        assertTrue(updateResultDto.id().matches("^[0-9a-fA-F-]{36}$"));
    }

    @Test
    @DisplayName("Test if update species throws DataIntegrityViolationException duplicate fields of an existing species")
    void updateSpeciesThrowsDataIntegrityViolationExceptionIfSpeciesFieldsAlreadyExists() throws Exception {

        persistSpecies(speciesCreateDto);

        SpeciesGetDto speciesToUpdate = persistSpecies(otherSpeciesCreateDto());

        SpeciesUpdateDto dto = SpeciesUpdateDto.builder()
                .name(speciesCreateDto.name())
                .build();

        var result = mockMvc.perform(put(SPECIES_UPDATE_URL, speciesToUpdate.id())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertEquals(error.constraint(), "uniquespeciesname");
    }

    @Test
    @DisplayName("Test if update species throws not found exception if species does not exist")
    void updateSpeciesThrowsNotFoundExceptionIfSpeciesDoesNotExist() throws Exception {

        mockMvc.perform(put(SPECIES_UPDATE_URL, "11111-11111-1111-1111")
                        .content(objectMapper.writeValueAsString(speciesUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test if delete species removes Species from database and returns message of success")
    void deleteRemovesSpeciesAndReturnsSuccessMessage() throws Exception {

        SpeciesGetDto speciesToDelete = persistSpecies(speciesCreateDto);

        mockMvc.perform(MockMvcRequestBuilders.delete(SPECIES_DELETE_URL, speciesToDelete.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(SPECIES_DELETE_MESSAGE, speciesToDelete.id())));
    }

    @Test
    @DisplayName("Test if delete species throws not found exception if species does not exist")
    void deleteThrowsNotFoundExceptionIfSpeciesDoesNotExist() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(SPECIES_DELETE_URL, "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}