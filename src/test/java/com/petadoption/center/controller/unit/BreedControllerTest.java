package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.BreedController;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.exception.not_found.BreedNotFoundException;
import com.petadoption.center.exception.not_found.SpeciesNotFoundException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.createBreed;
import static com.petadoption.center.testUtils.TestEntityFactory.createSpecies;
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
    private BreedGetDto breedGetDto;
    private BreedCreateDto breedCreateDto;
    private BreedUpdateDto breedUpdateDto;

    @BeforeEach
    void setUp() {

        Species species = createSpecies();
        SpeciesGetDto speciesGetDto = speciesGetDto();
        testBreed = createBreed(species);

        Breed updatedBreed = createBreed(species);
        updatedBreed.setId("1234-1234-5678");
        updatedBreed.setName("Weimaraner");

        breedCreateDto = primaryBreedCreateDto(species.getId());
        breedUpdateDto = breedUpdateDto();
        breedGetDto = primaryBreedGetDto(speciesGetDto);
    }

    @Test
    @DisplayName("Test if get all Breeds works correctly")
    void getAllBreedsShouldReturn() {

        int page = 0;
        int size = 10;
        String sort = "created_at";
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        List<BreedGetDto> expectedBreeds = List.of(breedGetDto);

        when(breedServiceI.getAll(pageable)).thenReturn(expectedBreeds);

        ResponseEntity<List<BreedGetDto>> response = breedController.getAll(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBreeds, response.getBody());
        verify(breedServiceI).getAll(pageable);
    }

    @Test
    @DisplayName("Test if get Breed by id works correctly")
    void getBreedByIdShouldReturn() throws BreedNotFoundException {

        when(breedServiceI.getById(testBreed.getId())).thenReturn(breedGetDto);

        ResponseEntity<BreedGetDto> response = breedController.getById(testBreed.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(breedGetDto, response.getBody());
        verify(breedServiceI).getById(testBreed.getId());

    }

    @Test
    @DisplayName("Test if add new Breed works correctly")
    void create() throws SpeciesNotFoundException {

        when(breedServiceI.create(breedCreateDto)).thenReturn(breedGetDto);

        ResponseEntity<BreedGetDto> response = breedController.create(breedCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(breedGetDto, response.getBody());
        verify(breedServiceI).create(breedCreateDto);
    }

    @Test
    @DisplayName("Test if update Breed works correctly")
    void updateBreedShouldReturn() throws BreedNotFoundException {

        when(breedServiceI.update(testBreed.getId(), breedUpdateDto)).thenReturn(breedGetDto);

        ResponseEntity<BreedGetDto> response = breedController.update(testBreed.getId(), breedUpdateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(breedGetDto, response.getBody());
        verify(breedServiceI).update(testBreed.getId(), breedUpdateDto);
    }


    @Test
    @DisplayName("Test if delete Breed works correctly")
    void deleteBreedShouldReturn() throws BreedNotFoundException {

        when(breedServiceI.delete(testBreed.getId())).thenReturn(BREED_WITH_ID + testBreed.getId() + DELETE_SUCCESS);

        ResponseEntity<String> response = breedController.delete(testBreed.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BREED_WITH_ID + testBreed.getId() + DELETE_SUCCESS, response.getBody());
        verify(breedServiceI).delete(testBreed.getId());
    }
}
