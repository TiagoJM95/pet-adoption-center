package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.SpeciesController;
import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.exception.not_found.SpeciesNotFoundException;
import com.petadoption.center.model.Species;
import com.petadoption.center.service.interfaces.SpeciesServiceI;
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

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.createSpecies;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.SPECIES_WITH_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class SpeciesControllerTest {

    @Mock
    private SpeciesServiceI speciesServiceI;

    @InjectMocks
    private SpeciesController speciesController;

    private Species testSpecies;
    private SpeciesGetDto speciesGetDto;
    private SpeciesCreateDto speciesCreateDto;
    private SpeciesUpdateDto speciesUpdateDto;

    @BeforeEach
    void setUp() {
        testSpecies = createSpecies();
        speciesGetDto = speciesGetDto();
        speciesCreateDto = speciesCreateDto();
        speciesUpdateDto = speciesUpdateDto();
    }

    @Test
    @DisplayName("Test if get All Species works correctly")
    void getAllSpeciesShouldReturnSpecies() {

        int page = 0;
        int size = 5;
        String sortBy = "id";

        List<SpeciesGetDto> expectedSpecies = List.of(speciesGetDto);

        when(speciesServiceI.getAllSpecies(page, size, sortBy)).thenReturn(expectedSpecies);

        ResponseEntity<List<SpeciesGetDto>> result = speciesController.getAllPetSpecies(page, size, sortBy);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(speciesGetDto), result.getBody());
        verify(speciesServiceI).getAllSpecies(page, size, sortBy);
    }

    @Test
    @DisplayName("Test if get Species by id works correctly")
    void getSpeciesByIdShouldReturnSpecies() throws SpeciesNotFoundException {

        when(speciesServiceI.getSpeciesById(testSpecies.getId())).thenReturn(speciesGetDto);

        ResponseEntity<SpeciesGetDto> result = speciesController.getPetSpeciesById(testSpecies.getId());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(speciesGetDto, result.getBody());
        verify(speciesServiceI).getSpeciesById(testSpecies.getId());
    }

    @Test
    @DisplayName("Test if add new species saves and returns SpeciesGetDto")
    void addPetSpeciesShouldSaveAndReturnSpecies() {

        when(speciesServiceI.addNewSpecies(speciesCreateDto)).thenReturn(speciesGetDto);

        ResponseEntity<SpeciesGetDto> result = speciesController.addNewPetSpecies(speciesCreateDto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(speciesGetDto, result.getBody());
        verify(speciesServiceI).addNewSpecies(speciesCreateDto);
    }

    @Test
    @DisplayName("Test if update species saves and returns SpeciesGetDto")
    void updatePetSpeciesShouldSaveAndReturnSpecies() throws SpeciesNotFoundException {

        when(speciesServiceI.updateSpecies(testSpecies.getId(), speciesUpdateDto)).thenReturn(speciesGetDto);

        ResponseEntity<SpeciesGetDto> result = speciesController.updatePetSpecies(testSpecies.getId(), speciesUpdateDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(speciesGetDto, result.getBody());
        verify(speciesServiceI).updateSpecies(testSpecies.getId(), speciesUpdateDto);
    }

    @Test
    @DisplayName("Test if delete species works correctly")
    void deleteSpeciesShouldReturnMessage() throws SpeciesNotFoundException {

        when(speciesServiceI.deleteSpecies(testSpecies.getId())).thenReturn(SPECIES_WITH_ID + testSpecies.getId() + DELETE_SUCCESS);

        ResponseEntity<String> result = speciesController.deleteSpecies(testSpecies.getId());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(SPECIES_WITH_ID + testSpecies.getId() + DELETE_SUCCESS, result.getBody());
        verify(speciesServiceI).deleteSpecies(testSpecies.getId());
    }
}
