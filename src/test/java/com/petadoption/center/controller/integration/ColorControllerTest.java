package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
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

import static com.petadoption.center.testUtils.TestDtoFactory.createColorCreateDto;
import static com.petadoption.center.util.Messages.COLOR_WITH_ID;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ColorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ColorGetDto colorGetDto;
    private ColorCreateDto colorCreateDto;

    @BeforeEach
    void setUp() {
        colorCreateDto = createColorCreateDto();
    }

    @Test
    @DisplayName("Test if create color works correctly")
    @DirtiesContext
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
    @DirtiesContext
    void getAllColors() throws Exception {

        createColor();

        mockMvc.perform(get("/api/v1/color/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(colorGetDto.id())))
                .andExpect(jsonPath("$[0].name", is(colorGetDto.name())));
    }

    @Test
    @DisplayName("Test if get color by id works correctly")
    @DirtiesContext
    void getColorById() throws Exception {

        createColor();

        mockMvc.perform(get("/api/v1/color/id/{id}", colorGetDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(colorGetDto.id())))
                .andExpect(jsonPath("$.name", is(colorGetDto.name())));
    }

    @Test
    @DisplayName("Test if delete color works correctly")
    @DirtiesContext
    void deleteColor() throws Exception {

        createColor();

        mockMvc.perform(delete("/api/v1/color/delete/{id}", colorGetDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(COLOR_WITH_ID + colorGetDto.id() + DELETE_SUCCESS));
    }
}
