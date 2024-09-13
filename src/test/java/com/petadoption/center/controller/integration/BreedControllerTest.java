package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
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
    private String breedId;
    private Species species;

    @BeforeEach
    void setUp() {
        species = new Species("123123-12312312-3123", "Dog");
        breedCreateDto = new BreedCreateDto("Golden Retriever", "12312312-1232351");
        breedUpdateDto = new BreedUpdateDto("Weimaraner");
    }

    @Test
    @DisplayName("Test create breed is working correctly")
    @DirtiesContext
    void createBreed() throws Exception {

        mockMvc.perform(post("/api/v1/pet-species/")
                        .content(objectMapper.writeValueAsString(species))
                        .contentType(MediaType.APPLICATION_JSON));

        var result = mockMvc.perform(post("/api/v1/breed/")
                        .content(objectMapper.writeValueAsString(breedCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(breedGetDto.name())))
                .andExpect(jsonPath("$.species", is(breedGetDto.species())))
                .andReturn();

        BreedGetDto breedCreated = objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto.class);
        breedId = breedCreated.id();
        breedGetDto = new BreedGetDto(breedId, breedCreateDto.name(), species.getName());
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
                .andExpect(jsonPath("$[0].species", is(breedGetDto.species())));
    }


    @Test
    @DisplayName("Test if get breed by id works correctly")
    @DirtiesContext
    void getBreedById() throws Exception {

        createBreed();

        mockMvc.perform(get("/api/v1/breed/id/{id}", breedId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(breedGetDto.id())))
                .andExpect(jsonPath("$.name", is(breedGetDto.name())))
                .andExpect(jsonPath("$.species", is(breedGetDto.species())));
    }


    @Test
    @DisplayName("Test if update breed works correctly")
    @DirtiesContext
    void updateBreed() throws Exception {

        createBreed();

        mockMvc.perform(put("/api/v1/breed/update/{id}",breedId)
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

        mockMvc.perform(delete("/api/v1/breed/delete/{id}",breedId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(BREED_WITH_ID + breedId + DELETE_SUCCESS));
    }
}
