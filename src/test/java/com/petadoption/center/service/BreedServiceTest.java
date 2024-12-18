package com.petadoption.center.service;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.exception.not_found.BreedNotFoundException;
import com.petadoption.center.exception.not_found.SpeciesNotFoundException;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
import com.petadoption.center.repository.BreedRepository;
import com.petadoption.center.repository.SpeciesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.petadoption.center.converter.SpeciesConverter.toDto;
import static com.petadoption.center.testUtils.TestDtoFactory.breedUpdateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.primaryBreedCreateDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createBreed;
import static com.petadoption.center.testUtils.TestEntityFactory.createSpecies;
import static com.petadoption.center.util.Messages.BREED_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class BreedServiceTest {

    @InjectMocks
    private BreedService breedService;

    @Mock
    private BreedRepository breedRepository;

    @Mock
    private SpeciesRepository speciesRepository;

    @Mock
    private SpeciesService speciesService;

    private Species species;

    private Breed testBreed;
    private Breed updatedBreed;
    private BreedCreateDto breedCreateDto;
    private BreedUpdateDto breedUpdateDto;
    Pageable pageable;



    @BeforeEach
    void setUp() {

        species = createSpecies();
        testBreed = createBreed(species);
        breedCreateDto = primaryBreedCreateDto(species.getId());
        breedUpdateDto = breedUpdateDto();
        updatedBreed = createBreed(species);

        updatedBreed.setId("1234-1234-5678");
        updatedBreed.setName("Weimaraner");
        updatedBreed.setSpecies(species);
        int page = 0;
        int size = 10;
        String sort = "created_at";
        pageable = PageRequest.of(page, size, Sort.by(sort));
    }

    @Test
    @DisplayName("Test if get all breeds works correctly")
    void getAllBreedsShouldReturnBreeds() {

        Page<Breed> pagedBreeds = new PageImpl<>(List.of(testBreed));

        when(breedRepository.findAll(pageable)).thenReturn(pagedBreeds);

        List<BreedGetDto> result = breedService.getAll(pageable);

        assertEquals(1, result.size());
        assertEquals(testBreed.getName(), result.getFirst().name());
    }

    @Test
    @DisplayName("Test if get all breeds return empty list if no Breeds")
    void getAllBreedsShouldReturnEmpty(){

        Page<Breed> pagedBreeds = new PageImpl<>(List.of());

        when(breedRepository.findAll(pageable)).thenReturn(pagedBreeds);

        List<BreedGetDto> result = breedService.getAll(pageable);

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test if get all Breeds return number of elements of page size")
    void getAllBreedsShouldReturnNumberOfElementsOfPageSize(){

        Breed breedToAdd = new Breed();
        List<Breed> allBreeds = List.of(testBreed, updatedBreed, breedToAdd);
        Page<Breed> pagedBreeds = new PageImpl<>(List.of(testBreed, updatedBreed), pageable, allBreeds.size());

        when(breedRepository.findAll(pageable)).thenReturn(pagedBreeds);

        List<BreedGetDto> result = breedService.getAll(pageable);

        assertEquals(2, result.size());
        assertEquals(testBreed.getName(), result.getFirst().name());
        assertEquals(updatedBreed.getName(), result.get(1).name());
    }

    @Test
    @DisplayName("Test if get all Breeds return with Descending Order")
    void getAllBreedsShouldReturnBreedsInDescendingOrder(){

        Breed breedToAdd = new Breed();
        breedToAdd.setName("Labrador Retriever");
        breedToAdd.setSpecies(species);
        breedToAdd.setId("2313-2313-2313");

        List<Breed> allBreeds = List.of(updatedBreed,breedToAdd, testBreed);
        Page<Breed> pagedBreeds = new PageImpl<>(allBreeds, pageable, allBreeds.size());

        when(breedRepository.findAll(any(PageRequest.class))).thenReturn(pagedBreeds);

        List<BreedGetDto> result = breedService.getAll(pageable);

        assertNotNull(result);
        assertEquals(3,result.size());
        assertEquals(result.get(0).name(), updatedBreed.getName());
        assertEquals(result.get(1).name(), breedToAdd.getName());
        assertEquals(result.get(2).name(), testBreed.getName());

    }

    @Test
    @DisplayName("Test if get all Breeds return with Ascending Order")
    void getAllBreedsShouldReturnBreedsInAscendingOrder(){

        Breed breedToAdd = new Breed();
        breedToAdd.setName("Labrador Retriever");
        breedToAdd.setSpecies(species);
        breedToAdd.setId("2313-2313-2313");

        List<Breed> allBreeds = List.of(testBreed, breedToAdd, updatedBreed);
        Page<Breed> pagedBreeds = new PageImpl<>(allBreeds, pageable, allBreeds.size());

        when(breedRepository.findAll(any(PageRequest.class))).thenReturn(pagedBreeds);

        List<BreedGetDto> result = breedService.getAll(pageable);

        assertNotNull(result);
        assertEquals(3,result.size());
        assertEquals(result.get(0).name(), testBreed.getName());
        assertEquals(result.get(1).name(), breedToAdd.getName());
        assertEquals(result.get(2).name(), updatedBreed.getName());
    }

    @Test
    @DisplayName("Test if get Breed by id works correctly")
    void getBreedByIdShouldReturn() throws BreedNotFoundException {

        when(breedRepository.findById(testBreed.getId())).thenReturn(Optional.of(testBreed));

        BreedGetDto result = breedService.getById(testBreed.getId());

        assertEquals(testBreed.getName(), result.name());
    }

    @Test
    @DisplayName("Test if get Breed by id throws BreedNotFoundException")
    void getBreedByIdShouldThrowNotFoundException() {

        when(breedRepository.findById(testBreed.getId())).thenReturn(Optional.empty());

        assertThrows(BreedNotFoundException.class, () -> breedService.getById(testBreed.getId()));
    }

    
    @Test
    @DisplayName("Test if add new breed saves and returns BreedGetDto")
    void createGetDto() throws SpeciesNotFoundException {

        when(speciesService.getById(breedCreateDto.speciesId())).thenReturn(toDto(species));

        when(breedRepository.save(any(Breed.class))).thenReturn(testBreed);

        BreedGetDto result = breedService.create(breedCreateDto);

        assertNotNull(breedCreateDto);
        assertEquals(testBreed.getName(), result.name());
    }



    @Test
    @DisplayName("Test if add new breed throws exception if species is not found")
    void createShouldThrowSpeciesNotFoundException() throws SpeciesNotFoundException {

        when(speciesService.getById(breedCreateDto.speciesId())).thenThrow(SpeciesNotFoundException.class);

        assertThrows(SpeciesNotFoundException.class, () -> breedService.create(breedCreateDto));
    }

    @Test
    @DisplayName("Test if update breed saves all fields and returns BreedGetDto")
    void updateBreedShouldSaveAllFieldsAndReturnGetDto() throws BreedNotFoundException {

        when(breedRepository.findById(testBreed.getId())).thenReturn(Optional.of(testBreed));
        when(breedRepository.save(any(Breed.class))).thenReturn(updatedBreed);

        BreedGetDto result = breedService.update(testBreed.getId(), breedUpdateDto);

        assertNotNull(breedUpdateDto);
        assertEquals(breedUpdateDto.name(), result.name());
    }

    @Test
    @DisplayName("Test if update breed throws exception breed not found")
    void updateBreedShouldThrowNotFoundException() {

        when(breedRepository.findById(testBreed.getId())).thenReturn(Optional.empty());

        assertThrows(BreedNotFoundException.class, () -> breedService.update(testBreed.getId(), breedUpdateDto));
    }


    @Test
    @DisplayName("Test if delete breed works correctly")
    void deleteShouldWorkCorrectly() throws BreedNotFoundException {

        when(breedRepository.findById(testBreed.getId())).thenReturn(Optional.of(testBreed));

        assertEquals(breedService.delete(testBreed.getId()), format(BREED_DELETE_MESSAGE, testBreed.getId()));
    }

    @Test
    @DisplayName("Test if delete breed throws exception breed not found")
    void deleteBreedShouldThrowNotFoundException() {

        when(breedRepository.findById(testBreed.getId())).thenReturn(Optional.empty());

        assertThrows(BreedNotFoundException.class, () -> breedService.delete(testBreed.getId()));
    }
}