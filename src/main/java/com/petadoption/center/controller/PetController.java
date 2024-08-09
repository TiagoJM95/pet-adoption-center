package com.petadoption.center.controller;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetPatchDto;
import com.petadoption.center.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;


    @GetMapping("/")
    public ResponseEntity<List<PetGetDto>> getAllPets(){
        return new ResponseEntity<>(petService.getAllPets(), HttpStatus.OK);
    }

    @GetMapping("/id/{petId}")
    public ResponseEntity<PetGetDto> getPetById(@PathVariable("petId") Long petId){
        return new ResponseEntity<>(petService.getPetById(petId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PetGetDto> addNewPet(@RequestBody PetCreateDto petCreateDto){
        return new ResponseEntity<>(petService.addNewPet(petCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{petId}")
    public ResponseEntity<PetGetDto> updatePet(@PathVariable ("petId") Long petId, @RequestBody PetPatchDto petPatchDto){
        return new ResponseEntity<>(petService.updatePet(petId, petPatchDto), HttpStatus.OK);
    }
}
