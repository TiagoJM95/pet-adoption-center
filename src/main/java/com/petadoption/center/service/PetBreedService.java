package com.petadoption.center.service;

import com.petadoption.center.dto.petBreed.PetBreedCreateDto;
import com.petadoption.center.dto.petBreed.PetBreedGetDto;
import com.petadoption.center.dto.petBreed.PetBreedPatchDto;

import java.util.List;

public interface PetBreedService {

    List<PetBreedGetDto> getAllPetBreeds();

    PetBreedGetDto getPetBreedById(Long petBreedId);

    PetBreedGetDto addNewPetBreed(PetBreedCreateDto petBreedCreateDto);

    PetBreedGetDto updatePetBreed(Long petBreedId, PetBreedPatchDto petBreedPatchDto);

}
