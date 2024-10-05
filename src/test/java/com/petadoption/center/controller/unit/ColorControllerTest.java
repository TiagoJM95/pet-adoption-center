package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.ColorController;
import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.model.Color;
import com.petadoption.center.service.interfaces.ColorServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.petadoption.center.testUtils.TestDtoFactory.colorCreateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.primaryColorGetDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createPrimaryColor;
import static com.petadoption.center.util.Messages.COLOR_WITH_ID;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ColorControllerTest {

    @Mock
    private ColorServiceI colorServiceI;

    @InjectMocks
    private ColorController colorController;

    private Color testColor;
    private ColorGetDto colorGetDto;
    private ColorCreateDto colorCreateDto;

    @BeforeEach
    void setUp() {

        testColor = createPrimaryColor();
        colorGetDto =  primaryColorGetDto();
        colorCreateDto = colorCreateDto();
    }

    @Test
    @DisplayName("Test if get All Colors works correctly")
    void getAllColorsShouldReturn() {

        int page = 0;
        int size = 5;
        String sortBy = "id";

        List<ColorGetDto> expectedColors = List.of(colorGetDto);

        when(colorServiceI.getAllColors(page, size, sortBy)).thenReturn(expectedColors);

        ResponseEntity<List<ColorGetDto>> response = colorController.getAll(page, size, sortBy);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedColors, response.getBody());
        verify(colorServiceI).getAllColors(page, size, sortBy);
    }


    @Test
    @DisplayName("Test if get Color by id works correctly")
    void getColorByIdShouldReturn() throws ColorNotFoundException {

        when(colorServiceI.getColorById(testColor.getId())).thenReturn(colorGetDto);

        ResponseEntity<ColorGetDto> response = colorController.getById(testColor.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(colorGetDto, response.getBody());
        verify(colorServiceI).getColorById(testColor.getId());

    }

    @Test
    @DisplayName("Test if add new Color works correctly")
    void createColorShouldReturnColor() {

        when(colorServiceI.addNewColor(colorCreateDto)).thenReturn(colorGetDto);

        ResponseEntity<ColorGetDto> response = colorController.create(colorCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(colorGetDto, response.getBody());
        verify(colorServiceI).addNewColor(colorCreateDto);
    }


    @Test
    @DisplayName("Test if delete Color works correctly")
    void deleteColorShouldReturn() throws ColorNotFoundException {

        when(colorServiceI.deleteColor(testColor.getId())).thenReturn(COLOR_WITH_ID + testColor.getId() + DELETE_SUCCESS);

        ResponseEntity<String> response = colorController.delete(testColor.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(COLOR_WITH_ID + testColor.getId() + DELETE_SUCCESS, response.getBody());
        verify(colorServiceI).deleteColor(testColor.getId());
    }
}
