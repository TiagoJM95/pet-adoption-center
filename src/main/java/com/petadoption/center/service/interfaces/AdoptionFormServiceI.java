package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.exception.adoptionform.AdoptionFormNotFoundException;

import java.util.List;

public interface AdoptionFormServiceI {
    List<AdoptionFormGetDto> getAllAdoptionForms(int page, int size, String sortBy);

    AdoptionFormGetDto getAdoptionFormById(String id) throws AdoptionFormNotFoundException;

    AdoptionFormGetDto addNewAdoptionForm(AdoptionFormCreateDto adoptionForm);

    AdoptionFormGetDto updateAdoptionForm(String id, AdoptionFormUpdateDto adoptionForm) throws AdoptionFormNotFoundException;

    String deleteAdoptionForm(String id) throws AdoptionFormNotFoundException;
}
