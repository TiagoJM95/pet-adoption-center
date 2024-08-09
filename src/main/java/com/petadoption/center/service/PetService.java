package com.petadoption.center.service;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetPatchDto;

import java.util.List;

public interface PetService {

    List<PetGetDto> getAllPets();

    PetGetDto getPetById(Long petId);

    PetGetDto addNewPet(PetCreateDto petCreateDto);

    PetGetDto updatePet(Long PetId, PetPatchDto petPatchDto);
}
