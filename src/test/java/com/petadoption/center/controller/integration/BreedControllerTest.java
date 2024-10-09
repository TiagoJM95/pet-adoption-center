package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.testUtils.TestPersistenceHelper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.petadoption.center.testUtils.TestDtoFactory.breedCreateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.breedUpdateDto;
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

    @Autowired
    private TestPersistenceHelper testPersistenceHelper;

    private BreedGetDto breedGetDto;
    private BreedCreateDto breedCreateDto;
    private BreedUpdateDto breedUpdateDto;

    @BeforeEach
    void setUp() {
        String SpeciesId = testPersistenceHelper.persistTestSpecies();
        breedCreateDto = breedCreateDto(SpeciesId);
        breedUpdateDto = breedUpdateDto();
    }

    @Test
    @DisplayName("Test create breed is working correctly")
    void createBreed() throws Exception {

        var result = mockMvc.perform(post("/api/v1/breed/")
                        .content(objectMapper.writeValueAsString(breedCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(breedCreateDto.name())))
                .andReturn();

        breedGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto.class);
    }


    @Test
    @DisplayName("Test if get all breeds works correctly")
    void getAll() throws Exception {

        createBreed();

        List<BreedGetDto> breedGetDtos = List.of(breedGetDto);
        String expectedJson = objectMapper.writeValueAsString(breedGetDtos);

        mockMvc.perform(get("/api/v1/breed/")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(content().json(expectedJson));
    }


    @Test
    @DisplayName("Test if get breed by id works correctly")
    void getById() throws Exception {

        createBreed();

        String expectedJson = objectMapper.writeValueAsString(breedGetDto);

        mockMvc.perform(get("/api/v1/breed/id/{id}", breedGetDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }


    @Test
    @DisplayName("Test if get breed by id throws breed not found exception")
    void getByIdThrowsBreedNotFoundException() throws Exception {

        mockMvc.perform(get("/api/v1/breed/id/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Test if update breed works correctly")
    void update() throws Exception {

        createBreed();

        mockMvc.perform(put("/api/v1/breed/update/{id}",breedGetDto.id())
                        .content(objectMapper.writeValueAsString(breedUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(breedUpdateDto.name())));
    }

    @Test
    @DisplayName("Test if update breed throws breed not found exception")
    void updateThrowsBreedNotFoundException() throws Exception {

        mockMvc.perform(put("/api/v1/breed/update/{id}", "11111-11111-1111-1111")
                        .content(objectMapper.writeValueAsString(breedUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test if delete breed works correctly")
    void delete() throws Exception {

        createBreed();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/breed/delete/{id}",breedGetDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(BREED_WITH_ID + breedGetDto.id() + DELETE_SUCCESS));
    }

    @Test
    @DisplayName("Test if delete breed throws breed not found exception")
    void deleteThrowsBreedNotFoundException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/breed/delete/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
