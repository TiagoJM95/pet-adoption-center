package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpeciesServiceI {
    List<SpeciesGetDto> getAll(Pageable pageable);
    SpeciesGetDto getById(String id);
    SpeciesGetDto getByName(String speciesName);
    SpeciesGetDto create(SpeciesCreateDto dto);
    SpeciesGetDto update(String id, SpeciesUpdateDto dto);
    String delete(String id);
}