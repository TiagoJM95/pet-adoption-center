package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.exception.BreedMismatchException;
import com.petadoption.center.exception.not_found.BreedNotFoundException;
import com.petadoption.center.exception.not_found.ColorNotFoundException;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;
import com.petadoption.center.exception.not_found.PetNotFoundException;
import com.petadoption.center.exception.not_found.SpeciesNotFoundException;
import com.petadoption.center.specifications.PetSearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PetServiceI {
    PetGetDto getById(String id);
    List<PetGetDto> searchPets(PetSearchCriteria searchCriteria, Pageable pageable);
    PetGetDto create(PetCreateDto dto);
    String createFromList(List<PetCreateDto> dtoList);
    PetGetDto update(String id, PetUpdateDto dto);
    String delete(String id);
}