package com.petadoption.center.service;

import com.petadoption.center.converter.AdoptionFormConverter;
import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.exception.adoptionform.AdoptionFormNotFoundException;
import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.repository.AdoptionFormRepository;
import com.petadoption.center.service.interfaces.AdoptionFormServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.util.Messages.ADOPTION_FORM_WITH_ID;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;


@Service
public class AdoptionFormService implements AdoptionFormServiceI {

    @Autowired
    private AdoptionFormRepository adoptionFormRepository;

    @Override
    public List<AdoptionFormGetDto> getAllAdoptionForms(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        /*return adoptionFormRepository.findAll(pageRequest).stream().map((adoptionForm) -> AdoptionFormConverter.toDto(adoptionForm, )).toList();*/
        return null;

    }

    @Override
    public AdoptionFormGetDto getAdoptionFormById(String id) throws AdoptionFormNotFoundException{
        return null;
    }

    @Override
    public AdoptionFormGetDto addNewAdoptionForm(AdoptionFormCreateDto adoptionForm) {
        return null;
    }

    @Override
    public AdoptionFormGetDto updateAdoptionForm(String id, AdoptionFormUpdateDto adoptionForm) throws AdoptionFormNotFoundException {
        return null;
    }

    @Override
    public String deleteAdoptionForm(String id) throws AdoptionFormNotFoundException {
        findAdoptionFormById(id);
        adoptionFormRepository.deleteById(id);
        return ADOPTION_FORM_WITH_ID + id + DELETE_SUCCESS;
    }

    private AdoptionForm findAdoptionFormById(String id) throws AdoptionFormNotFoundException {
        return adoptionFormRepository.findById(id).orElseThrow(() -> new AdoptionFormNotFoundException(id));
    }
}
