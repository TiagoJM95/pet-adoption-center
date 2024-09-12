package com.petadoption.center.service;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.exception.breed.BreedDuplicateException;
import com.petadoption.center.exception.breed.BreedMismatchException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;

import java.util.List;

public interface BreedService {

    List<BreedGetDto> getAllBreeds(int page, int size, String sortBy);
    BreedGetDto getBreedById(String id) throws BreedNotFoundException;
    List<BreedGetDto> getBreedsBySpecies(String species) throws SpeciesNotFoundException;
    BreedGetDto addNewBreed(BreedCreateDto breed) throws BreedDuplicateException, SpeciesNotFoundException;
    BreedGetDto updateBreed(String id, BreedUpdateDto breed) throws BreedNotFoundException, BreedDuplicateException;
    String deleteBreed(String id) throws BreedNotFoundException;
    void verifyIfBreedsAndSpeciesMatch(PetCreateDto dto) throws BreedNotFoundException, BreedMismatchException;
}
