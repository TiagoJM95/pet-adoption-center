package com.petadoption.center.converter;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.model.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ColorConverterTest {


    @Test
    @DisplayName("Test ColorCreateDto to Color model is working correctly")
    void fromColorCreateDtoToModel() {

        ColorCreateDto colorCreateDto = new ColorCreateDto("Black");

        Color color = ColorConverter.fromColorCreateDtoToModel(colorCreateDto);

        assertEquals("Black", color.getName());
    }

    @Test
    @DisplayName("Test Color model to ColorGetDto is working correctly")
    void fromModelToColorGetDto() {

        ColorGetDto colorGetDto = ColorConverter.fromModelToColorGetDto(new Color(1L, "Black"));

        assertEquals(1L, colorGetDto.id());
        assertEquals("Black", colorGetDto.name());
    }
}