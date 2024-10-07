package com.petadoption.center.controller;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.service.interfaces.AdoptionFormServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/adoption-form")
public class AdoptionFormController {

    private final AdoptionFormServiceI adoptionFormServiceI;

    @Autowired
    public AdoptionFormController(AdoptionFormServiceI adoptionFormServiceI) {
        this.adoptionFormServiceI = adoptionFormServiceI;
    }

    @GetMapping("/")
    public ResponseEntity<List<AdoptionFormGetDto>> getAll(@PageableDefault(sort = "createdAt") Pageable pageable) {
        return new ResponseEntity<>(adoptionFormServiceI.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AdoptionFormGetDto> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(adoptionFormServiceI.getById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AdoptionFormGetDto> create(@RequestBody @Valid AdoptionFormCreateDto dto) {
        return new ResponseEntity<>(adoptionFormServiceI.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AdoptionFormGetDto> update(@PathVariable ("id") String id, @RequestBody @Valid AdoptionFormUpdateDto dto) {
        return new ResponseEntity<>(adoptionFormServiceI.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable ("id") String id) {
        return new ResponseEntity<>(adoptionFormServiceI.delete(id), HttpStatus.OK);
    }
}