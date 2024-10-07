package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdoptionFormServiceI {
    List<AdoptionFormGetDto> getAll(Pageable pageable);
    AdoptionFormGetDto getById(String id);
    AdoptionFormGetDto create(AdoptionFormCreateDto dto);
    AdoptionFormGetDto update(String id, AdoptionFormUpdateDto dto);
    String delete(String id);
}