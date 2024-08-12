package com.petadoption.center.service;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;

import java.util.List;

public interface BreedService {

    List<BreedGetDto> getAllPetBreeds();

    BreedGetDto getPetBreedById(Long id);

    BreedGetDto addNewPetBreed(BreedCreateDto breed);

    BreedGetDto updatePetBreed(Long id, BreedUpdateDto breed);

}
