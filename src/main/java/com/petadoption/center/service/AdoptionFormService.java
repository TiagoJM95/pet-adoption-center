package com.petadoption.center.service;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;

import java.util.List;

public interface AdoptionFormService {
    List<AdoptionFormGetDto> getAllAdoptionForms(int page, int size, String sortBy);

    AdoptionFormGetDto getAdoptionFormById(Long id);

    AdoptionFormGetDto addNewAdoptionForm(AdoptionFormCreateDto adoptionForm);

    AdoptionFormGetDto updateAdoptionForm(Long id, AdoptionFormUpdateDto adoptionForm);
}
