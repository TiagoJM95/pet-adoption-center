package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.petadoption.center.aspect.Error;



import static com.petadoption.center.testUtils.TestDtoFactory.primaryColorCreateDto;
import static com.petadoption.center.util.Messages.COLOR_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ColorControllerTest extends TestContainerConfig {

    private ColorCreateDto colorCreateDto;
    private String colorId;


    @BeforeEach
    void setUp() {
        colorCreateDto = primaryColorCreateDto();
    }

    @AfterEach
    void tearDown() {
        colorRepository.deleteAll();
    }

    private ColorGetDto persistColor() throws Exception {

        var result =  mockMvc.perform(post("/api/v1/color/")
                        .content(objectMapper.writeValueAsString(colorCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        ColorGetDto colorCreatedDto = objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto.class);
        colorId = colorCreatedDto.id();
        return objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto.class);
    }

    @Test
    @DisplayName("Test if create color works correctly")
    void createColor() throws Exception {

        ColorGetDto expected = ColorGetDto.builder()
                .name(colorCreateDto.name())
                .build();

        ColorGetDto colorCreatedGetDto = persistColor();

        assertThat(colorCreatedGetDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Test if create color throws DataIntegrityViolationException")
    void createColorThrowsDataIntegrityViolationException() throws Exception {

        persistColor();

        var result = mockMvc.perform(post("/api/v1/color/")
                        .content(objectMapper.writeValueAsString(colorCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error.constraint()).isEqualTo("uniquecolorname");
    }

    @Test
    @DisplayName("Test if throw HttpMessageNotReadableException if request body is empty")
    void shouldThrowHttpMessageNotReadableException_WhenCreateIsCalledWithNoBody() throws Exception {

        var result = mockMvc.perform(post("/api/v1/color/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertTrue(error.message().contains("Required request body is missing"));
    }

    @Test
    @DisplayName("Test if get all colors works correctly")
    void getAll() throws Exception {

        ColorGetDto colorCreatedGetDto = persistColor();

        var result = mockMvc.perform(get("/api/v1/color/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ColorGetDto[] colorGetDtoArray = objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto[].class);
        assertThat(colorGetDtoArray).hasSize(1);
        assertThat(colorGetDtoArray[0])
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(colorCreatedGetDto);
    }

    @Test
    @DisplayName("Test if get color by id works correctly")
    void getById() throws Exception {

        ColorGetDto colorCreatedGetDto = persistColor();

        var result = mockMvc.perform(get("/api/v1/color/id/{id}", colorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ColorGetDto colorGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto.class);
        assertThat(colorGetDto)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(colorCreatedGetDto);

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

        persistColor();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/color/delete/{id}", colorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(COLOR_DELETE_MESSAGE, colorId)));
    }
}
