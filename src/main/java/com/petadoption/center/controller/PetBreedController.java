package com.petadoption.center.controller;

import com.petadoption.center.dto.petBreed.PetBreedCreateDto;
import com.petadoption.center.dto.petBreed.PetBreedGetDto;
import com.petadoption.center.dto.petBreed.PetBreedUpdateDto;
import com.petadoption.center.service.PetBreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet-breed")
public class PetBreedController {

    @Autowired
    private PetBreedService petBreedService;

    @GetMapping("/")
    public ResponseEntity<List<PetBreedGetDto>> getAllPetBreeds(){
        return new ResponseEntity<>(petBreedService.getAllPetBreeds(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PetBreedGetDto> getPetBreedById(@PathVariable("id") Long id){
        return new ResponseEntity<>(petBreedService.getPetBreedById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PetBreedGetDto> addNewUser(@RequestBody PetBreedCreateDto petBreed){
        return new ResponseEntity<>(petBreedService.addNewPetBreed(petBreed), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetBreedGetDto> updatePetBreed(@PathVariable ("id") Long id,
                                                         @RequestBody PetBreedUpdateDto petBreed){
        return new ResponseEntity<>(petBreedService.updatePetBreed(id, petBreed), HttpStatus.OK);
    }
}
