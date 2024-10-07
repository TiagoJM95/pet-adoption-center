package com.petadoption.center.controller;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.service.interfaces.PetServiceI;
import com.petadoption.center.specifications.PetSearchCriteria;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet")
public class PetController {

    private final PetServiceI petServiceI;

    @Autowired
    public PetController(PetServiceI petServiceI) {
        this.petServiceI = petServiceI;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PetGetDto> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(petServiceI.getById(id), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<PetGetDto>> searchPets(@Valid @RequestBody PetSearchCriteria searchCriteria,
                                                      @PageableDefault(sort = "createdAt") Pageable pageable) {
        return new ResponseEntity<>(petServiceI.searchPets(searchCriteria, pageable), HttpStatus.OK);
    }

    @PostMapping("/addSingle")
    public ResponseEntity<PetGetDto> create(@Valid @RequestBody PetCreateDto dto) {
        return new ResponseEntity<>(petServiceI.create(dto), HttpStatus.CREATED);
    }

    @PostMapping("/addList")
    public ResponseEntity<String> createFromList(@Valid @RequestBody List<PetCreateDto> dtoList) {
        return new ResponseEntity<>(petServiceI.createFromList(dtoList), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetGetDto> update(@Valid @PathVariable ("id") String id, @RequestBody PetUpdateDto dto) {
        return new ResponseEntity<>(petServiceI.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable ("id") String id) {
        return new ResponseEntity<>(petServiceI.delete(id), HttpStatus.OK);
    }
}
