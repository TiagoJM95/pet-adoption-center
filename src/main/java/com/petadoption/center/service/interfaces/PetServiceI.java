package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.dto.user.UserFavoritePetsDto;
import com.petadoption.center.exception.breed.BreedMismatchException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetDescriptionException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Pet;
import com.petadoption.center.specifications.PetSearchCriteria;

import java.util.List;
import java.util.Set;

public interface PetServiceI {
    PetGetDto getPetById(String id) throws PetNotFoundException;

    List<PetGetDto> searchPets(PetSearchCriteria searchCriteria, int page, int size, String sortBy) throws SpeciesNotFoundException, PetDescriptionException;

    PetGetDto addNewPet(PetCreateDto pet) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, BreedMismatchException, PetDescriptionException;

    void addListOfNewPets(List<PetCreateDto> pets) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, BreedMismatchException, PetDescriptionException;

    PetGetDto updatePet(String id, PetUpdateDto pet) throws PetNotFoundException, OrgNotFoundException, PetDescriptionException;

    String deletePet(String id) throws PetNotFoundException;

    Set<Pet> addPetToFavorites(String petId, UserFavoritePetsDto dto) throws PetNotFoundException;

    Set<PetGetDto> convertFavoritesToDto(UserFavoritePetsDto dto);

    Set<Pet> removePetFromFavorites(String petId, UserFavoritePetsDto dto) throws PetNotFoundException;
}