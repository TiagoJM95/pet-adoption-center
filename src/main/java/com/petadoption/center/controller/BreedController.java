package com.petadoption.center.controller;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.exception.breed.BreedDuplicateException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.service.BreedService;
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
    private BreedService breedService;

    @GetMapping("/")
    public ResponseEntity<List<BreedGetDto>> getAllBreeds(@RequestParam (defaultValue = "0", required = false) int page,
                                                          @RequestParam (defaultValue = "5", required = false) int size,
                                                          @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(breedService.getAllBreeds(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BreedGetDto> getBreedById(@PathVariable("id") Long id) throws BreedNotFoundException {
        return new ResponseEntity<>(breedService.getBreedById(id), HttpStatus.OK);
    }

    @GetMapping("/species/{species}")
    public ResponseEntity<List<BreedGetDto>> getBreedsBySpecies(@PathVariable("species") String species)
            throws SpeciesNotFoundException {
        return new ResponseEntity<>(breedService.getBreedsBySpecies(species), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<BreedGetDto> addNewBreed(@Valid @RequestBody BreedCreateDto dto)
            throws BreedDuplicateException, SpeciesNotFoundException {
        return new ResponseEntity<>(breedService.addNewBreed(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BreedGetDto> updateBreed(@PathVariable ("id") Long id, @Valid @RequestBody BreedUpdateDto dto)
            throws BreedDuplicateException, BreedNotFoundException {
        return new ResponseEntity<>(breedService.updateBreed(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBreed(@PathVariable ("id") Long id) throws BreedNotFoundException {
        return new ResponseEntity<>(breedService.deleteBreed(id), HttpStatus.OK);
    }
}
