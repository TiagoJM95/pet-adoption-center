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

import static com.petadoption.center.testUtils.TestDtoFactory.colorCreateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.createPrimaryColorDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createPrimaryColor;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ColorConverterTest {


    @Test
    @DisplayName("Test ColorCreateDto to Color model is working correctly")
    void fromColorCreateDtoToModel() {

        Color color = ColorConverter.toModel(colorCreateDto());

        assertEquals("Black", color.getName());
    }

    @Test
    @DisplayName("Test if ColorGetDto to Color model is working correctly")
    void fromColorGetDtoToModel() {


        Color color = ColorConverter.toModel(createPrimaryColorDto());

        assertEquals("444444-44444444-4444", color.getId());
        assertEquals("Black", color.getName());
    }

    @Test
    @DisplayName("Test Color model to ColorGetDto is working correctly")
    void fromModelToColorGetDto() {

        ColorGetDto colorGetDto = ColorConverter.toDto(createPrimaryColor());

        assertEquals("444444-44444444-4444", colorGetDto.id());
        assertEquals("Black", colorGetDto.name());
    }
}