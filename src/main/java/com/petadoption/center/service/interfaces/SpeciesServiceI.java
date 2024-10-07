package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.exception.not_found.SpeciesNotFoundException;

import java.util.List;

public interface SpeciesServiceI {
    List<SpeciesGetDto> getAllSpecies(int page, int size, String sortBy);
    SpeciesGetDto getSpeciesById(String id) throws SpeciesNotFoundException;
    SpeciesGetDto getSpeciesByName(String name) throws SpeciesNotFoundException;
    SpeciesGetDto addNewSpecies(SpeciesCreateDto species);
    SpeciesGetDto updateSpecies(String id, SpeciesUpdateDto species) throws SpeciesNotFoundException;
    String deleteSpecies(String id) throws SpeciesNotFoundException;
}