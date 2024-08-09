package com.petadoption.center.controller;

import com.petadoption.center.dto.petSpecies.PetSpeciesCreateDto;
import com.petadoption.center.dto.petSpecies.PetSpeciesGetDto;
import com.petadoption.center.dto.petSpecies.PetSpeciesUpdateDto;
import com.petadoption.center.service.PetSpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet-species")
public class PetSpeciesController {

    @Autowired
    private PetSpeciesService petSpeciesService;


    @GetMapping("/")
    public ResponseEntity<List<PetSpeciesGetDto>> getAllPetSpecies(){
        return new ResponseEntity<>(petSpeciesService.getAllPetSpecies(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PetSpeciesGetDto> getPetSpeciesById(@PathVariable("id") Long id){
        return new ResponseEntity<>(petSpeciesService.getPetSpeciesById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PetSpeciesGetDto> addNewPetSpecies(@RequestBody PetSpeciesCreateDto petSpecies){
        return new ResponseEntity<>(petSpeciesService.addNewPetSpecies(petSpecies), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetSpeciesGetDto> updatePetSpecies(@PathVariable ("id") Long id, @RequestBody PetSpeciesUpdateDto petSpecies){
        return new ResponseEntity<>(petSpeciesService.updatePetSpecies(id, petSpecies), HttpStatus.OK);
    }
}
