package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.petBreed.PetBreedCreateDto;
import com.petadoption.center.dto.petBreed.PetBreedGetDto;
import com.petadoption.center.dto.petBreed.PetBreedUpdateDto;
import com.petadoption.center.repository.PetBreedRepository;
import com.petadoption.center.service.PetBreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetBreedServiceImpl implements PetBreedService {

    @Autowired
    private PetBreedRepository petBreedRepository;

    @Override
    public List<PetBreedGetDto> getAllPetBreeds() {
        return List.of();
    }

    @Override
    public PetBreedGetDto getPetBreedById(Long id) {
        return null;
    }

    @Override
    public PetBreedGetDto addNewPetBreed(PetBreedCreateDto petBreed) {
        return null;
    }

    @Override
    public PetBreedGetDto updatePetBreed(Long id, PetBreedUpdateDto petBreed) {
        return null;
    }
}
