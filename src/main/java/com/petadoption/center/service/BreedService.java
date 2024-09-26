package com.petadoption.center.service;

import com.petadoption.center.converter.BreedConverter;
import com.petadoption.center.converter.SpeciesConverter;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.exception.breed.BreedMismatchException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
import com.petadoption.center.repository.BreedRepository;
import com.petadoption.center.service.interfaces.BreedServiceI;
import com.petadoption.center.service.interfaces.SpeciesServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public List<BreedGetDto> getAllBreeds(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        return breedRepository.findAll(pageRequest).stream().map(BreedConverter::toDto).toList();
    }

    @Override
    public BreedGetDto getBreedById(String id) throws BreedNotFoundException {
        return BreedConverter.toDto(findBreedById(id));
    }

    @Override
    public List<BreedGetDto> getBreedsBySpecies(String species) throws SpeciesNotFoundException {
        Species filterSpecies = SpeciesConverter.toModel(speciesServiceI.getSpeciesByName(species));
        return breedRepository.findBySpecies(filterSpecies).stream()
                .map(BreedConverter::toDto).toList();
    }

    @Override
    public BreedGetDto addNewBreed(BreedCreateDto dto) throws SpeciesNotFoundException {
        Breed breed = BreedConverter.toModel(dto);
        Species species = SpeciesConverter.toModel(speciesServiceI.getSpeciesById(dto.speciesId()));
        breed.setSpecies(species);
        return BreedConverter.toDto(breedRepository.save(breed));
    }

    @Override
    public BreedGetDto updateBreed(String id, BreedUpdateDto dto) throws BreedNotFoundException {
        Breed breed = findBreedById(id);
        updateFields(dto.name(), breed.getName(), breed::setName);
        return BreedConverter.toDto(breedRepository.save(breed));
    }

    @Override
    public String deleteBreed(String id) throws BreedNotFoundException {
        findBreedById(id);
        breedRepository.deleteById(id);
        return BREED_WITH_ID + id + DELETE_SUCCESS;
    }

    @Override
    public void verifyIfBreedsAndSpeciesMatch(PetCreateDto dto) throws BreedNotFoundException, BreedMismatchException {
        Breed primaryBreed = findBreedById(dto.primaryBreedId());
        Breed secondaryBreed;

        if(!Objects.equals(primaryBreed.getSpecies().getId(), dto.petSpeciesId())) {
            throw new BreedMismatchException(BREED_SPECIES_MISMATCH);
        }

        if(dto.secondaryBreedId() != null) {
            secondaryBreed = findBreedById(dto.secondaryBreedId());
            if(!Objects.equals(secondaryBreed.getSpecies().getId(), dto.petSpeciesId())) {
                throw new BreedMismatchException(BREED_SPECIES_MISMATCH);
            }
        }
    }

    private Breed findBreedById(String id) throws BreedNotFoundException {
        return breedRepository.findById(id).orElseThrow(
                () -> new BreedNotFoundException(BREED_WITH_ID + id + NOT_FOUND));
    }
}