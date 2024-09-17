package com.petadoption.center.controller;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.service.interfaces.AdoptionFormServiceI;
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
    public ResponseEntity<List<AdoptionFormGetDto>> getAllAdoptionForms(@RequestParam (defaultValue = "0", required = false) int page,
                                                                        @RequestParam (defaultValue = "5", required = false) int size,
                                                                        @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(adoptionFormServiceI.getAllAdoptionForms(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AdoptionFormGetDto> getAdoptionFormById(@PathVariable("id") Long id){
        return new ResponseEntity<>(adoptionFormServiceI.getAdoptionFormById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AdoptionFormGetDto> addNewAdoptionForm(@RequestBody AdoptionFormCreateDto adoptionForm){
        return new ResponseEntity<>(adoptionFormServiceI.addNewAdoptionForm(adoptionForm), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AdoptionFormGetDto> updateAdoptionForm(@PathVariable ("id") Long id,
                                                                 @RequestBody AdoptionFormUpdateDto adoptionForm){
        return new ResponseEntity<>(adoptionFormServiceI.updateAdoptionForm(id, adoptionForm), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdoptionForm(@PathVariable ("id") Long id) {
        return new ResponseEntity<>(adoptionFormServiceI.deleteAdoptionForm(id), HttpStatus.OK);
    }
}
