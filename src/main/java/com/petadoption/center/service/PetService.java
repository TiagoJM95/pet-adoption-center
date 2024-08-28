package com.petadoption.center.service;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetDuplicateImageException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;

import java.util.List;

public interface PetService {

    List<PetGetDto> getAllPets(String nameLikeFilter);

    PetGetDto getPetById(Long id) throws PetNotFoundException;

    PetGetDto addNewPet(PetCreateDto pet) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, PetDuplicateImageException;

    PetGetDto updatePet(Long id, PetUpdateDto pet) throws PetNotFoundException, PetDuplicateImageException, OrgNotFoundException;
}
