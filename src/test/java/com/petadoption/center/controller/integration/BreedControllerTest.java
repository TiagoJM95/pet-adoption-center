package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.petadoption.center.testUtils.TestDtoFactory.breedUpdateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.primaryBreedCreateDto;
import static com.petadoption.center.util.Messages.BREED_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class BreedControllerTest extends TestContainerConfig {

    private BreedGetDto breedGetDto;
    private BreedCreateDto breedCreateDto;
    private BreedUpdateDto breedUpdateDto;


    @BeforeAll
    void setUp() {
        String SpeciesId = helper.persistTestSpecies();
        breedCreateDto = primaryBreedCreateDto(SpeciesId);
        breedUpdateDto = breedUpdateDto();
    }

    @AfterEach
    void tearDown() {
        breedRepository.deleteAll();
    }
    @AfterAll
    void cleanSpecies(){
        speciesRepository.deleteAll();
    }

    private void persistBreed() throws Exception {

        var result = mockMvc.perform(post("/api/v1/breed/")
                        .content(objectMapper.writeValueAsString(breedCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(breedCreateDto.name())))
                .andReturn();

        breedGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto.class);
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

        persistBreed();

        List<BreedGetDto> breedGetDtos = List.of(breedGetDto);
        String expectedJson = objectMapper.writeValueAsString(breedGetDtos);

        mockMvc.perform(get("/api/v1/breed/")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }


    @Test
    @DisplayName("Test if get breed by id works correctly")
    void getById() throws Exception {

        persistBreed();

        String expectedJson = objectMapper.writeValueAsString(breedGetDto);

        mockMvc.perform(get("/api/v1/breed/id/{id}", breedGetDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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

        persistBreed();

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

        persistBreed();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/breed/delete/{id}",breedGetDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(BREED_DELETE_MESSAGE, breedGetDto.id())));
    }

    @Test
    @DisplayName("Test if delete breed throws breed not found exception")
    void deleteThrowsBreedNotFoundException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/breed/delete/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
