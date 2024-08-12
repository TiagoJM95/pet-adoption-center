package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.repository.SpeciesRepository;
import com.petadoption.center.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    @Autowired
    private SpeciesRepository petSpeciesRepository;

    @Override
    public List<SpeciesGetDto> getAllPetSpecies() {
        return List.of();
    }

    @Override
    public SpeciesGetDto getPetSpeciesById(Long id) {
        return null;
    }

    @Override
    public SpeciesGetDto addNewPetSpecies(SpeciesCreateDto species) {
        return null;
    }

    @Override
    public SpeciesGetDto updatePetSpecies(Long id, SpeciesUpdateDto species) {
        return null;
    }
}
