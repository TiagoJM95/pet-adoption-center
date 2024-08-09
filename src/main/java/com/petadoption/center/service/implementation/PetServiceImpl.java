package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetPatchDto;
import com.petadoption.center.service.PetService;

import java.util.List;

public class PetServiceImpl implements PetService {
    @Override
    public List<PetGetDto> getAllPets() {
        return List.of();
    }

    @Override
    public PetGetDto getPetById(Long petId) {
        return null;
    }

    @Override
    public PetGetDto addNewPet(PetCreateDto petCreateDto) {
        return null;
    }

    @Override
    public PetGetDto updatePet(Long PetId, PetPatchDto petPatchDto) {
        return null;
    }
}
