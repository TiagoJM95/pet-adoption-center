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

    private final String GET_OR_CREATE = URL + "color/";
    private final String GET_BY_ID = URL + "color/id/{id}";
    private final String DELETE = URL + "color/delete/{id}";

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

        var result =  mockMvc.perform(post(GET_OR_CREATE)
                        .content(objectMapper.writeValueAsString(colorCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        ColorGetDto colorCreatedDto = objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto.class);
        colorId = colorCreatedDto.id();
        return objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto.class);
    }

    private ColorGetDto persistAnotherColor() throws Exception {

        ColorCreateDto dto = ColorCreateDto.builder()
                .name("Blue")
                .build();

        var result =  mockMvc.perform(post(GET_OR_CREATE)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto.class);
    }

    @Test
    @DisplayName("Test if create color returns the created color with ColorGetDto")
    void createColorReturnsColorGetDto() throws Exception {

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
    @DisplayName("Test if create color with duplicate fields of an existing color throws DataIntegrityViolationException")
    void createColorThrowsDataIntegrityViolationExceptionIfDuplicateField() throws Exception {

        persistColor();

        var result = mockMvc.perform(post(GET_OR_CREATE)
                        .content(objectMapper.writeValueAsString(colorCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error.constraint()).isEqualTo("uniquecolorname");
    }

    @Test
    @DisplayName("Test if create color with no body throw HttpMessageNotReadableException")
    void shouldThrowHttpMessageNotReadableException_WhenCreateIsCalledWithNoBody() throws Exception {

        var result = mockMvc.perform(post(GET_OR_CREATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertTrue(error.message().contains("Required request body is missing"));
    }

    @Test
    @DisplayName("Test if get all colors returns a list ColorGetDto")
    void getAllReturnsListOfColorGetDto() throws Exception {

        ColorGetDto colorCreatedGetDto = persistColor();

        var result = mockMvc.perform(get(GET_OR_CREATE)
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
    @DisplayName("Test if get all colors pagination return the number of elements requested")
    void getAllReturnsNumberOfElementsOfRequest() throws Exception {

        ColorGetDto firstColor = persistColor();
        persistAnotherColor();

        var result = mockMvc.perform(get(GET_OR_CREATE)
                        .param("page", "0")
                        .param("size", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ColorGetDto[] colorGetDtoArray = objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto[].class);
        assertThat(colorGetDtoArray).hasSize(1);
        assertThat(colorGetDtoArray[0])
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(firstColor);
    }

    @Test
    @DisplayName("Test if get all return empty list if no colors in database")
    void getAllReturnEmptyList() throws Exception {

        var result = mockMvc.perform(get(GET_OR_CREATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ColorGetDto[] colorGetDtoArray = objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto[].class);
        assertThat(colorGetDtoArray).isEmpty();
    }

    @Test
    @DisplayName("Test if get color by id return the ColorGetDto of the requested color")
    void getByIdReturnsColorGetDtoOfRequestedColor() throws Exception {

        ColorGetDto colorCreatedGetDto = persistColor();

        var result = mockMvc.perform(get(GET_BY_ID, colorId)
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

        mockMvc.perform(get(GET_BY_ID, "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test if delete color removes Color from database and returns message of success")
    void deleteRemovesColorAndReturnsMessage() throws Exception {

        persistColor();

        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE, colorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(COLOR_DELETE_MESSAGE, colorId)));
    }

    @Test
    @DisplayName("Test if delete color throws color not found exception if color does not exist")
    void deleteThrowsColorNotFoundExceptionIfColorDoesNotExist() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE, "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
