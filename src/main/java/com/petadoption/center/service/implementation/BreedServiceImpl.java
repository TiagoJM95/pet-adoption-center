package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.BreedConverter;
import com.petadoption.center.converter.SpeciesConverter;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.exception.breed.BreedDuplicateException;
import com.petadoption.center.exception.breed.BreedMismatchException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
import com.petadoption.center.repository.BreedRepository;
import com.petadoption.center.service.BreedService;
import com.petadoption.center.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.petadoption.center.util.Utils.updateFields;
import static com.petadoption.center.util.Messages.*;

@Service
public class BreedServiceImpl implements BreedService {

    private final BreedRepository breedRepository;
    private final SpeciesService speciesService;

    @Autowired
    public BreedServiceImpl(BreedRepository breedRepository, SpeciesService speciesService) {
        this.breedRepository = breedRepository;
        this.speciesService = speciesService;
    }

    @Override
    public List<BreedGetDto> getAllBreeds(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        return breedRepository.findAll(pageRequest).stream().map(BreedConverter::toDto).toList();
    }

    @Override
    public BreedGetDto getBreedById(Long id) throws BreedNotFoundException {
        return BreedConverter.toDto(findBreedById(id));
    }

    @Override
    public List<BreedGetDto> getBreedsBySpecies(String species) throws SpeciesNotFoundException {
        Species filterSpecies = SpeciesConverter.toModel(speciesService.getSpeciesByName(species));
        return breedRepository.findBySpecies(filterSpecies).stream()
                .map(BreedConverter::toDto).toList();
    }

    @Override
    public BreedGetDto addNewBreed(BreedCreateDto dto) throws BreedDuplicateException, SpeciesNotFoundException {
        checkIfBreedsExistsByName(dto.name());
        Species species = SpeciesConverter.toModel(speciesService.getSpeciesById(dto.speciesId()));
        return BreedConverter.toDto(breedRepository.save(BreedConverter.toModel(dto, species)));
    }

    @Override
    public BreedGetDto updateBreed(Long id, BreedUpdateDto dto) throws BreedNotFoundException, BreedDuplicateException {
        Breed breed = findBreedById(id);
        checkIfBreedsExistsByName(dto.name());
        updateFields(dto.name(), breed.getName(), breed::setName);
        return BreedConverter.toDto(breedRepository.save(breed));
    }

    @Override
    public String deleteBreed(Long id) throws BreedNotFoundException {
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

    private Breed findBreedById(Long id) throws BreedNotFoundException {
        return breedRepository.findById(id).orElseThrow(
                () -> new BreedNotFoundException(BREED_WITH_ID + id + NOT_FOUND));
    }

    private void checkIfBreedsExistsByName(String name) throws BreedDuplicateException {
        if (breedRepository.findByName(name).isPresent()) {
            throw new BreedDuplicateException(BREED_WITH_NAME + name + ALREADY_EXISTS);
        }
    }
}
