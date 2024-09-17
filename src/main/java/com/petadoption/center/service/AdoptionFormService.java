package com.petadoption.center.service;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.repository.AdoptionFormRepository;
import com.petadoption.center.service.interfaces.AdoptionFormServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionFormService implements AdoptionFormServiceI {

    @Autowired
    private AdoptionFormRepository adoptionFormRepository;

    @Override
    public List<AdoptionFormGetDto> getAllAdoptionForms(int page, int size, String sortBy) {
        return List.of();
    }

    @Override
    public AdoptionFormGetDto getAdoptionFormById(Long id) {
        return null;
    }

    @Override
    public AdoptionFormGetDto addNewAdoptionForm(AdoptionFormCreateDto adoptionForm) {
        return null;
    }

    @Override
    public AdoptionFormGetDto updateAdoptionForm(Long id, AdoptionFormUpdateDto adoptionForm) {
        return null;
    }

    @Override
    public String deleteAdoptionForm(Long id) {
        return "";
    }
}
