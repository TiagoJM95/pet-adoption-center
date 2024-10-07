package com.petadoption.center.service;

import com.petadoption.center.converter.BreedConverter;
import com.petadoption.center.converter.SpeciesConverter;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.exception.BreedMismatchException;
import com.petadoption.center.exception.not_found.BreedNotFoundException;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
import com.petadoption.center.repository.BreedRepository;
import com.petadoption.center.service.interfaces.BreedServiceI;
import com.petadoption.center.service.interfaces.SpeciesServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Utils.updateFields;

@Service
public class BreedService implements BreedServiceI {

    private final BreedRepository breedRepository;
    private final SpeciesServiceI speciesServiceI;

    @Autowired
    public BreedService(BreedRepository breedRepository, SpeciesServiceI speciesServiceI) {
        this.breedRepository = breedRepository;
        this.speciesServiceI = speciesServiceI;
    }

    @Override
    public List<BreedGetDto> getAll(Pageable pageable) {
        return breedRepository.findAll(pageable).stream().map(BreedConverter::toDto).toList();
    }

    @Override
    public BreedGetDto getById(String id) {
        return BreedConverter.toDto(findById(id));
    }

    @Override
    public List<BreedGetDto> getBySpecies(String speciesName) {
        Species species = SpeciesConverter.toModel(speciesServiceI.getSpeciesByName(speciesName));
        return breedRepository.findBySpecies(species).stream()
                .map(BreedConverter::toDto).toList();
    }

    @Override
    public BreedGetDto create(BreedCreateDto dto) {
        Breed breed = BreedConverter.toModel(dto);
        Species species = SpeciesConverter.toModel(speciesServiceI.getSpeciesById(dto.speciesId()));
        breed.setSpecies(species);
        return BreedConverter.toDto(breedRepository.save(breed));
    }

    @Override
    public BreedGetDto update(String id, BreedUpdateDto dto) {
        Breed breed = findById(id);
        updateFields(dto.name(), breed.getName(), breed::setName);
        return BreedConverter.toDto(breedRepository.save(breed));
    }

    @Override
    public String delete(String id) {
        findById(id);
        breedRepository.deleteById(id);
        return BREED_WITH_ID + id + DELETE_SUCCESS;
    }

    @Override
    public void verifyIfBreedsAndSpeciesMatch(PetCreateDto dto) {

        Species species = SpeciesConverter.toModel(speciesServiceI.getSpeciesById(dto.petSpeciesId()));
        Breed primaryBreed = findById(dto.primaryBreedId());
        Breed secondaryBreed;

        if(!Objects.equals(primaryBreed.getSpecies(), species)) {
            throw new BreedMismatchException(BREED_SPECIES_MISMATCH);
        }

        if(dto.secondaryBreedId() != null) {
            secondaryBreed = findById(dto.secondaryBreedId());
            if(!Objects.equals(secondaryBreed.getSpecies(), species)) {
                throw new BreedMismatchException(BREED_SPECIES_MISMATCH);
            }
        }
    }

    private Breed findById(String id) {
        return breedRepository.findById(id).orElseThrow(
                () -> new BreedNotFoundException(BREED_WITH_ID + id + NOT_FOUND));
    }
}