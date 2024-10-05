package com.petadoption.center.controller;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.exception.adoptionform.AdoptionFormNotFoundException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.service.interfaces.AdoptionFormServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<AdoptionFormGetDto>> getAll(@RequestParam (defaultValue = "0", required = false) int page,
                                                           @RequestParam (defaultValue = "5", required = false) int size,
                                                           @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(adoptionFormServiceI.getAllAdoptionForms(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AdoptionFormGetDto> getById(@PathVariable("id") String id)
            throws AdoptionFormNotFoundException {
        return new ResponseEntity<>(adoptionFormServiceI.getAdoptionFormById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AdoptionFormGetDto> create(@RequestBody @Valid AdoptionFormCreateDto dto)
            throws UserNotFoundException, PetNotFoundException {
        return new ResponseEntity<>(adoptionFormServiceI.addNewAdoptionForm(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AdoptionFormGetDto> update(@PathVariable ("id") String id, @RequestBody @Valid AdoptionFormUpdateDto dto)
            throws AdoptionFormNotFoundException {
        return new ResponseEntity<>(adoptionFormServiceI.updateAdoptionForm(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable ("id") String id)
            throws AdoptionFormNotFoundException {
        return new ResponseEntity<>(adoptionFormServiceI.deleteAdoptionForm(id), HttpStatus.OK);
    }
}