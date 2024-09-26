package com.petadoption.center.converter;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.model.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ColorConverterTest {


    @Test
    @DisplayName("Test ColorCreateDto to Color model is working correctly")
    void fromColorCreateDtoToModel() {

        ColorCreateDto colorCreateDto = new ColorCreateDto("Black");

        Color color = ColorConverter.toModel(colorCreateDto);

        assertEquals("Black", color.getName());
    }

    @Test
    @DisplayName("Test if ColorGetDto to Color model is working correctly")
    void fromColorGetDtoToModel() {

        ColorGetDto colorGetDto = new ColorGetDto(
                "3213-3213-3213",
                "Black");

        Color color = ColorConverter.toModel(colorGetDto);

        assertEquals("3213-3213-3213", color.getId());
        assertEquals("Black", color.getName());
    }

    @Test
    @DisplayName("Test Color model to ColorGetDto is working correctly")
    void fromModelToColorGetDto() {

        Color color = new Color(
                "3213-3213-3213",
                "Black");

        ColorGetDto colorGetDto = ColorConverter.toDto(color);

        assertEquals("3213-3213-3213", colorGetDto.id());
        assertEquals("Black", colorGetDto.name());
    }
}