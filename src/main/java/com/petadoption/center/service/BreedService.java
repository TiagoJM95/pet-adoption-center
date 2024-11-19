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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Utils.updateFields;
import static java.lang.String.format;

@Service
@CacheConfig(cacheNames = "breed")
public class BreedService implements BreedServiceI {

    private final BreedRepository breedRepository;
    private final SpeciesServiceI speciesServiceI;

    @Autowired
    public BreedService(BreedRepository breedRepository, SpeciesServiceI speciesServiceI) {
        this.breedRepository = breedRepository;
        this.speciesServiceI = speciesServiceI;
    }

    @Override
    @Cacheable
    public List<BreedGetDto> getAll(Pageable pageable) {
        return breedRepository.findAll(pageable).stream().map(BreedConverter::toDto).toList();
    }

    @Override
    @Cacheable(key = "#id")
    public BreedGetDto getById(String id) {
        return BreedConverter.toDto(findById(id));
    }

    @Override
    public List<BreedGetDto> getBySpecies(String speciesName) {
        Species species = SpeciesConverter.toModel(speciesServiceI.getByName(speciesName));
        return breedRepository.findBySpecies(species).stream()
                .map(BreedConverter::toDto).toList();
    }

    @Override
    public BreedGetDto create(BreedCreateDto dto) {
        Breed breed = BreedConverter.toModel(dto);
        Species species = SpeciesConverter.toModel(speciesServiceI.getById(dto.speciesId()));
        breed.setSpecies(species);
        return BreedConverter.toDto(breedRepository.save(breed));
    }

    @Override
    @CachePut(key = "#id")
    public BreedGetDto update(String id, BreedUpdateDto dto) {
        Breed breed = findById(id);
        updateFields(dto.name(), breed.getName(), breed::setName);
        return BreedConverter.toDto(breedRepository.save(breed));
    }

    @Override
    @CacheEvict(key = "#id")
    public String delete(String id) {
        findById(id);
        breedRepository.deleteById(id);
        return format(BREED_DELETE_MESSAGE, id);
    }

    @Override
    public void verifyIfBreedsAndSpeciesMatch(PetCreateDto dto) {

        Species species = SpeciesConverter.toModel(speciesServiceI.getById(dto.speciesId()));
        Breed primaryBreed = findById(dto.primaryBreedId());
        Breed secondaryBreed;

        if(!Objects.equals(primaryBreed.getSpecies(), species)) {
            throw new BreedMismatchException(format(BREED_SPECIES_MISMATCH, primaryBreed.getName(), species.getName()));
        }

        if(dto.secondaryBreedId() != null) {
            secondaryBreed = findById(dto.secondaryBreedId());
            if(!Objects.equals(secondaryBreed.getSpecies(), species)) {
                throw new BreedMismatchException(format(BREED_SPECIES_MISMATCH, secondaryBreed.getName(), species.getName()));
            }
        }
    }

    private Breed findById(String id) {
        return breedRepository.findById(id).orElseThrow(
                () -> new BreedNotFoundException(format(BREED_NOT_FOUND, id)));
    }
}