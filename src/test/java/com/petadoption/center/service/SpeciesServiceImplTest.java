package com.petadoption.center.service;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.exception.species.SpeciesDuplicateException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Species;
import com.petadoption.center.repository.SpeciesRepository;
import com.petadoption.center.service.implementation.SpeciesServiceImpl;
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

import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.SPECIES_WITH_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class SpeciesServiceImplTest {

    @InjectMocks
    private SpeciesServiceImpl speciesService;

    @Mock
    private SpeciesRepository speciesRepository;

    private Species testSpecies;
    private Species updatedSpecies;
    private SpeciesCreateDto speciesCreateDto;
    private SpeciesUpdateDto speciesUpdateDto;

    @BeforeEach
    void setUp() {
        testSpecies = new Species();
        testSpecies.setId(1L);
        testSpecies.setName("Dog");

        updatedSpecies = new Species();
        updatedSpecies.setId(2L);
        updatedSpecies.setName("Cat");

        speciesCreateDto = new SpeciesCreateDto("Dog");

        speciesUpdateDto = new SpeciesUpdateDto("Cat");
    }

    @Test
    @DisplayName("Test if get all species return empty list if no species")
    void getAllPetSpeciesShouldReturnEmpty() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<Species> pagedSpecies = new PageImpl<>(List.of());

        when(speciesRepository.findAll(pageRequest)).thenReturn(pagedSpecies);

        List<SpeciesGetDto> result = speciesService.getAllSpecies(0, 10, "id");

        assertEquals(0, result.size());
    }


    @Test
    @DisplayName("Test if get all species is working correctly")
    void getAllPetSpeciesShouldReturnSpecies() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<Species> pagedSpecies = new PageImpl<>(List.of(testSpecies));

        when(speciesRepository.findAll(pageRequest)).thenReturn(pagedSpecies);

        List<SpeciesGetDto> result = speciesService.getAllSpecies(0, 10, "id");

        assertEquals(1, result.size());
        assertEquals(testSpecies.getName(), result.getFirst().name());
    }

    @Test
    @DisplayName("Test if get all species return number of elements of page size")
    void getAllPetSpeciesShouldReturnNumberOfElementsOfPageSize() {

        Species speciesToAdd = new Species();
        List<Species> allSpecies = List.of(testSpecies, updatedSpecies, speciesToAdd);
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<Species> pagedSpecies = new PageImpl<>(List.of(testSpecies, updatedSpecies), pageRequest, allSpecies.size());

        when(speciesRepository.findAll(pageRequest)).thenReturn(pagedSpecies);

        List<SpeciesGetDto> result = speciesService.getAllSpecies(0, 10, "id");

        assertEquals(2, result.size());
        assertEquals(testSpecies.getName(), result.get(0).name());
        assertEquals(updatedSpecies.getName(), result.get(1).name());
    }

    @Test
    @DisplayName("Test if get all species return with descending order")
    void getAllPetSpeciesShouldReturnWithDescendingOrder() {

        Species speciesToAdd = new Species();
        speciesToAdd.setId(10L);
        List<Species> allSpecies = List.of(speciesToAdd, updatedSpecies, testSpecies);
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "id");
        Page<Species> pagedSpecies = new PageImpl<>(allSpecies, pageRequest, allSpecies.size());

        when(speciesRepository.findAll(any(PageRequest.class))).thenReturn(pagedSpecies);

        List<SpeciesGetDto> result = speciesService.getAllSpecies(0, 3, "id");

        assertEquals(3, result.size());
        assertTrue(result.get(0).id() > result.get(1).id());
        assertTrue(result.get(1).id() > result.get(2).id());
    }

    @Test
    @DisplayName("Test if get all species return with ascending order")
    void getAllPetSpeciesShouldReturnWithAscendingOrder() {

        Species speciesToAdd = new Species();
        speciesToAdd.setId(10L);
        List<Species> allSpecies = List.of( testSpecies, updatedSpecies, speciesToAdd);
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.ASC, "id");
        Page<Species> pagedSpecies = new PageImpl<>(allSpecies, pageRequest, allSpecies.size());

        when(speciesRepository.findAll(any(PageRequest.class))).thenReturn(pagedSpecies);

        List<SpeciesGetDto> result = speciesService.getAllSpecies(0, 3, "id");

        assertEquals(3, result.size());
        assertTrue(result.get(0).id() < result.get(1).id());
        assertTrue(result.get(1).id() < result.get(2).id());
    }

    @Test
    @DisplayName("Test if get species by id is working correctly")
    void getPetSpeciesByIdShouldReturnSpecies() throws SpeciesNotFoundException {

        when(speciesRepository.findById(1L)).thenReturn(Optional.of(testSpecies));

        SpeciesGetDto result = speciesService.getSpeciesById(1L);

        assertEquals(testSpecies.getName(), result.name());
    }

    @Test
    @DisplayName("Test if get species by id throw exception if not found")
    void getPetSpeciesByIdShouldThrowException() {

        when(speciesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SpeciesNotFoundException.class, () -> speciesService.getSpeciesById(1L));
    }

    @Test
    @DisplayName("Test if add new species saves and returns SpeciesGetDto")
    void addPetSpeciesShouldSaveAndReturnSpecies() throws SpeciesDuplicateException {

        when(speciesRepository.findByName(testSpecies.getName())).thenReturn(Optional.empty());

        when(speciesRepository.save(any(Species.class))).thenReturn(testSpecies);

        SpeciesGetDto result = speciesService.addNewSpecies(speciesCreateDto);

        assertNotNull(result);
        assertEquals(testSpecies.getName(), result.name());
    }

    @Test
    @DisplayName("Test if add new species throws SpeciesNameDuplicateException")
    void addPetSpeciesShouldThrowException() {

        when(speciesRepository.findByName(speciesCreateDto.name())).thenReturn(Optional.of(testSpecies));

        assertThrows(SpeciesDuplicateException.class, () -> speciesService.addNewSpecies(speciesCreateDto));
    }

    @Test
    @DisplayName("Test if update species saves and returns SpeciesGetDto")
    void updatePetSpeciesShouldSaveAndReturnSpecies() throws SpeciesNotFoundException, SpeciesDuplicateException {

        when(speciesRepository.findByName(speciesUpdateDto.name())).thenReturn(Optional.empty());

        when(speciesRepository.findById(testSpecies.getId())).thenReturn(Optional.of(testSpecies));

        when(speciesRepository.save(any(Species.class))).thenReturn(updatedSpecies);

        SpeciesGetDto result = speciesService.updateSpecies(testSpecies.getId(), speciesUpdateDto);

        assertNotNull(speciesUpdateDto);
        assertEquals(speciesUpdateDto.name(), result.name());
    }

    @Test
    @DisplayName("Test if update species throws SpeciesNotFoundException")
    void updatePetSpeciesShouldThrowNotFoundException(){

        when(speciesRepository.findById(testSpecies.getId())).thenReturn(Optional.empty());

        assertThrows(SpeciesNotFoundException.class, () -> speciesService.updateSpecies(testSpecies.getId(), speciesUpdateDto));
    }

    @Test
    @DisplayName("Test if update species throws SpeciesNameDuplicateException")
    void updatePetSpeciesShouldThrowDuplicatedNameException(){

        when(speciesRepository.findById(testSpecies.getId())).thenReturn(Optional.of(testSpecies));

        when(speciesRepository.findByName(speciesUpdateDto.name())).thenReturn(Optional.of(testSpecies));

        assertThrows(SpeciesDuplicateException.class, () -> speciesService.updateSpecies(testSpecies.getId(), speciesUpdateDto));
    }

    @Test
    @DisplayName("Test if delete species removes and returns SpeciesGetDto")
    void deletePetSpeciesShouldRemoveAndReturnSpecies() throws SpeciesNotFoundException {

        when(speciesRepository.findById(testSpecies.getId())).thenReturn(Optional.of(testSpecies));

        assertEquals(speciesService.deleteSpecies(testSpecies.getId()), SPECIES_WITH_ID + testSpecies.getId() + DELETE_SUCCESS);
    }

    @Test
    @DisplayName("Test if delete species throws SpeciesNotFoundException")
    void deletePetSpeciesShouldThrowNotFoundException(){

        when(speciesRepository.findById(testSpecies.getId())).thenReturn(Optional.empty());

        assertThrows(SpeciesNotFoundException.class, () -> speciesService.deleteSpecies(testSpecies.getId()));
    }
}
