package com.petadoption.center.service;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormPatchDto;

import java.util.List;

public interface AdoptionFormService {
    List<AdoptionFormGetDto> getAllAdoptionForms();

    AdoptionFormGetDto getAdoptionFormById(Long AdoptionFormId);

    AdoptionFormGetDto addNewAdoptionForm(AdoptionFormCreateDto AdoptionFormCreateDto);

    AdoptionFormGetDto updateAdoptionForm(Long AdoptionFormId, AdoptionFormPatchDto AdoptionFormPatchDto);
}
