package com.petadoption.center.service;

import com.petadoption.center.dto.petBreed.PetBreedCreateDto;
import com.petadoption.center.dto.petBreed.PetBreedGetDto;
import com.petadoption.center.dto.petBreed.PetBreedUpdateDto;

import java.util.List;

public interface PetBreedService {

    List<PetBreedGetDto> getAllPetBreeds();

    PetBreedGetDto getPetBreedById(Long id);

    PetBreedGetDto addNewPetBreed(PetBreedCreateDto petBreed);

    PetBreedGetDto updatePetBreed(Long id, PetBreedUpdateDto petBreed);

}
