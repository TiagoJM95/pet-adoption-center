package com.petadoption.center.controller;

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
import com.petadoption.center.model.Pet;
import com.petadoption.center.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/")
    public ResponseEntity<List<PetGetDto>> getAllPets(@RequestParam(required = false) String nameLike,
                                                      @RequestParam(required = false) Breed breed,
                                                      @RequestParam(required = false) Breed primaryBreed,
                                                      @RequestParam(required = false) Breed secondaryBreed,
                                                      @RequestParam(required = false) Color color,
                                                      @RequestParam(required = false) Color primaryColor,
                                                      @RequestParam(required = false) Color secondaryColor,
                                                      @RequestParam(required = false) Color tertiaryColor,
                                                      @RequestParam(required = false) String gender,
                                                      @RequestParam(required = false) String coat,
                                                      @RequestParam(required = false) String size,
                                                      @RequestParam(required = false) String age,
                                                      @RequestParam(required = false) Boolean isAdopted,
                                                      @RequestParam(required = false) Boolean isSterilized) {
        return new ResponseEntity<>(petService.getAllPets(nameLike, breed, primaryBreed, secondaryBreed, color, primaryColor, secondaryColor, tertiaryColor, gender, coat, size, age, isAdopted, isSterilized), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PetGetDto> getPetById(@PathVariable("id") Long id) throws PetNotFoundException {
        return new ResponseEntity<>(petService.getPetById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PetGetDto> addNewPet(@RequestBody PetCreateDto pet) throws OrgNotFoundException, BreedNotFoundException, ColorNotFoundException, SpeciesNotFoundException, PetDuplicateImageException {
        return new ResponseEntity<>(petService.addNewPet(pet), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetGetDto> updatePet(@PathVariable ("id") Long id, @RequestBody PetUpdateDto pet) throws OrgNotFoundException, PetDuplicateImageException, PetNotFoundException {
        return new ResponseEntity<>(petService.updatePet(id, pet), HttpStatus.OK);
    }
}
