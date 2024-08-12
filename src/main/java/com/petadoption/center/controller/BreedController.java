package com.petadoption.center.controller;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet-breed")
public class BreedController {

    @Autowired
    private BreedService petBreedService;

    @GetMapping("/")
    public ResponseEntity<List<BreedGetDto>> getAllPetBreeds(){
        return new ResponseEntity<>(petBreedService.getAllPetBreeds(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BreedGetDto> getPetBreedById(@PathVariable("id") Long id){
        return new ResponseEntity<>(petBreedService.getPetBreedById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<BreedGetDto> addNewUser(@RequestBody BreedCreateDto breed){
        return new ResponseEntity<>(petBreedService.addNewPetBreed(breed), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BreedGetDto> updatePetBreed(@PathVariable ("id") Long id,
                                                      @RequestBody BreedUpdateDto breed){
        return new ResponseEntity<>(petBreedService.updatePetBreed(id, breed), HttpStatus.OK);
    }
}
