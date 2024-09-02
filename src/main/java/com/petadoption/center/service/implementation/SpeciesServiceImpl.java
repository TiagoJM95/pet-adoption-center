package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.SpeciesConverter;
import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.exception.species.SpeciesNameDuplicateException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Species;
import com.petadoption.center.repository.SpeciesRepository;
import com.petadoption.center.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.petadoption.center.converter.SpeciesConverter.fromModelToSpeciesGetDto;
import static com.petadoption.center.converter.SpeciesConverter.fromSpeciesCreateDtoToModel;
import static com.petadoption.center.util.FieldUpdater.updateIfChanged;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.SPECIES_WITH_ID;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    private final SpeciesRepository speciesRepository;

    @Autowired
    public SpeciesServiceImpl(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public Species findSpeciesById(Long id) throws SpeciesNotFoundException {
        return speciesRepository.findById(id).orElseThrow(() -> new SpeciesNotFoundException(id));
    }

    @Override
    public Species findSpeciesByName(String name) throws SpeciesNotFoundException {
        return speciesRepository.findByName(name).orElseThrow(() -> new SpeciesNotFoundException(Long.valueOf(name)));
    }

    @Override
    public List<SpeciesGetDto> getAllPetSpecies(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        return speciesRepository.findAll(pageRequest).stream().map(SpeciesConverter::fromModelToSpeciesGetDto).toList();
    }

    @Override
    public SpeciesGetDto getPetSpeciesById(Long id) throws SpeciesNotFoundException {
        return fromModelToSpeciesGetDto(findSpeciesById(id));
    }

    @Override
    public SpeciesGetDto addNewPetSpecies(SpeciesCreateDto species) throws SpeciesNameDuplicateException {
        checkIfSpeciesExistsByName(species.name());
        return fromModelToSpeciesGetDto(speciesRepository.save(fromSpeciesCreateDtoToModel(species)));
    }

    @Override
    public SpeciesGetDto updatePetSpecies(Long id, SpeciesUpdateDto species) throws SpeciesNotFoundException, SpeciesNameDuplicateException {
        Species speciesToUpdate = findSpeciesById(id);
        checkIfSpeciesExistsByName(species.name());
        updateIfChanged(species::name, speciesToUpdate::getName, speciesToUpdate::setName);
        return fromModelToSpeciesGetDto(speciesRepository.save(speciesToUpdate));
    }

    @Override
    public String deleteSpecies(Long id) throws SpeciesNotFoundException {
        findSpeciesById(id);
        speciesRepository.deleteById(id);
        return SPECIES_WITH_ID + id + DELETE_SUCCESS;
    }

    private void checkIfSpeciesExistsByName(String name) throws SpeciesNameDuplicateException {
        Optional<Species> species = speciesRepository.findByName(name);
        if (species.isPresent()) {
            throw new SpeciesNameDuplicateException(name);
        }
    }
}
