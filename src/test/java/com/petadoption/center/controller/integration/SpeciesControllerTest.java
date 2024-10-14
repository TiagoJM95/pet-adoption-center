package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.testUtils.TestPersistenceHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static com.petadoption.center.testUtils.TestDtoFactory.speciesCreateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.speciesUpdateDto;
import static com.petadoption.center.util.Messages.SPECIES_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class SpeciesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private static TestPersistenceHelper testPersistenceHelper;

    private SpeciesGetDto speciesGetDto;
    private SpeciesCreateDto speciesCreateDto;
    private SpeciesUpdateDto speciesUpdateDto;

    private String speciesId;

    @AfterAll
    static void cleanAll(){
        testPersistenceHelper.cleanAll();
    }

    @BeforeEach
    void setUp() {
        speciesCreateDto = speciesCreateDto();
        speciesUpdateDto = speciesUpdateDto();
    }

    @Test
    @DisplayName("Test create species is working correctly")
    void createSpecies() throws Exception {

       var result = mockMvc.perform(post("/api/v1/species/")
                        .content(objectMapper.writeValueAsString(speciesCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(speciesCreateDto.name())))
               .andReturn();

        SpeciesGetDto specieCreated = objectMapper.readValue(result.getResponse().getContentAsString(), SpeciesGetDto.class);

        speciesId = specieCreated.id();
        speciesGetDto = new SpeciesGetDto(speciesId, speciesCreateDto.name(), LocalDateTime.now());
    }

    @Test
    @DisplayName("Test if get all species works correctly")
    void getAllSpecies() throws Exception {

        createSpecies();

        mockMvc.perform(get("/api/v1/species/")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(speciesGetDto.name())));

    }

    @Test
    @DisplayName("Test if get species by id works correctly")
    void getSpeciesById() throws Exception {

        createSpecies();

        mockMvc.perform(get("/api/v1/species/id/{id}", speciesId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(speciesGetDto.name())));
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

        createSpecies();

        mockMvc.perform(put("/api/v1/species/update/{id}", speciesId)
                        .content(objectMapper.writeValueAsString(speciesUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(speciesUpdateDto.name())));
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

        createSpecies();

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
