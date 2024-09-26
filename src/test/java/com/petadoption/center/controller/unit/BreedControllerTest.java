package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.BreedController;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.exception.breed.BreedDuplicateException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
import com.petadoption.center.service.interfaces.BreedServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.petadoption.center.util.Messages.BREED_WITH_ID;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class BreedControllerTest {

    @Mock
    private BreedServiceI breedServiceI;

    @InjectMocks
    private BreedController breedController;

    private Breed testBreed;
    private Breed updatedBreed;
    private BreedGetDto breedGetDto;
    private BreedCreateDto breedCreateDto;
    private BreedUpdateDto breedUpdateDto;
    private Species species;
    private SpeciesGetDto speciesGetDto;


    @BeforeEach
    void setUp() {

        species = new Species("1111-1111-2222", "Dog");
        speciesGetDto = new SpeciesGetDto("1111-1111-2222", "Dog");

        testBreed = new Breed();
        testBreed.setId("2222-2222-3333");
        testBreed.setName("Golden Retriever");
        testBreed.setSpecies(species);

        updatedBreed = new Breed();
        updatedBreed.setId("1234-1234-5678");
        updatedBreed.setName("Weimaraner");
        updatedBreed.setSpecies(species);

        breedCreateDto = new BreedCreateDto(
                "Golden Retriever",
                species.getId()
        );

        breedUpdateDto = new BreedUpdateDto(
                "Weimaraner"
        );

        breedGetDto = new BreedGetDto(
                "2222-2222-3333",
                "Golden Retriever",
                speciesGetDto
        );
    }

    @Test
    @DisplayName("Test if get all Breeds works correctly")
    void getAllBreedsShouldReturnBreeds() {

        int page = 0;
        int size = 5;
        String sortBy = "id";

        List<BreedGetDto> expectedBreeds = List.of(breedGetDto);

        when(breedServiceI.getAllBreeds(page, size, sortBy)).thenReturn(expectedBreeds);

        ResponseEntity<List<BreedGetDto>> response = breedController.getAllBreeds(page, size, sortBy);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBreeds, response.getBody());
        verify(breedServiceI).getAllBreeds(page, size, sortBy);
    }

    @Test
    @DisplayName("Test if get Breed by id works correctly")
    void getBreedByIdShouldReturnBreed() throws BreedNotFoundException {

        when(breedServiceI.getBreedById(testBreed.getId())).thenReturn(breedGetDto);

        ResponseEntity<BreedGetDto> response = breedController.getBreedById(testBreed.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(breedGetDto, response.getBody());
        verify(breedServiceI).getBreedById(testBreed.getId());

    }

    @Test
    @DisplayName("Test if add new Breed works correctly")
    void addNewBreedShouldReturnBreed() throws BreedDuplicateException, SpeciesNotFoundException {

        when(breedServiceI.addNewBreed(breedCreateDto)).thenReturn(breedGetDto);

        ResponseEntity<BreedGetDto> response = breedController.addNewBreed(breedCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(breedGetDto, response.getBody());
        verify(breedServiceI).addNewBreed(breedCreateDto);
    }

    @Test
    @DisplayName("Test if update Breed works correctly")
    void updateBreedShouldReturnBreed() throws BreedNotFoundException, BreedDuplicateException {

        when(breedServiceI.updateBreed(testBreed.getId(), breedUpdateDto)).thenReturn(breedGetDto);

        ResponseEntity<BreedGetDto> response = breedController.updateBreed(testBreed.getId(), breedUpdateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(breedGetDto, response.getBody());
        verify(breedServiceI).updateBreed(testBreed.getId(), breedUpdateDto);
    }


    @Test
    @DisplayName("Test if delete Breed works correctly")
    void deleteBreedShouldReturnBreed() throws BreedNotFoundException {

        when(breedServiceI.deleteBreed(testBreed.getId())).thenReturn(BREED_WITH_ID + testBreed.getId() + DELETE_SUCCESS);

        ResponseEntity<String> response = breedController.deleteBreed(testBreed.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BREED_WITH_ID + testBreed.getId() + DELETE_SUCCESS, response.getBody());
        verify(breedServiceI).deleteBreed(testBreed.getId());
    }
}
