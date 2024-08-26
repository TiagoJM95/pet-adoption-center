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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.petadoption.center.converter.SpeciesConverter.fromModelToSpeciesGetDto;
import static com.petadoption.center.converter.SpeciesConverter.fromSpeciesCreateDtoToModel;

@Service
public class SpeciesServiceImpl implements SpeciesService {


    private final SpeciesRepository speciesRepository;

    @Autowired
    public SpeciesServiceImpl(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public List<SpeciesGetDto> getAllPetSpecies() {
        return speciesRepository.findAll().stream().map(SpeciesConverter::fromModelToSpeciesGetDto).toList();
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
        speciesToUpdate.setName(species.name());
        return fromModelToSpeciesGetDto(speciesRepository.save(speciesToUpdate));
    }

    private Species findSpeciesById(Long id) throws SpeciesNotFoundException {
        return speciesRepository.findById(id).orElseThrow(() -> new SpeciesNotFoundException(id));
    }

    private void checkIfSpeciesExistsByName(String name) throws SpeciesNameDuplicateException {
        Optional<Species> species = speciesRepository.findByName(name);
        if (species.isPresent()) {
            throw new SpeciesNameDuplicateException(name);
        }
    }
}
