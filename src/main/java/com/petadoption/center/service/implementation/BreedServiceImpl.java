package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.repository.BreedRepository;
import com.petadoption.center.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BreedServiceImpl implements BreedService {

    @Autowired
    private BreedRepository petBreedRepository;

    @Override
    public List<BreedGetDto> getAllPetBreeds() {
        return List.of();
    }

    @Override
    public BreedGetDto getPetBreedById(Long id) {
        return null;
    }

    @Override
    public BreedGetDto addNewPetBreed(BreedCreateDto breed) {
        return null;
    }

    @Override
    public BreedGetDto updatePetBreed(Long id, BreedUpdateDto breed) {
        return null;
    }
}
