package com.petadoption.center.service;

import com.petadoption.center.dto.petSpecies.PetSpeciesCreateDto;
import com.petadoption.center.dto.petSpecies.PetSpeciesGetDto;
import com.petadoption.center.dto.petSpecies.PetSpeciesUpdateDto;

import java.util.List;

public interface PetSpeciesService {
    List<PetSpeciesGetDto> getAllPetSpecies();

    PetSpeciesGetDto getPetSpeciesById(Long id);

    PetSpeciesGetDto addNewPetSpecies(PetSpeciesCreateDto petSpecies);

    PetSpeciesGetDto updatePetSpecies(Long id, PetSpeciesUpdateDto PetSpecies);
}
