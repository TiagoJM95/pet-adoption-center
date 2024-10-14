package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.petadoption.center.testUtils.TestDtoFactory.primaryColorCreateDto;
import static com.petadoption.center.util.Messages.COLOR_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ColorControllerTest extends TestContainerConfig {

    private ColorGetDto colorGetDto;
    private ColorCreateDto colorCreateDto;


    @BeforeEach
    void setUp() {
        colorCreateDto = primaryColorCreateDto();
    }

    @AfterEach
    void tearDown() {
        colorRepository.deleteAll();
    }

    private String persistColor() throws Exception {
        return helper.persistTestPrimaryColor();
    }

    @Test
    @DisplayName("Test if create color works correctly")
    void createColor() throws Exception {

      var result =  mockMvc.perform(post("/api/v1/color/")
                        .content(objectMapper.writeValueAsString(colorCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(colorCreateDto.name())))
                .andReturn();

      colorGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto.class);
    }

    @Test
    @DisplayName("Test if get all colors works correctly")
    void getAll() throws Exception {

        String id = persistColor();

        mockMvc.perform(get("/api/v1/color/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(id)))
                .andExpect(jsonPath("$[0].name", is(colorCreateDto.name())));
    }

    @Test
    @DisplayName("Test if get color by id works correctly")
    void getById() throws Exception {

        String id = persistColor();

        mockMvc.perform(get("/api/v1/color/id/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(colorCreateDto.name())));
    }

    @Test
    @DisplayName("Test if get color by id throws color not found exception")
    void getByIdThrowsColorNotFoundException() throws Exception {

        mockMvc.perform(get("/api/v1/color/id/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test if delete color works correctly")
    void delete() throws Exception {

        String id = persistColor();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/color/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(COLOR_DELETE_MESSAGE, id)));
    }
}
