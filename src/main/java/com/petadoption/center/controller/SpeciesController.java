package com.petadoption.center.controller;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.exception.species.SpeciesNameDuplicateException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.service.SpeciesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet-species")
public class SpeciesController {

    @Autowired
    private SpeciesService speciesService;


    @GetMapping("/")
    public ResponseEntity<List<SpeciesGetDto>> getAllPetSpecies(@RequestParam (defaultValue = "0", required = false) int page,
                                                                @RequestParam (defaultValue = "5", required = false) int size,
                                                                @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(speciesService.getAllPetSpecies(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SpeciesGetDto> getPetSpeciesById(@PathVariable("id") Long id) throws SpeciesNotFoundException {
        return new ResponseEntity<>(speciesService.getPetSpeciesById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<SpeciesGetDto> addNewPetSpecies(@Valid @RequestBody SpeciesCreateDto species) throws SpeciesNameDuplicateException {
        return new ResponseEntity<>(speciesService.addNewPetSpecies(species), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SpeciesGetDto> updatePetSpecies(@PathVariable ("id") Long id,
                                                          @Valid @RequestBody SpeciesUpdateDto species) throws SpeciesNameDuplicateException, SpeciesNotFoundException {
        return new ResponseEntity<>(speciesService.updatePetSpecies(id, species), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSpecies(@PathVariable ("id") Long id) throws SpeciesNotFoundException {
        return new ResponseEntity<>(speciesService.deleteSpecies(id), HttpStatus.OK);
    }
}
