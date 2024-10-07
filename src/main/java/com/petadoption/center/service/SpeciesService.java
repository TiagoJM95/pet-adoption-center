package com.petadoption.center.service;

import com.petadoption.center.converter.SpeciesConverter;
import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.exception.not_found.SpeciesNotFoundException;
import com.petadoption.center.model.Species;
import com.petadoption.center.repository.SpeciesRepository;
import com.petadoption.center.service.interfaces.SpeciesServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Utils.updateFields;

@Service
public class SpeciesService implements SpeciesServiceI {

    private final SpeciesRepository speciesRepository;

    @Autowired
    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public List<SpeciesGetDto> getAllSpecies(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        return speciesRepository.findAll(pageRequest).stream().map(SpeciesConverter::toDto).toList();
    }

    @Override
    public SpeciesGetDto getSpeciesById(String id) throws SpeciesNotFoundException {
        return SpeciesConverter.toDto(findSpeciesById(id));
    }

    @Override
    public SpeciesGetDto getSpeciesByName(String name) throws SpeciesNotFoundException {
        return SpeciesConverter.toDto(findSpeciesByName(name));
    }

    @Override
    public SpeciesGetDto addNewSpecies(SpeciesCreateDto dto) {
        return SpeciesConverter.toDto(speciesRepository.save(SpeciesConverter.toModel(dto)));
    }

    @Override
    public SpeciesGetDto updateSpecies(String id, SpeciesUpdateDto dto) throws SpeciesNotFoundException {
        Species species = findSpeciesById(id);
        updateFields(dto.name(), species.getName(), species::setName);
        return SpeciesConverter.toDto(speciesRepository.save(species));
    }

    @Override
    public String deleteSpecies(String id) throws SpeciesNotFoundException {
        findSpeciesById(id);
        speciesRepository.deleteById(id);
        return SPECIES_WITH_ID + id + DELETE_SUCCESS;
    }

    private Species findSpeciesById(String id) throws SpeciesNotFoundException {
        return speciesRepository.findById(id).orElseThrow(
                () -> new SpeciesNotFoundException(SPECIES_WITH_ID + id + NOT_FOUND));
    }

    private Species findSpeciesByName(String name) throws SpeciesNotFoundException {
        return speciesRepository.findByName(name).orElseThrow(
                () -> new SpeciesNotFoundException(SPECIES_WITH_NAME + name + NOT_FOUND));
    }
}
