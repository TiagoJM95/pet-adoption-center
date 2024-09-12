package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.SpeciesController;
import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.exception.species.SpeciesNameDuplicateException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Species;
import com.petadoption.center.service.SpeciesService;
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
import java.util.Objects;

import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.SPECIES_WITH_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class SpeciesControllerTest {

    @Mock
    private SpeciesService speciesService;

    @InjectMocks
    private SpeciesController speciesController;

    private Species testSpecies;
    private SpeciesGetDto speciesGetDto;
    private SpeciesCreateDto speciesCreateDto;
    private SpeciesUpdateDto speciesUpdateDto;

    @BeforeEach
    void setUp() {
        testSpecies = new Species();
        testSpecies.setId(1L);
        testSpecies.setName("Dog");

        speciesGetDto = new SpeciesGetDto(1L, "Dog");

        speciesCreateDto = new SpeciesCreateDto("Dog");

        speciesUpdateDto = new SpeciesUpdateDto("Cat");
    }

    @Test
    @DisplayName("Test if get All Species works correctly")
    void getAllSpeciesShouldReturnSpecies() {

        int page = 0;
        int size = 5;
        String sortBy = "id";

        List<SpeciesGetDto> expectedSpecies = List.of(speciesGetDto);

        when(speciesService.getAllPetSpecies(page, size, sortBy)).thenReturn(expectedSpecies);

        ResponseEntity<List<SpeciesGetDto>> result = speciesController.getAllPetSpecies(page, size, sortBy);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(speciesGetDto), result.getBody());
        verify(speciesService).getAllPetSpecies(page, size, sortBy);
    }

    @Test
    @DisplayName("Test if get Species by id works correctly")
    void getSpeciesByIdShouldReturnSpecies() throws SpeciesNotFoundException {

        when(speciesService.getPetSpeciesById(1L)).thenReturn(speciesGetDto);

        ResponseEntity<SpeciesGetDto> result = speciesController.getPetSpeciesById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(speciesGetDto, result.getBody());
        verify(speciesService).getPetSpeciesById(1L);
    }

    @Test
    @DisplayName("Test if add new species saves and returns SpeciesGetDto")
    void addPetSpeciesShouldSaveAndReturnSpecies() throws SpeciesNameDuplicateException {

        when(speciesService.addNewPetSpecies(speciesCreateDto)).thenReturn(speciesGetDto);

        ResponseEntity<SpeciesGetDto> result = speciesController.addNewPetSpecies(speciesCreateDto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(speciesGetDto, result.getBody());
        verify(speciesService).addNewPetSpecies(speciesCreateDto);
    }

    @Test
    @DisplayName("Test if update species saves and returns SpeciesGetDto")
    void updatePetSpeciesShouldSaveAndReturnSpecies() throws SpeciesNotFoundException, SpeciesNameDuplicateException {

        when(speciesService.updatePetSpecies(1L, speciesUpdateDto)).thenReturn(speciesGetDto);

        ResponseEntity<SpeciesGetDto> result = speciesController.updatePetSpecies(1L, speciesUpdateDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(speciesGetDto, result.getBody());
        verify(speciesService).updatePetSpecies(1L, speciesUpdateDto);
    }

    @Test
    @DisplayName("Test if delete species works correctly")
    void deleteSpeciesShouldReturnMessage() throws SpeciesNotFoundException {

        when(speciesService.deleteSpecies(1L)).thenReturn(SPECIES_WITH_ID + 1L + DELETE_SUCCESS);

        ResponseEntity<String> result = speciesController.deleteSpecies(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(SPECIES_WITH_ID + 1L + DELETE_SUCCESS, result.getBody());
        verify(speciesService).deleteSpecies(1L);
    }
}
