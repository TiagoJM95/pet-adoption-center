package com.petadoption.center.service;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.exception.species.SpeciesDuplicateException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;

import java.util.List;

public interface SpeciesService {

    List<SpeciesGetDto> getAllSpecies(int page, int size, String sortBy);
    SpeciesGetDto getSpeciesById(String id) throws SpeciesNotFoundException;
    SpeciesGetDto getSpeciesByName(String name) throws SpeciesNotFoundException;
    SpeciesGetDto addNewSpecies(SpeciesCreateDto species) throws SpeciesDuplicateException;
    SpeciesGetDto updateSpecies(String id, SpeciesUpdateDto species) throws SpeciesNotFoundException, SpeciesDuplicateException;
    String deleteSpecies(String id) throws SpeciesNotFoundException;
}
