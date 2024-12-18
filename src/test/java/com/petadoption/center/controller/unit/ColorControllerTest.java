package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.ColorController;
import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.not_found.ColorNotFoundException;
import com.petadoption.center.model.Color;
import com.petadoption.center.service.interfaces.ColorServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.petadoption.center.testUtils.TestDtoFactory.primaryColorCreateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.primaryColorGetDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createPrimaryColor;
import static com.petadoption.center.util.Messages.COLOR_DELETE_MESSAGE;
import static java.lang.String.format;
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
        colorCreateDto = primaryColorCreateDto();
    }

    @Test
    @DisplayName("Test if get All Colors works correctly")
    void getAllColorsShouldReturn() {

        int page = 0;
        int size = 10;
        String sort = "created_at";
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        List<ColorGetDto> expectedColors = List.of(colorGetDto);

        when(colorServiceI.getAll(pageable)).thenReturn(expectedColors);

        ResponseEntity<List<ColorGetDto>> response = colorController.getAll(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedColors, response.getBody());
        verify(colorServiceI).getAll(pageable);
    }


    @Test
    @DisplayName("Test if get Color by id works correctly")
    void getColorByIdShouldReturn() throws ColorNotFoundException {

        when(colorServiceI.getById(testColor.getId())).thenReturn(colorGetDto);

        ResponseEntity<ColorGetDto> response = colorController.getById(testColor.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(colorGetDto, response.getBody());
        verify(colorServiceI).getById(testColor.getId());

    }

    @Test
    @DisplayName("Test if add new Color works correctly")
    void createColorShouldReturnColor() {

        when(colorServiceI.create(colorCreateDto)).thenReturn(colorGetDto);

        ResponseEntity<ColorGetDto> response = colorController.create(colorCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(colorGetDto, response.getBody());
        verify(colorServiceI).create(colorCreateDto);
    }


    @Test
    @DisplayName("Test if delete Color works correctly")
    void deleteColorShouldReturn() throws ColorNotFoundException {

        when(colorServiceI.delete(testColor.getId())).thenReturn(format(COLOR_DELETE_MESSAGE, testColor.getId()));

        ResponseEntity<String> response = colorController.delete(testColor.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(format(COLOR_DELETE_MESSAGE, testColor.getId()), response.getBody());
        verify(colorServiceI).delete(testColor.getId());
    }
}
