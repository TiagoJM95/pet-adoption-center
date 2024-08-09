package com.petadoption.center.controller;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
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
    public ResponseEntity<List<PetGetDto>> getAllPets(){
        return new ResponseEntity<>(petService.getAllPets(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PetGetDto> getPetById(@PathVariable("id") Long id){
        return new ResponseEntity<>(petService.getPetById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PetGetDto> addNewPet(@RequestBody PetCreateDto pet){
        return new ResponseEntity<>(petService.addNewPet(pet), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetGetDto> updatePet(@PathVariable ("id") Long id, @RequestBody PetUpdateDto pet){
        return new ResponseEntity<>(petService.updatePet(id, pet), HttpStatus.OK);
    }
}
