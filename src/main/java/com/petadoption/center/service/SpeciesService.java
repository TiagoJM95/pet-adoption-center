package com.petadoption.center.service;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.exception.species.SpeciesNameDuplicateException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.exception.user.UserNotFoundException;

import java.util.List;

public interface SpeciesService {
    List<SpeciesGetDto> getAllPetSpecies(int page, int size, String sortBy);

    SpeciesGetDto getPetSpeciesById(Long id) throws SpeciesNotFoundException;

    SpeciesGetDto addNewPetSpecies(SpeciesCreateDto species) throws SpeciesNameDuplicateException;

    SpeciesGetDto updatePetSpecies(Long id, SpeciesUpdateDto species) throws SpeciesNotFoundException, SpeciesNameDuplicateException;

    String deleteSpecies(Long id) throws SpeciesNotFoundException;
}
