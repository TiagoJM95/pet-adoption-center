package com.petadoption.center.service;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.exception.breed.BreedNameDuplicateException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;

import java.util.List;

public interface BreedService {

    List<BreedGetDto> getAllBreeds();

    BreedGetDto getBreedById(Long id) throws BreedNotFoundException;

    List<BreedGetDto> getBreedsBySpecies(String species) throws SpeciesNotFoundException;

    BreedGetDto addNewBreed(BreedCreateDto breed) throws BreedNameDuplicateException, SpeciesNotFoundException;

    BreedGetDto updateBreed(Long id, BreedUpdateDto breed) throws BreedNotFoundException, BreedNameDuplicateException;

}
