package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.exception.BreedMismatchException;
import com.petadoption.center.exception.not_found.BreedNotFoundException;
import com.petadoption.center.exception.not_found.SpeciesNotFoundException;

import java.util.List;

public interface BreedServiceI {

    List<BreedGetDto> getAllBreeds(int page, int size, String sortBy);
    BreedGetDto getBreedById(String id) throws BreedNotFoundException;
    List<BreedGetDto> getBreedsBySpecies(String species) throws SpeciesNotFoundException;
    BreedGetDto addNewBreed(BreedCreateDto breed) throws SpeciesNotFoundException;
    BreedGetDto updateBreed(String id, BreedUpdateDto breed) throws BreedNotFoundException;
    String deleteBreed(String id) throws BreedNotFoundException;
    void verifyIfBreedsAndSpeciesMatch(PetCreateDto dto) throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException;
}