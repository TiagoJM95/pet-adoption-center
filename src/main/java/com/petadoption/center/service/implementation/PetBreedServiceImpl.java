package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.petBreed.PetBreedCreateDto;
import com.petadoption.center.dto.petBreed.PetBreedGetDto;
import com.petadoption.center.dto.petBreed.PetBreedPatchDto;
import com.petadoption.center.repository.PetBreedRepository;
import com.petadoption.center.service.PetBreedService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetBreedServiceImpl implements PetBreedService {

    private final PetBreedRepository petBreedRepository;

    public PetBreedServiceImpl(PetBreedRepository petBreedRepository) {
        this.petBreedRepository = petBreedRepository;
    }

    @Override
    public List<PetBreedGetDto> getAllPetBreeds() {
        return List.of();
    }

    @Override
    public PetBreedGetDto getPetBreedById(Long petBreedId) {
        return null;
    }

    @Override
    public PetBreedGetDto addNewPetBreed(PetBreedCreateDto petBreedCreateDto) {
        return null;
    }

    @Override
    public PetBreedGetDto updatePetBreed(Long petBreedId, PetBreedPatchDto petBreedPatchDto) {
        return null;
    }
}
