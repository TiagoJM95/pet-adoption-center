package com.petadoption.center.controller;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.exception.breed.BreedMismatchException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrganizationNotFoundException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.service.interfaces.PetServiceI;
import com.petadoption.center.specifications.PetSearchCriteria;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet")
public class PetController {

    @Autowired
    private PetServiceI petServiceI;

    @GetMapping("/id/{id}")
    public ResponseEntity<PetGetDto> getPetById(@PathVariable("id") String id) throws PetNotFoundException {
        return new ResponseEntity<>(petServiceI.getPetById(id), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<PetGetDto>> searchPets(@Valid @RequestBody PetSearchCriteria searchCriteria,
                                                      @RequestParam (defaultValue = "0", required = false) int page,
                                                      @RequestParam (defaultValue = "5", required = false) int size,
                                                      @RequestParam (defaultValue = "id", required = false) String sortBy) {
        return new ResponseEntity<>(petServiceI.searchPets(searchCriteria, page, size, sortBy), HttpStatus.OK);
    }

    @PostMapping("/addSingle")
    public ResponseEntity<PetGetDto> addNewPet(@Valid @RequestBody PetCreateDto pet) throws OrganizationNotFoundException, BreedNotFoundException, ColorNotFoundException, SpeciesNotFoundException, BreedMismatchException {
        return new ResponseEntity<>(petServiceI.addNewPet(pet), HttpStatus.CREATED);
    }

    @PostMapping("/addList")
    public ResponseEntity<String> addListOfNewPets(@Valid @RequestBody List<PetCreateDto> pets) throws OrganizationNotFoundException, BreedNotFoundException, ColorNotFoundException, SpeciesNotFoundException, BreedMismatchException {
        petServiceI.addListOfNewPets(pets);
        return new ResponseEntity<>("Added", HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetGetDto> updatePet(@Valid @PathVariable ("id") String id, @RequestBody PetUpdateDto pet) throws OrganizationNotFoundException, PetNotFoundException {
        return new ResponseEntity<>(petServiceI.updatePet(id, pet), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePet(@PathVariable ("id") String id) throws PetNotFoundException {
        return new ResponseEntity<>(petServiceI.deletePet(id), HttpStatus.OK);
    }
}
