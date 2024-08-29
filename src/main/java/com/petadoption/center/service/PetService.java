package com.petadoption.center.service;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetDuplicateImageException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Color;

import java.util.List;

public interface PetService {

    List<PetGetDto> getAllPets(String nameLikeFilter, Breed breed, Breed primaryBreed, Breed secondaryBreed, Color color, Color primaryColor, Color secondaryColor, Color tertiaryColor, String gender, String coat, String size, String age, Boolean isAdopted, Boolean isSterilized);

    PetGetDto getPetById(Long id) throws PetNotFoundException;

    PetGetDto addNewPet(PetCreateDto pet) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, PetDuplicateImageException;

    PetGetDto updatePet(Long id, PetUpdateDto pet) throws PetNotFoundException, PetDuplicateImageException, OrgNotFoundException;
}
