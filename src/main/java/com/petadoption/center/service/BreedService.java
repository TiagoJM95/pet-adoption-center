package com.petadoption.center.service;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.exception.breed.BreedDuplicateException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;

import java.util.List;

public interface BreedService {

    List<BreedGetDto> getAllBreeds(int page, int size, String sortBy);
    BreedGetDto getBreedById(Long id) throws BreedNotFoundException;
    List<BreedGetDto> getBreedsBySpecies(String species) throws SpeciesNotFoundException;
    BreedGetDto addNewBreed(BreedCreateDto breed) throws BreedDuplicateException, SpeciesNotFoundException;
    BreedGetDto updateBreed(Long id, BreedUpdateDto breed) throws BreedNotFoundException, BreedDuplicateException;
    String deleteBreed(Long id) throws BreedNotFoundException;
}
