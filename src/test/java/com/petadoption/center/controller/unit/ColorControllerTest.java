package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.ColorController;
import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.color.ColorDuplicateException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.model.Color;
import com.petadoption.center.service.ColorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.petadoption.center.util.Messages.COLOR_WITH_ID;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ColorControllerTest {

    @Mock
    private ColorService colorService;

    @InjectMocks
    private ColorController colorController;

    private Color testColor;
    private ColorGetDto colorGetDto;
    private ColorCreateDto colorCreateDto;

    @BeforeEach
    void setUp() {
        testColor = new Color();
        testColor.setId("1111-1111-2222");
        testColor.setName("Black");

        colorGetDto = new ColorGetDto("1111-1111-2222", "Black");

        colorCreateDto = new ColorCreateDto("Black");
    }

    @Test
    @DisplayName("Test if get All Colors works correctly")
    void getAllColorsShouldReturnColors() {

        int page = 0;
        int size = 5;
        String sortBy = "id";

        List<ColorGetDto> expectedColors = List.of(colorGetDto);

        when(colorService.getAllColors(page, size, sortBy)).thenReturn(expectedColors);

        ResponseEntity<List<ColorGetDto>> response = colorController.getAllColors(page, size, sortBy);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedColors, response.getBody());
        verify(colorService).getAllColors(page, size, sortBy);
    }


    @Test
    @DisplayName("Test if get Color by id works correctly")
    void getColorByIdShouldReturnColor() throws ColorNotFoundException {

        when(colorService.getColorById(testColor.getId())).thenReturn(colorGetDto);

        ResponseEntity<ColorGetDto> response = colorController.getColorById(testColor.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(colorGetDto, response.getBody());
        verify(colorService).getColorById(testColor.getId());

    }

    @Test
    @DisplayName("Test if add new Color works correctly")
    void createColorShouldReturnColor() throws ColorDuplicateException {

        when(colorService.addNewColor(colorCreateDto)).thenReturn(colorGetDto);

        ResponseEntity<ColorGetDto> response = colorController.addNewColor(colorCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(colorGetDto, response.getBody());
        verify(colorService).addNewColor(colorCreateDto);
    }


    @Test
    @DisplayName("Test if delete Color works correctly")
    void deleteColorShouldReturnColor() throws ColorNotFoundException {

        when(colorService.deleteColor(testColor.getId())).thenReturn(COLOR_WITH_ID + testColor.getId() + DELETE_SUCCESS);

        ResponseEntity<String> response = colorController.deleteColor(testColor.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(COLOR_WITH_ID + testColor.getId() + DELETE_SUCCESS, response.getBody());
        verify(colorService).deleteColor(testColor.getId());
    }
}
