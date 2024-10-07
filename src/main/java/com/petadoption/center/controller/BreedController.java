package com.petadoption.center.controller;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.service.interfaces.BreedServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/breed")
public class BreedController {

    private final BreedServiceI breedServiceI;

    @Autowired
    public BreedController(BreedServiceI breedServiceI) {
        this.breedServiceI = breedServiceI;
    }

    @GetMapping("/")
    public ResponseEntity<List<BreedGetDto>> getAll(@PageableDefault(sort = "createdAt") Pageable pageable) {
        return new ResponseEntity<>(breedServiceI.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BreedGetDto> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(breedServiceI.getById(id), HttpStatus.OK);
    }

    @GetMapping("/species/{speciesName}")
    public ResponseEntity<List<BreedGetDto>> getBySpecies(@PathVariable("speciesName") String speciesName) {
        return new ResponseEntity<>(breedServiceI.getBySpecies(speciesName), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<BreedGetDto> create(@Valid @RequestBody BreedCreateDto dto) {
        return new ResponseEntity<>(breedServiceI.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BreedGetDto> update(@PathVariable ("id") String id, @Valid @RequestBody BreedUpdateDto dto) {
        return new ResponseEntity<>(breedServiceI.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable ("id") String id) {
        return new ResponseEntity<>(breedServiceI.delete(id), HttpStatus.OK);
    }
}