package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormPatchDto;
import com.petadoption.center.service.AdoptionFormService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionFormServiceImpl implements AdoptionFormService {
    @Override
    public List<AdoptionFormGetDto> getAllAdoptionForms() {
        return List.of();
    }

    @Override
    public AdoptionFormGetDto getAdoptionFormById(Long AdoptionFormId) {
        return null;
    }

    @Override
    public AdoptionFormGetDto addNewAdoptionForm(AdoptionFormCreateDto AdoptionFormCreateDto) {
        return null;
    }

    @Override
    public AdoptionFormGetDto updateAdoptionForm(Long AdoptionFormId, AdoptionFormPatchDto AdoptionFormPatchDto) {
        return null;
    }
}
