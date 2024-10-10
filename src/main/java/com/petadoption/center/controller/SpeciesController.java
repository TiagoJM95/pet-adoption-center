package com.petadoption.center.controller;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.service.interfaces.SpeciesServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/species")
public class SpeciesController {

    private final SpeciesServiceI speciesServiceI;

    @Autowired
    public SpeciesController(SpeciesServiceI speciesServiceI) {
        this.speciesServiceI = speciesServiceI;
    }

    @GetMapping("/")
    public ResponseEntity<List<SpeciesGetDto>> getAll(@PageableDefault(sort = "createdAt") Pageable pageable){
        return new ResponseEntity<>(speciesServiceI.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SpeciesGetDto> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(speciesServiceI.getById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<SpeciesGetDto> create(@Valid @RequestBody SpeciesCreateDto dto) {
        return new ResponseEntity<>(speciesServiceI.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SpeciesGetDto> update(@PathVariable ("id") String id, @Valid @RequestBody SpeciesUpdateDto dto) {
        return new ResponseEntity<>(speciesServiceI.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable ("id") String id) {
        return new ResponseEntity<>(speciesServiceI.delete(id), HttpStatus.OK);
    }
}
