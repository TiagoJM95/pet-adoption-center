package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.petSpecies.PetSpeciesCreateDto;
import com.petadoption.center.dto.petSpecies.PetSpeciesGetDto;
import com.petadoption.center.dto.petSpecies.PetSpeciesUpdateDto;
import com.petadoption.center.repository.PetSpeciesRepository;
import com.petadoption.center.service.PetSpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetSpeciesServiceImpl implements PetSpeciesService {

    @Autowired
    private PetSpeciesRepository petSpeciesRepository;

    @Override
    public List<PetSpeciesGetDto> getAllPetSpecies() {
        return List.of();
    }

    @Override
    public PetSpeciesGetDto getPetSpeciesById(Long id) {
        return null;
    }

    @Override
    public PetSpeciesGetDto addNewPetSpecies(PetSpeciesCreateDto petSpecies) {
        return null;
    }

    @Override
    public PetSpeciesGetDto updatePetSpecies(Long id, PetSpeciesUpdateDto PetSpecies) {
        return null;
    }
}
