package com.petadoption.center.service;

import com.petadoption.center.converter.AdoptionFormConverter;
import com.petadoption.center.converter.PetConverter;
import com.petadoption.center.converter.UserConverter;
import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.exception.not_found.AdoptionFormNotFoundException;
import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.User;
import com.petadoption.center.repository.AdoptionFormRepository;
import com.petadoption.center.service.interfaces.AdoptionFormServiceI;
import com.petadoption.center.service.interfaces.PetServiceI;
import com.petadoption.center.service.interfaces.UserServiceI;
import com.petadoption.center.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.converter.AdoptionFormConverter.toDto;
import static com.petadoption.center.util.Messages.ADOPTION_FORM_WITH_ID;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;

@Service
public class AdoptionFormService implements AdoptionFormServiceI {

    private final AdoptionFormRepository adoptionFormRepository;
    private final UserServiceI userServiceI;
    private final PetServiceI petServiceI;

    @Autowired
    public AdoptionFormService(AdoptionFormRepository adoptionFormRepository, UserServiceI userServiceI, PetServiceI petServiceI) {
        this.adoptionFormRepository = adoptionFormRepository;
        this.userServiceI = userServiceI;
        this.petServiceI = petServiceI;
    }

    @Override
    public List<AdoptionFormGetDto> getAll(Pageable pageable) {
        return adoptionFormRepository.findAll(pageable).stream().map(AdoptionFormConverter::toDto).toList();
    }

    @Override
    public AdoptionFormGetDto getById(String id) {
        return toDto(findById(id));
    }

    @Override
    public AdoptionFormGetDto create(AdoptionFormCreateDto dto) {
        AdoptionForm adoptionForm = buildAdoptionFormFromDto(dto);
        return AdoptionFormConverter.toDto(adoptionFormRepository.save(adoptionForm));
    }

    @Override
    public AdoptionFormGetDto update(String id, AdoptionFormUpdateDto dto) {
        AdoptionForm adoptionForm = findById(id);
        updateFields(dto, adoptionForm);
        return toDto(adoptionFormRepository.save(adoptionForm));
    }

    @Override
    public String delete(String id) {
        findById(id);
        adoptionFormRepository.deleteById(id);
        return ADOPTION_FORM_WITH_ID + id + DELETE_SUCCESS;
    }

    private AdoptionForm findById(String id) {
        return adoptionFormRepository.findById(id).orElseThrow(() -> new AdoptionFormNotFoundException(id));
    }

    private AdoptionForm buildAdoptionFormFromDto(AdoptionFormCreateDto dto) {
       AdoptionForm adoptionForm = AdoptionFormConverter.toModel(dto);
       User user = UserConverter.toModel(userServiceI.getById(dto.userId()));
       Pet pet = PetConverter.toModel(petServiceI.getById(dto.petId()));

       adoptionForm.setUser(user);
       adoptionForm.setPet(pet);
       return adoptionForm;
    }

    private void updateFields(AdoptionFormUpdateDto dto, AdoptionForm adoptionForm) {
        Utils.updateFields(dto.userFamily(), adoptionForm.getUserFamily(), adoptionForm::setUserFamily);
        Utils.updateFields(dto.petVacationHome(), adoptionForm.getPetVacationHome(), adoptionForm::setPetVacationHome);
        Utils.updateFields(dto.isResponsibleForPet(), adoptionForm.getIsResponsibleForPet(), adoptionForm::setIsResponsibleForPet);
        Utils.updateFields(dto.otherNotes(), adoptionForm.getOtherNotes(), adoptionForm::setOtherNotes);
        Utils.updateFields(dto.petAddress(), adoptionForm.getPetAddress(), adoptionForm::setPetAddress);
    }
}