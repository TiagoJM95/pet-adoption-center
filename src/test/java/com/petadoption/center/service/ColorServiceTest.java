package com.petadoption.center.service;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.not_found.ColorNotFoundException;
import com.petadoption.center.model.Color;
import com.petadoption.center.repository.ColorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.petadoption.center.testUtils.TestDtoFactory.colorCreateDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createPrimaryColor;
import static com.petadoption.center.util.Messages.COLOR_WITH_ID;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ColorServiceTest {

    @InjectMocks
    private ColorService colorService;

    @Mock
    private ColorRepository colorRepository;

    private Color testColor;
    private Color updatedColor;
    private ColorCreateDto colorCreateDto;

    @BeforeEach
    void setUp() {
        testColor = createPrimaryColor();
        updatedColor = createPrimaryColor();

        updatedColor.setId("1111-2222-2222");
        updatedColor.setName("White");

        colorCreateDto = colorCreateDto();
    }

    @Test
    @DisplayName("Test if get all colors return empty list if no colors")
    void getAllShouldReturnEmpty() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<Color> pagedColors = new PageImpl<>(List.of());

        when(colorRepository.findAll(pageRequest)).thenReturn(pagedColors);

        List<ColorGetDto> result = colorService.getAll(0, 10, "id");

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test if get all colors is working correctly")
    void getAllColorsShouldReturn() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<Color> pagedColors = new PageImpl<>(List.of(testColor));

        when(colorRepository.findAll(pageRequest)).thenReturn(pagedColors);

        List<ColorGetDto> result = colorService.getAll(0, 10, "id");

        assertEquals(1, result.size());
        assertEquals(testColor.getName(), result.getFirst().name());

    }

    @Test
    @DisplayName("Test if get all colors return number of elements of page size")
    void getAllShouldReturnNumberOfElementsOfPageSize() {

        Color colorToAdd = new Color();
        List<Color> allColors = List.of(testColor, updatedColor, colorToAdd);
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<Color> pagedColors = new PageImpl<>(List.of(testColor, updatedColor), pageRequest, allColors.size());

        when(colorRepository.findAll(pageRequest)).thenReturn(pagedColors);

        List<ColorGetDto> result = colorService.getAll(0, 10, "id");

        assertEquals(2, result.size());
        assertEquals(testColor.getName(), result.get(0).name());
        assertEquals(updatedColor.getName(), result.get(1).name());
    }

    @Test
    @DisplayName("Test if get all colors return with Descending Order")
    void getAllColorsShouldReturnInDescendingOrder() {

        Color colorToAdd = new Color();
        colorToAdd.setId("0000-0000-0000");
        colorToAdd.setName("Blue");

        List<Color> allColors = List.of(updatedColor, colorToAdd, testColor);
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "name");
        Page<Color> pagedColors = new PageImpl<>(allColors, pageRequest, allColors.size());

        when(colorRepository.findAll(any(PageRequest.class))).thenReturn(pagedColors);

        List<ColorGetDto> result = colorService.getAll(0, 3, "name");

        assertNotNull(result);
        assertEquals(3,result.size());
        assertEquals(result.get(0).name(), updatedColor.getName());
        assertEquals(result.get(1).name(), colorToAdd.getName());
        assertEquals(result.get(2).name(), testColor.getName());
    }

    @Test
    @DisplayName("Test if get all colors return with Ascending Order")
    void getAllColorsShouldReturnInAscendingOrder() {

        Color colorToAdd = new Color();
        colorToAdd.setId("0000-0000-0000");
        colorToAdd.setName("Blue");

        List<Color> allColors = List.of(testColor, colorToAdd, updatedColor);
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<Color> pagedColors = new PageImpl<>(allColors, pageRequest, allColors.size());

        when(colorRepository.findAll(pageRequest)).thenReturn(pagedColors);

        List<ColorGetDto> result = colorService.getAll(0, 10, "id");

        assertNotNull(result);
        assertEquals(3,result.size());
        assertEquals(result.get(0).name(), testColor.getName());
        assertEquals(result.get(1).name(), colorToAdd.getName());
        assertEquals(result.get(2).name(), updatedColor.getName());
    }


    @Test
    @DisplayName("Test if get color by id is working correctly")
    void getColorByIdShouldReturn() throws ColorNotFoundException {

        when(colorRepository.findById(testColor.getId())).thenReturn(Optional.of(testColor));

        ColorGetDto result = colorService.getById(testColor.getId());

        assertEquals(testColor.getName(), result.name());
    }

    @Test
    @DisplayName("Test if get color by id return ColorNotFoundException")
    void getColorByIdShouldReturnNotFoundException() {

        when(colorRepository.findById(testColor.getId())).thenReturn(Optional.empty());

        assertThrows(ColorNotFoundException.class, () -> colorService.getById(testColor.getId()));
    }

    @Test
    @DisplayName("Test if add new color saves and returns ColorGetDto")
    void createGetDto() {

        when(colorRepository.save(any(Color.class))).thenReturn(testColor);

        ColorGetDto result = colorService.create(colorCreateDto);

        assertNotNull(result);
        assertEquals(testColor.getName(), result.name());
    }


    @Test
    @DisplayName("Test if delete color works correctly")
    void deleteShouldWorkCorrectly() throws ColorNotFoundException {

        when(colorRepository.findById(testColor.getId())).thenReturn(Optional.of(testColor));

        assertEquals(colorService.delete(testColor.getId()), COLOR_WITH_ID + testColor.getId() + DELETE_SUCCESS);
    }


    @Test
    @DisplayName("Test if delete color throws ColorNotFoundException")
    void deleteColorShouldThrowNotFoundException() {

        when(colorRepository.findById(testColor.getId())).thenReturn(Optional.empty());

        assertThrows(ColorNotFoundException.class, () -> colorService.delete(testColor.getId()));
    }
}





