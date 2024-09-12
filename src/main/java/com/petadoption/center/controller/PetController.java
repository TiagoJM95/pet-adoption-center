package com.petadoption.center.controller;

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
import com.petadoption.center.service.PetService;
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
    private PetService petService;

    @GetMapping("/id/{id}")
    public ResponseEntity<PetGetDto> getPetById(@PathVariable("id") String id) throws PetNotFoundException {
        return new ResponseEntity<>(petService.getPetById(id), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<PetGetDto>> searchPets(@RequestBody PetSearchCriteria searchCriteria,
                                                      @RequestParam (defaultValue = "0", required = false) int page,
                                                      @RequestParam (defaultValue = "5", required = false) int size,
                                                      @RequestParam (defaultValue = "id", required = false) String sortBy)
            throws SpeciesNotFoundException, InvalidDescriptionException {
        return new ResponseEntity<>(petService.searchPets(searchCriteria, page, size, sortBy), HttpStatus.OK);
    }

    @PostMapping("/addSingle")
    public ResponseEntity<PetGetDto> addNewPet(@Valid @RequestBody PetCreateDto pet) throws OrgNotFoundException, BreedNotFoundException, ColorNotFoundException, SpeciesNotFoundException, PetDuplicateException, BreedMismatchException, InvalidDescriptionException {
        return new ResponseEntity<>(petService.addNewPet(pet), HttpStatus.CREATED);
    }

    @PostMapping("/addList")
    public ResponseEntity<String> addListOfNewPets(@Valid @RequestBody List<PetCreateDto> pets) throws OrgNotFoundException, BreedNotFoundException, ColorNotFoundException, SpeciesNotFoundException, PetDuplicateException, BreedMismatchException, InvalidDescriptionException {
        petService.addListOfNewPets(pets);
        return new ResponseEntity<>("Added", HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetGetDto> updatePet(@Valid @PathVariable ("id") String id, @RequestBody PetUpdateDto pet) throws OrgNotFoundException, PetDuplicateException, PetNotFoundException, InvalidDescriptionException {
        return new ResponseEntity<>(petService.updatePet(id, pet), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePet(@PathVariable ("id") String id) throws PetNotFoundException {
        return new ResponseEntity<>(petService.deletePet(id), HttpStatus.OK);
    }
}
