package com.petadoption.center.controller;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormPatchDto;
import com.petadoption.center.service.AdoptionFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/adoption-form")
public class AdoptionFormController {

    @Autowired
    private AdoptionFormService adoptionFormService;


    @GetMapping("/")
    public ResponseEntity<List<AdoptionFormGetDto>> getAllAdoptionForms(){
        return new ResponseEntity<>(adoptionFormService.getAllAdoptionForms(), HttpStatus.OK);
    }

    @GetMapping("/id/{adoptionFormId}")
    public ResponseEntity<AdoptionFormGetDto> getAdoptionFormById(@PathVariable("adoptionFormId") Long adoptionFormId){
        return new ResponseEntity<>(adoptionFormService.getAdoptionFormById(adoptionFormId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AdoptionFormGetDto> addNewAdoptionForm(@RequestBody AdoptionFormCreateDto adoptionFormCreateDto){
        return new ResponseEntity<>(adoptionFormService.addNewAdoptionForm(adoptionFormCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{adoptionFormId}")
    public ResponseEntity<AdoptionFormGetDto> updateAdoptionForm(@PathVariable ("adoptionFormId") Long adoptionFormId, @RequestBody AdoptionFormPatchDto adoptionFormPatchDto){
        return new ResponseEntity<>(adoptionFormService.updateAdoptionForm(adoptionFormId, adoptionFormPatchDto), HttpStatus.OK);
    }
}
