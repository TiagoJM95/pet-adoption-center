package com.petadoption.center.controller;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.service.interfaces.SpeciesServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet-species")
public class SpeciesController {

    @Autowired
    private SpeciesServiceI speciesServiceI;


    @GetMapping("/")
    public ResponseEntity<List<SpeciesGetDto>> getAllPetSpecies(@RequestParam (defaultValue = "0", required = false) int page,
                                                                @RequestParam (defaultValue = "5", required = false) int size,
                                                                @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(speciesServiceI.getAllSpecies(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SpeciesGetDto> getPetSpeciesById(@PathVariable("id") String id) throws SpeciesNotFoundException {
        return new ResponseEntity<>(speciesServiceI.getSpeciesById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<SpeciesGetDto> addNewPetSpecies(@Valid @RequestBody SpeciesCreateDto dto) {
        return new ResponseEntity<>(speciesServiceI.addNewSpecies(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SpeciesGetDto> updatePetSpecies(@PathVariable ("id") String id, @Valid @RequestBody SpeciesUpdateDto dto)
            throws SpeciesNotFoundException {
        return new ResponseEntity<>(speciesServiceI.updateSpecies(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSpecies(@PathVariable ("id") String id) throws SpeciesNotFoundException {
        return new ResponseEntity<>(speciesServiceI.deleteSpecies(id), HttpStatus.OK);
    }
}
