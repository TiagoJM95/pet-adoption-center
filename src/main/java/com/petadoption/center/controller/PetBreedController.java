package com.petadoption.center.controller;

import com.petadoption.center.dto.petBreed.PetBreedCreateDto;
import com.petadoption.center.dto.petBreed.PetBreedGetDto;
import com.petadoption.center.dto.petBreed.PetBreedPatchDto;
import com.petadoption.center.service.PetBreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet-breed")
public class PetBreedController {

    @Autowired
    private PetBreedService petBreedService;

    @GetMapping("/")
    public ResponseEntity<List<PetBreedGetDto>> getAllPetBreeds(){
        return new ResponseEntity<>(petBreedService.getAllPetBreeds(), HttpStatus.OK);
    }

    @GetMapping("/id/{petBreedId}")
    public ResponseEntity<PetBreedGetDto> getPetBreedById(@PathVariable("petBreedId") Long petBreedId){
        return new ResponseEntity<>(petBreedService.getPetBreedById(petBreedId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PetBreedGetDto> addNewUser(@RequestBody PetBreedCreateDto petBreedCreateDto){
        return new ResponseEntity<>(petBreedService.addNewPetBreed(petBreedCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{petBreedId}")
    public ResponseEntity<PetBreedGetDto> updatePetBreed(@PathVariable ("petBreedId") Long petBreedId, @RequestBody PetBreedPatchDto petBreedPatchDto){
        return new ResponseEntity<>(petBreedService.updatePetBreed(petBreedId, petBreedPatchDto), HttpStatus.OK);
    }
}
