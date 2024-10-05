package com.petadoption.center.controller;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.service.interfaces.BreedServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/breed")
public class BreedController {

    @Autowired
    private BreedServiceI breedServiceI;

    @GetMapping("/")
    public ResponseEntity<List<BreedGetDto>> getAll(@RequestParam (defaultValue = "0", required = false) int page,
                                                    @RequestParam (defaultValue = "5", required = false) int size,
                                                    @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(breedServiceI.getAllBreeds(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BreedGetDto> getById(@PathVariable("id") String id)
            throws BreedNotFoundException {
        return new ResponseEntity<>(breedServiceI.getBreedById(id), HttpStatus.OK);
    }

    @GetMapping("/species/{species}")
    public ResponseEntity<List<BreedGetDto>> getBySpecies(@PathVariable("species") String speciesName)
            throws SpeciesNotFoundException {
        return new ResponseEntity<>(breedServiceI.getBreedsBySpecies(speciesName), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<BreedGetDto> create(@Valid @RequestBody BreedCreateDto dto)
            throws SpeciesNotFoundException {
        return new ResponseEntity<>(breedServiceI.addNewBreed(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BreedGetDto> update(@PathVariable ("id") String id, @Valid @RequestBody BreedUpdateDto dto)
            throws BreedNotFoundException {
        return new ResponseEntity<>(breedServiceI.updateBreed(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable ("id") String id)
            throws BreedNotFoundException {
        return new ResponseEntity<>(breedServiceI.deleteBreed(id), HttpStatus.OK);
    }
}