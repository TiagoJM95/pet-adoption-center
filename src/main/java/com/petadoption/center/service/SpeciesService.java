package com.petadoption.center.service;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;

import java.util.List;

public interface SpeciesService {
    List<SpeciesGetDto> getAllPetSpecies();

    SpeciesGetDto getPetSpeciesById(Long id);

    SpeciesGetDto addNewPetSpecies(SpeciesCreateDto species);

    SpeciesGetDto updatePetSpecies(Long id, SpeciesUpdateDto species);
}
