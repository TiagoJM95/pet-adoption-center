package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.repository.AdoptionFormRepository;
import com.petadoption.center.service.AdoptionFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionFormServiceImpl implements AdoptionFormService {

    @Autowired
    private AdoptionFormRepository adoptionFormRepository;

    @Override
    public List<AdoptionFormGetDto> getAllAdoptionForms() {
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
}
