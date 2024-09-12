package com.petadoption.center.service;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.dto.petSearchCriteria.PetSearchCriteria;
import com.petadoption.center.exception.breed.BreedMismatchException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.InvalidDescriptionException;
import com.petadoption.center.exception.pet.PetDuplicateException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;

import java.util.List;

public interface PetService {

    PetGetDto getPetById(String id) throws PetNotFoundException;
    List<PetGetDto> searchPets(PetSearchCriteria searchCriteria, int page, int size, String sortBy) throws SpeciesNotFoundException, InvalidDescriptionException;
    PetGetDto addNewPet(PetCreateDto pet) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, PetDuplicateException, BreedMismatchException, InvalidDescriptionException;
    void addListOfNewPets(List<PetCreateDto> pets) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, PetDuplicateException, BreedMismatchException, InvalidDescriptionException;
    PetGetDto updatePet(String id, PetUpdateDto pet) throws PetNotFoundException, PetDuplicateException, OrgNotFoundException, InvalidDescriptionException;
    String deletePet(String id) throws PetNotFoundException;
}