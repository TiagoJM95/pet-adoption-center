package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.BreedConverter;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.exception.breed.BreedNameDuplicateException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
import com.petadoption.center.repository.BreedRepository;
import com.petadoption.center.repository.SpeciesRepository;
import com.petadoption.center.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.petadoption.center.converter.BreedConverter.fromBreedCreateDtoToModel;
import static com.petadoption.center.converter.BreedConverter.fromModelToBreedGetDto;
import static com.petadoption.center.util.FieldUpdater.updateIfChanged;

@Service
public class BreedServiceImpl implements BreedService {

    private final BreedRepository breedRepository;
    private final SpeciesRepository speciesRepository;

    @Autowired
    public BreedServiceImpl(BreedRepository breedRepository, SpeciesRepository speciesRepository) {
        this.breedRepository = breedRepository;
        this.speciesRepository = speciesRepository;
    }

    @Override
    public List<BreedGetDto> getAllBreeds(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        return breedRepository.findAll(pageRequest).stream().map(BreedConverter::fromModelToBreedGetDto).toList();
    }

    @Override
    public BreedGetDto getBreedById(Long id) throws BreedNotFoundException {
        return fromModelToBreedGetDto(findBreedById(id));
    }

    @Override
    public List<BreedGetDto> getBreedsBySpecies(String species) throws SpeciesNotFoundException {
        return breedRepository.findBySpecies(
                speciesRepository.findByName(species).orElseThrow(() -> new SpeciesNotFoundException(Long.valueOf(species))))
                .stream().map(BreedConverter::fromModelToBreedGetDto).toList();
    }

    @Override
    public BreedGetDto addNewBreed(BreedCreateDto breed) throws BreedNameDuplicateException, SpeciesNotFoundException {
        checkIfBreedsExistsByName(breed.name());
        Species species = speciesRepository.findById(breed.specieId()).orElseThrow(() -> new SpeciesNotFoundException(breed.specieId()));
        return fromModelToBreedGetDto(breedRepository.save(fromBreedCreateDtoToModel(breed, species)));
    }

    @Override
    public BreedGetDto updateBreed(Long id, BreedUpdateDto breed) throws BreedNotFoundException, BreedNameDuplicateException {
        Breed breedToUpdate = findBreedById(id);
        checkIfBreedsExistsByName(breed.name());
        updateIfChanged(breed::name, breedToUpdate::getName, breedToUpdate::setName);
        return fromModelToBreedGetDto(breedRepository.save(breedToUpdate));
    }

    Breed findBreedById(Long id) throws BreedNotFoundException {
        return breedRepository.findById(id).orElseThrow(() -> new BreedNotFoundException(id));
    }

    private void checkIfBreedsExistsByName(String name) throws BreedNameDuplicateException {
        if (breedRepository.findByName(name).isPresent()) {
            throw new BreedNameDuplicateException(name);
        }
    }
}
