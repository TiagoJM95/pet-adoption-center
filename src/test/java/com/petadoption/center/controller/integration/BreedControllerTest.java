package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
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

import static com.petadoption.center.util.Messages.BREED_WITH_ID;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class BreedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private BreedGetDto breedGetDto;
    private BreedCreateDto breedCreateDto;
    private BreedUpdateDto breedUpdateDto;

    private Species species;

    @BeforeEach
    void setUp() {

        species = new Species(1L, "Dog");

        breedCreateDto = new BreedCreateDto(
                "Golden Retriever",
                1L
        );

        breedUpdateDto = new BreedUpdateDto(
                "Weimaraner"
        );

        breedGetDto = new BreedGetDto(
                1L,
                "Golden Retriever",
                "Dog"
        );
    }

    @Test
    @DisplayName("Test create breed is working correctly")
    void createBreed() throws Exception {

        mockMvc.perform(post("/api/v1/pet-species/")
                        .content(objectMapper.writeValueAsString(species))
                        .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/api/v1/breed/")
                        .content(objectMapper.writeValueAsString(breedCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(breedGetDto.name())))
                .andExpect(jsonPath("$.specie", is(breedGetDto.specie())));

    }


    @Test
    @DisplayName("Test if get all breeds works correctly")
    @DirtiesContext
    void getAllBreeds() throws Exception {

        createBreed();

        mockMvc.perform(get("/api/v1/breed/")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].name", is(breedGetDto.name())))
                .andExpect(jsonPath("$[0].specie", is(breedGetDto.specie())));
    }


    @Test
    @DisplayName("Test if get breed by id works correctly")
    @DirtiesContext
    void getBreedById() throws Exception {

        createBreed();

        mockMvc.perform(get("/api/v1/breed/id/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(breedGetDto.id().intValue())))
                .andExpect(jsonPath("$.name", is(breedGetDto.name())))
                .andExpect(jsonPath("$.specie", is(breedGetDto.specie())));
    }


    @Test
    @DisplayName("Test if update breed works correctly")
    @DirtiesContext
    void updateBreed() throws Exception {

        createBreed();

        mockMvc.perform(put("/api/v1/breed/update/{id}", 1L)
                        .content(objectMapper.writeValueAsString(breedUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(breedUpdateDto.name())));
    }

    @Test
    @DisplayName("Test if delete breed works correctly")
    @DirtiesContext
    void deleteBreed() throws Exception {

        createBreed();

        mockMvc.perform(delete("/api/v1/breed/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(BREED_WITH_ID + 1L + DELETE_SUCCESS));
    }
}
