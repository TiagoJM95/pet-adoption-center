package com.petadoption.center.controller;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.exception.not_found.AdoptionFormNotFoundException;
import com.petadoption.center.exception.not_found.PetNotFoundException;
import com.petadoption.center.exception.not_found.UserNotFoundException;
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

    @Autowired
    private AdoptionFormServiceI adoptionFormServiceI;

    @GetMapping("/")
    public ResponseEntity<List<AdoptionFormGetDto>> getAll(@PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable){
        return new ResponseEntity<>(adoptionFormServiceI.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AdoptionFormGetDto> getById(@PathVariable("id") String id)
            throws AdoptionFormNotFoundException {
        return new ResponseEntity<>(adoptionFormServiceI.getById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AdoptionFormGetDto> create(@RequestBody @Valid AdoptionFormCreateDto dto)
            throws UserNotFoundException, PetNotFoundException {
        return new ResponseEntity<>(adoptionFormServiceI.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AdoptionFormGetDto> update(@PathVariable ("id") String id, @RequestBody @Valid AdoptionFormUpdateDto dto)
            throws AdoptionFormNotFoundException {
        return new ResponseEntity<>(adoptionFormServiceI.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable ("id") String id)
            throws AdoptionFormNotFoundException {
        return new ResponseEntity<>(adoptionFormServiceI.delete(id), HttpStatus.OK);
    }
}