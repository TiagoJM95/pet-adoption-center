package com.petadoption.center.controller;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.exception.species.SpeciesNameDuplicateException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet-species")
public class SpeciesController {

    @Autowired
    private SpeciesService petSpeciesService;


    @GetMapping("/")
    public ResponseEntity<List<SpeciesGetDto>> getAllPetSpecies(){
        return new ResponseEntity<>(petSpeciesService.getAllPetSpecies(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SpeciesGetDto> getPetSpeciesById(@PathVariable("id") Long id) throws SpeciesNotFoundException {
        return new ResponseEntity<>(petSpeciesService.getPetSpeciesById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<SpeciesGetDto> addNewPetSpecies(@RequestBody SpeciesCreateDto species) throws SpeciesNameDuplicateException {
        return new ResponseEntity<>(petSpeciesService.addNewPetSpecies(species), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SpeciesGetDto> updatePetSpecies(@PathVariable ("id") Long id,
                                                          @RequestBody SpeciesUpdateDto species) throws SpeciesNameDuplicateException, SpeciesNotFoundException {
        return new ResponseEntity<>(petSpeciesService.updatePetSpecies(id, species), HttpStatus.OK);
    }
}
