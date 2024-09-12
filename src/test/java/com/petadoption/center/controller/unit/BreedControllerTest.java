package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.BreedController;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.exception.breed.BreedDuplicateException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
import com.petadoption.center.service.BreedService;
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

import static com.petadoption.center.util.Messages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class BreedControllerTest {

    @Mock
    private BreedService breedService;

    @InjectMocks
    private BreedController breedController;

    private Breed testBreed;
    private Breed updatedBreed;
    private BreedGetDto breedGetDto;
    private BreedCreateDto breedCreateDto;
    private BreedUpdateDto breedUpdateDto;
    private Species species;


    @BeforeEach
    void setUp() {

        species = new Species(1L, "Dog");

        testBreed = new Breed();
        testBreed.setId(1L);
        testBreed.setName("Golden Retriever");
        testBreed.setSpecies(species);

        updatedBreed = new Breed();
        updatedBreed.setId(2L);
        updatedBreed.setName("Weimaraner");
        updatedBreed.setSpecies(species);

        breedCreateDto = new BreedCreateDto(
                "Golden Retriever",
                1L
        );

        breedUpdateDto = new BreedUpdateDto(
                "Weimaraner"
        );

        breedGetDto = new BreedGetDto(
                1L,
                "Golden Retriever",
                "Dog"
        );
    }

    @Test
    @DisplayName("Test if get all Breeds works correctly")
    void getAllBreedsShouldReturnBreeds() {

        int page = 0;
        int size = 5;
        String sortBy = "id";

        List<BreedGetDto> expectedBreeds = List.of(breedGetDto);

        when(breedService.getAllBreeds(page, size, sortBy)).thenReturn(expectedBreeds);

        ResponseEntity<List<BreedGetDto>> response = breedController.getAllBreeds(page, size, sortBy);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBreeds, response.getBody());
        verify(breedService).getAllBreeds(page, size, sortBy);
    }

    @Test
    @DisplayName("Test if get Breed by id works correctly")
    void getBreedByIdShouldReturnBreed() throws BreedNotFoundException {

        when(breedService.getBreedById(testBreed.getId())).thenReturn(breedGetDto);

        ResponseEntity<BreedGetDto> response = breedController.getBreedById(testBreed.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(breedGetDto, response.getBody());
        verify(breedService).getBreedById(testBreed.getId());

    }

    @Test
    @DisplayName("Test if add new Breed works correctly")
    void addNewBreedShouldReturnBreed() throws BreedDuplicateException, SpeciesNotFoundException {

        when(breedService.addNewBreed(breedCreateDto)).thenReturn(breedGetDto);

        ResponseEntity<BreedGetDto> response = breedController.addNewBreed(breedCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(breedGetDto, response.getBody());
        verify(breedService).addNewBreed(breedCreateDto);
    }

    @Test
    @DisplayName("Test if update Breed works correctly")
    void updateBreedShouldReturnBreed() throws BreedNotFoundException, BreedDuplicateException {

        when(breedService.updateBreed(testBreed.getId(), breedUpdateDto)).thenReturn(breedGetDto);

        ResponseEntity<BreedGetDto> response = breedController.updateBreed(testBreed.getId(), breedUpdateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(breedGetDto, response.getBody());
        verify(breedService).updateBreed(testBreed.getId(), breedUpdateDto);
    }


    @Test
    @DisplayName("Test if delete Breed works correctly")
    void deleteBreedShouldReturnBreed() throws BreedNotFoundException {

        when(breedService.deleteBreed(testBreed.getId())).thenReturn(BREED_WITH_ID + 1L + DELETE_SUCCESS);

        ResponseEntity<String> response = breedController.deleteBreed(testBreed.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BREED_WITH_ID + 1L + DELETE_SUCCESS, response.getBody());
        verify(breedService).deleteBreed(testBreed.getId());
    }
}
