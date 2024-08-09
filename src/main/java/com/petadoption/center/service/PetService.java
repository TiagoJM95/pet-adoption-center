package com.petadoption.center.service;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;

import java.util.List;

public interface PetService {

    List<PetGetDto> getAllPets();

    PetGetDto getPetById(Long id);

    PetGetDto addNewPet(PetCreateDto pet);

    PetGetDto updatePet(Long id, PetUpdateDto pet);
}
