package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BreedServiceI {
    List<BreedGetDto> getAll(Pageable pageable);
    BreedGetDto getById(String id);
    List<BreedGetDto> getBySpecies(String species);
    BreedGetDto create(BreedCreateDto breed);
    BreedGetDto update(String id, BreedUpdateDto breed);
    String delete(String id);
    void verifyIfBreedsAndSpeciesMatch(PetCreateDto dto);
}