package com.petadoption.center.service;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.exception.breed.BreedDuplicateException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
import com.petadoption.center.repository.BreedRepository;
import com.petadoption.center.repository.SpeciesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.petadoption.center.converter.SpeciesConverter.toDto;
import static com.petadoption.center.util.Messages.BREED_WITH_ID;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
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



    @BeforeEach
    void setUp() {

        species = new Species("123123-12312312-3123", "Dog");

        testBreed = new Breed();
        testBreed.setId("1111-1111-2222");
        testBreed.setName("Golden Retriever");
        testBreed.setSpecies(species);

        updatedBreed = new Breed();
        updatedBreed.setId("1234-1234-5678");
        updatedBreed.setName("Weimaraner");
        updatedBreed.setSpecies(species);

        breedCreateDto = new BreedCreateDto(
                "Golden Retriever",
                "123123-12312312-3123"
        );

        breedUpdateDto = new BreedUpdateDto(
                "Weimaraner"
        );
    }

    @Test
    @DisplayName("Test if get all breeds works correctly")
    void getAllBreedsShouldReturnBreeds() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<Breed> pagedBreeds = new PageImpl<>(List.of(testBreed));

        when(breedRepository.findAll(pageRequest)).thenReturn(pagedBreeds);

        List<BreedGetDto> result = breedService.getAllBreeds(0,10,"id");

        assertEquals(1, result.size());
        assertEquals(testBreed.getName(), result.getFirst().name());
    }

    @Test
    @DisplayName("Test if get all breeds return empty list if no Breeds")
    void getAllBreedsShouldReturnEmpty(){

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<Breed> pagedBreeds = new PageImpl<>(List.of());

        when(breedRepository.findAll(pageRequest)).thenReturn(pagedBreeds);

        List<BreedGetDto> result = breedService.getAllBreeds(0,10,"id");

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test if get all Breeds return number of elements of page size")
    void getAllBreedsShouldReturnNumberOfElementsOfPageSize(){

        Breed breedToAdd = new Breed();
        List<Breed> allBreeds = List.of(testBreed, updatedBreed, breedToAdd);
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.ASC, "id");
        Page<Breed> pagedBreeds = new PageImpl<>(List.of(testBreed, updatedBreed), pageRequest, allBreeds.size());

        when(breedRepository.findAll(pageRequest)).thenReturn(pagedBreeds);

        List<BreedGetDto> result = breedService.getAllBreeds(0,2,"id");

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
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "name");
        Page<Breed> pagedBreeds = new PageImpl<>(allBreeds, pageRequest, allBreeds.size());

        when(breedRepository.findAll(any(PageRequest.class))).thenReturn(pagedBreeds);

        List<BreedGetDto> result = breedService.getAllBreeds(0,3,"name");

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
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.ASC, "name");
        Page<Breed> pagedBreeds = new PageImpl<>(allBreeds, pageRequest, allBreeds.size());

        when(breedRepository.findAll(any(PageRequest.class))).thenReturn(pagedBreeds);

        List<BreedGetDto> result = breedService.getAllBreeds(0,3,"name");

        assertNotNull(result);
        assertEquals(3,result.size());
        assertEquals(result.get(0).name(), testBreed.getName());
        assertEquals(result.get(1).name(), breedToAdd.getName());
        assertEquals(result.get(2).name(), updatedBreed.getName());
    }

    @Test
    @DisplayName("Test if get Breed by id works correctly")
    void getBreedByIdShouldReturnBreed() throws BreedNotFoundException {

        when(breedRepository.findById(testBreed.getId())).thenReturn(Optional.of(testBreed));

        BreedGetDto result = breedService.getBreedById(testBreed.getId());

        assertEquals(testBreed.getName(), result.name());
    }

    @Test
    @DisplayName("Test if get Breed by id throws BreedNotFoundException")
    void getBreedByIdShouldThrowBreedNotFoundException() {

        when(breedRepository.findById(testBreed.getId())).thenReturn(Optional.empty());

        assertThrows(BreedNotFoundException.class, () -> breedService.getBreedById(testBreed.getId()));
    }

    
    @Test
    @DisplayName("Test if add new breed saves and returns BreedGetDto")
    void addNewBreedShouldReturnBreedGetDto() throws BreedDuplicateException, SpeciesNotFoundException {

        when(breedRepository.findByName(breedCreateDto.name())).thenReturn(Optional.empty());

        when(speciesService.getSpeciesById(breedCreateDto.speciesId())).thenReturn(toDto(species));

        when(breedRepository.save(any(Breed.class))).thenReturn(testBreed);

        BreedGetDto result = breedService.addNewBreed(breedCreateDto);

        assertNotNull(breedCreateDto);
        assertEquals(testBreed.getName(), result.name());
    }


   /* @Test
    @DisplayName("Test if add new user throws exception if name is duplicated")
    void addNewBreedShouldThrowBreedNameDuplicateException() {

        when(breedRepository.findByName(breedCreateDto.name())).thenReturn(Optional.of(testBreed));

        assertThrows(BreedDuplicateException.class, () -> breedService.addNewBreed(breedCreateDto));
    }*/

    @Test
    @DisplayName("Test if add new breed throws exception if species is not found")
    void addNewBreedShouldThrowSpeciesNotFoundException() throws SpeciesNotFoundException {

        when(breedRepository.save(any(Breed.class))).thenReturn(testBreed);

        when(speciesService.getSpeciesById(breedCreateDto.speciesId())).thenThrow(SpeciesNotFoundException.class);

        assertThrows(SpeciesNotFoundException.class, () -> breedService.addNewBreed(breedCreateDto));
    }

    @Test
    @DisplayName("Test if update breed saves all fields and returns BreedGetDto")
    void updateBreedShouldSaveAllFieldsAndReturnBreedGetDto() throws BreedDuplicateException, BreedNotFoundException {

        when(breedRepository.findById(testBreed.getId())).thenReturn(Optional.of(testBreed));
        when(breedRepository.save(any(Breed.class))).thenReturn(updatedBreed);

        BreedGetDto result = breedService.updateBreed(testBreed.getId(), breedUpdateDto);

        assertNotNull(breedUpdateDto);
        assertEquals(breedUpdateDto.name(), result.name());
    }

    @Test
    @DisplayName("Test if update breed throws exception breed not found")
    void updateBreedShouldThrowBreedNotFoundException() {

        when(breedRepository.findById(testBreed.getId())).thenReturn(Optional.empty());

        assertThrows(BreedNotFoundException.class, () -> breedService.updateBreed(testBreed.getId(), breedUpdateDto));
    }


/*    @Test
    @DisplayName("Test if update breed throws exception if name is duplicated")
    void updateBreedShouldThrowBreedNameDuplicateException() {

        when(breedRepository.findById(testBreed.getId())).thenReturn(Optional.of(testBreed));

        when(breedRepository.findByName(breedUpdateDto.name())).thenReturn(Optional.of(updatedBreed));

        assertThrows(BreedDuplicateException.class, () -> breedService.updateBreed(testBreed.getId(), breedUpdateDto));
    }*/


    @Test
    @DisplayName("Test if delete breed works correctly")
    void deleteBreedShouldWorkCorrectly() throws BreedNotFoundException {

        when(breedRepository.findById(testBreed.getId())).thenReturn(Optional.of(testBreed));

        assertEquals(breedService.deleteBreed(testBreed.getId()),BREED_WITH_ID + testBreed.getId() + DELETE_SUCCESS);
    }

    @Test
    @DisplayName("Test if delete breed throws exception breed not found")
    void deleteBreedShouldThrowBreedNotFoundException() {

        when(breedRepository.findById(testBreed.getId())).thenReturn(Optional.empty());

        assertThrows(BreedNotFoundException.class, () -> breedService.deleteBreed(testBreed.getId()));
    }
}