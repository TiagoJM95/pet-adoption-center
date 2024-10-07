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

import java.util.List;

public interface PetServiceI {
    PetGetDto getPetById(String id) throws PetNotFoundException;

    List<PetGetDto> searchPets(PetSearchCriteria searchCriteria, int page, int size, String sortBy);

    PetGetDto addNewPet(PetCreateDto pet) throws OrganizationNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, BreedMismatchException;

    void addListOfNewPets(List<PetCreateDto> pets) throws OrganizationNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, BreedMismatchException;

    PetGetDto updatePet(String id, PetUpdateDto pet) throws PetNotFoundException, OrganizationNotFoundException;

    String deletePet(String id) throws PetNotFoundException;
}