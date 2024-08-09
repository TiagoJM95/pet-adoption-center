package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.repository.PetRepository;
import com.petadoption.center.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Override
    public List<PetGetDto> getAllPets() {
        return List.of();
    }

    @Override
    public PetGetDto getPetById(Long id) {
        return null;
    }

    @Override
    public PetGetDto addNewPet(PetCreateDto pet) {
        return null;
    }

    @Override
    public PetGetDto updatePet(Long id, PetUpdateDto pet) {
        return null;
    }
}
