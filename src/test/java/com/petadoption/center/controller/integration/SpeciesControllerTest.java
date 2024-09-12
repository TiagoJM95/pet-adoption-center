package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.SPECIES_WITH_ID;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class SpeciesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SpeciesGetDto speciesGetDto;
    private SpeciesCreateDto speciesCreateDto;
    private SpeciesUpdateDto speciesUpdateDto;


    @BeforeEach
    void setUp() {
        speciesGetDto = new SpeciesGetDto(
                1L,
                "Dog");

        speciesCreateDto = new SpeciesCreateDto(
                "Dog");

        speciesUpdateDto = new SpeciesUpdateDto(
                "Cat");
    }

    @Test
    @DisplayName("Test create species is working correctly")
    @DirtiesContext
    void createSpecies() throws Exception {

        mockMvc.perform(post("/api/v1/pet-species/")
                        .content(objectMapper.writeValueAsString(speciesCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(speciesGetDto.name())));
    }

    @Test
    @DisplayName("Test if get all species works correctly")
    @DirtiesContext
    void getAllSpecies() throws Exception {

        createSpecies();

        mockMvc.perform(get("/api/v1/pet-species/")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(speciesGetDto.name())));

    }

    @Test
    @DisplayName("Test if get species by id works correctly")
    @DirtiesContext
    void getSpeciesById() throws Exception {

        createSpecies();

        mockMvc.perform(get("/api/v1/pet-species/id/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(speciesGetDto.name())));
    }

    @Test
    @DisplayName("Test if update species works correctly")
    @DirtiesContext
    void updateSpecies() throws Exception {

        createSpecies();

        mockMvc.perform(put("/api/v1/pet-species/update/{id}", 1L)
                        .content(objectMapper.writeValueAsString(speciesUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(speciesUpdateDto.name())));
    }

    @Test
    @DisplayName("Test if delete species works correctly")
    @DirtiesContext
    void deleteSpecies() throws Exception {

        createSpecies();

        mockMvc.perform(delete("/api/v1/pet-species/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(SPECIES_WITH_ID + 1L + DELETE_SUCCESS));
    }
}
