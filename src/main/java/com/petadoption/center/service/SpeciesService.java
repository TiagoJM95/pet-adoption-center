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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Utils.updateFields;
import static java.lang.String.format;

@Service
public class SpeciesService implements SpeciesServiceI {

    private final SpeciesRepository speciesRepository;

    @Autowired
    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public List<SpeciesGetDto> getAll(Pageable pageable) {
        return speciesRepository.findAll(pageable).stream().map(SpeciesConverter::toDto).toList();
    }

    @Override
    public SpeciesGetDto getById(String id) {
        return SpeciesConverter.toDto(findById(id));
    }

    @Override
    public SpeciesGetDto getByName(String speciesName) {
        return SpeciesConverter.toDto(findByName(speciesName));
    }

    @Override
    public SpeciesGetDto create(SpeciesCreateDto dto) {
        return SpeciesConverter.toDto(speciesRepository.save(SpeciesConverter.toModel(dto)));
    }

    @Override
    public SpeciesGetDto update(String id, SpeciesUpdateDto dto) {
        Species species = findById(id);
        updateFields(dto.name(), species.getName(), species::setName);
        return SpeciesConverter.toDto(speciesRepository.save(species));
    }

    @Override
    public String delete(String id) {
        findById(id);
        speciesRepository.deleteById(id);
        return format(SPECIES_DELETE_MESSAGE, id);
    }

    private Species findById(String id) {
        return speciesRepository.findById(id).orElseThrow(
                () -> new SpeciesNotFoundException(format(SPECIES_NOT_FOUND, id)));
    }

    private Species findByName(String name) {
        return speciesRepository.findByName(name).orElseThrow(
                () -> new SpeciesNotFoundException(format(SPECIES_NOT_FOUND_NAME, name)));
    }
}