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



import static com.petadoption.center.testUtils.TestDtoFactory.speciesCreateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.speciesUpdateDto;
import static com.petadoption.center.util.Messages.SPECIES_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SpeciesControllerTest extends TestContainerConfig{

    private SpeciesCreateDto speciesCreateDto;
    private SpeciesUpdateDto speciesUpdateDto;
    private String speciesId;

    @BeforeEach
    void setUp() {
        speciesCreateDto = speciesCreateDto();
        speciesUpdateDto = speciesUpdateDto();
    }

    @AfterEach
    void tearDown() {
        speciesRepository.deleteAll();
    }

    private SpeciesGetDto persistSpecies() throws Exception {

        var result = mockMvc.perform(post("/api/v1/species/")
                        .content(objectMapper.writeValueAsString(speciesCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        SpeciesGetDto createResultDto = objectMapper.readValue(result.getResponse().getContentAsString(), SpeciesGetDto.class);
        speciesId = createResultDto.id();
        return createResultDto;
    }

    @Test
    @DisplayName("Test create species is working correctly")
    void createSpecies() throws Exception {

        SpeciesGetDto expectedSpeciesGetDto = SpeciesGetDto.builder()
                .name(speciesCreateDto.name())
                .build();

        SpeciesGetDto createdSpeciesDto = persistSpecies();

        assertThat(createdSpeciesDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(expectedSpeciesGetDto);

        assertNotNull(createdSpeciesDto.createdAt());
        assertTrue(createdSpeciesDto.id().matches("^[0-9a-fA-F-]{36}$"));
    }

    @Test
    @DisplayName("Test if create species throws DataIntegrityViolationException")
    void createSpeciesThrowsDataIntegrityViolationException() throws Exception {
        persistSpecies();

        var result = mockMvc.perform(post("/api/v1/species/")
                        .content(objectMapper.writeValueAsString(speciesCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error.constraint()).isEqualTo("uniquespeciesname");
    }

    @Test
    @DisplayName("Test if throw HttpMessageNotReadableException if request body is empty")
    void shouldThrowHttpMessageNotReadableException_WhenCreateIsCalledWithNoBody() throws Exception {

        var result = mockMvc.perform(post("/api/v1/species/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertTrue(error.message().contains("Required request body is missing"));
    }

    @Test
    @DisplayName("Test if get all species works correctly")
    void getAllSpecies() throws Exception {

        SpeciesGetDto createdSpeciesDto = persistSpecies();

        var result =mockMvc.perform(get("/api/v1/species/")
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
    @DisplayName("Test if get species by id works correctly")
    void getSpeciesById() throws Exception {

        SpeciesGetDto createdSpeciesDto = persistSpecies();

        var result = mockMvc.perform(get("/api/v1/species/id/{id}", speciesId)
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
    @DisplayName("Test if get species by id throws not found exception")
    void getSpeciesByIdThrowsNotFoundException() throws Exception {

        mockMvc.perform(get("/api/v1/species/id/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test if update species works correctly")
    void updateSpecies() throws Exception {

        SpeciesGetDto expectedUpdatedSpecies = SpeciesGetDto.builder()
                .name(speciesUpdateDto.name())
                .build();

        persistSpecies();
        var result = mockMvc.perform(put("/api/v1/species/update/{id}", speciesId)
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
    @DisplayName("Test if update species throws not found exception")
    void updateSpeciesThrowsNotFoundException() throws Exception {

        mockMvc.perform(put("/api/v1/species/update/{id}", "11111-11111-1111-1111")
                        .content(objectMapper.writeValueAsString(speciesUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test if delete species works correctly")
    void delete() throws Exception {

        persistSpecies();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/species/delete/{id}", speciesId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(SPECIES_DELETE_MESSAGE, speciesId)));
    }

    @Test
    @DisplayName("Test if delete species throws not found exception")
    void deleteThrowsNotFoundException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/species/delete/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
