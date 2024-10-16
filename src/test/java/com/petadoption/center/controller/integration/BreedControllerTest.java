package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.petadoption.center.aspect.Error;



import static com.petadoption.center.testUtils.TestDtoFactory.breedUpdateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.primaryBreedCreateDto;
import static com.petadoption.center.util.Messages.BREED_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class BreedControllerTest extends TestContainerConfig {

    private BreedCreateDto breedCreateDto;
    private BreedUpdateDto breedUpdateDto;
    private String breedId;


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


    private BreedGetDto persistBreed() throws Exception {

        var result = mockMvc.perform(post("/api/v1/breed/")
                        .content(objectMapper.writeValueAsString(breedCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BreedGetDto breedCreatedGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto.class);
        breedId = breedCreatedGetDto.id();
        return breedCreatedGetDto;
    }

    @Test
    @DisplayName("Test create breed is working correctly")
    void createBreed() throws Exception {

        BreedGetDto breedCreatedGetDto = persistBreed();

        var result = mockMvc.perform(get("/api/v1/breed/id/{id}", breedId)
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
    @DisplayName("Test if create breed throws DataIntegrityViolationException")
    void createBreedThrowsDataIntegrityViolationException() throws Exception {

        persistBreed();

        var result = mockMvc.perform(post("/api/v1/breed/")
                        .content(objectMapper.writeValueAsString(breedCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error.constraint()).isEqualTo("uniquebreedname");
    }

    @Test
    @DisplayName("Test if throw HttpMessageNotReadableException if request body is empty")
    void shouldThrowHttpMessageNotReadableException_WhenCreateIsCalledWithNoBody() throws Exception {

        var result = mockMvc.perform(post("/api/v1/breed/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertTrue(error.message().contains("Required request body is missing"));
    }

    @Test
    @DisplayName("Test if get all breeds works correctly")
    void getAll() throws Exception {

        BreedGetDto breedCreatedGetDto = persistBreed();

        var result = mockMvc.perform(get("/api/v1/breed/")
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
    @DisplayName("Test if get breed by id works correctly")
    void getById() throws Exception {

        BreedGetDto breedCreatedGetDto = persistBreed();

        var result = mockMvc.perform(get("/api/v1/breed/id/{id}", breedId)
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
    @DisplayName("Test if get breed by id throws breed not found exception")
    void getByIdThrowsBreedNotFoundException() throws Exception {

        mockMvc.perform(get("/api/v1/breed/id/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test if update breed works correctly")
    void update() throws Exception {

        BreedGetDto expectedUpdatedBreedGetDto = BreedGetDto.builder()
                .name(breedUpdateDto.name())
                .build();

        persistBreed();

        var result = mockMvc.perform(put("/api/v1/breed/update/{id}",breedId)
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

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/breed/delete/{id}",breedId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(BREED_DELETE_MESSAGE, breedId)));
    }

    @Test
    @DisplayName("Test if delete breed throws breed not found exception")
    void deleteThrowsBreedNotFoundException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/breed/delete/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
