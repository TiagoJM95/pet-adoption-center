package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.exception.breed.BreedMismatchException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrganizationNotFoundException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
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