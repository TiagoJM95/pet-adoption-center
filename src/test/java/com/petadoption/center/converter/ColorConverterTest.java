package com.petadoption.center.converter;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.model.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static com.petadoption.center.testUtils.TestDtoFactory.primaryColorCreateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.primaryColorGetDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createPrimaryColor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ColorConverterTest {


    @Test
    @DisplayName("Test ColorCreateDto to Color model is working correctly")
    void fromColorCreateDtoToModel() {

        Color color = ColorConverter.toModel(primaryColorCreateDto());

        assertEquals("Black", color.getName());
    }

    @Test
    @DisplayName("Test if ColorGetDto to Color model is working correctly")
    void fromColorGetDtoToModel() {


        Color color = ColorConverter.toModel(primaryColorGetDto());

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

    @Test
    @DisplayName("Test if ColorCreateDto to model returns null if received null dto")
    void testIfFromColorCreateDtoReturnNullIfReceivedNullDto() {
        assertNull(ColorConverter.toModel((ColorCreateDto) null));
    }

    @Test
    @DisplayName("Test if ColorGetDto to model returns null if received null dto")
    void testIfFromColorGetDtoReturnNullIfReceivedNullDto() {
        assertNull(ColorConverter.toModel((ColorGetDto) null));
    }

    @Test
    @DisplayName("Test if model to ColorGetDto returns null if received null model")
    void testIfFromModelReturnNullIfReceivedNullModel() {
        assertNull(ColorConverter.toDto(null));
    }
}