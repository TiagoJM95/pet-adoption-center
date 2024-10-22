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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.converter.AdoptionFormConverter.toDto;
import static com.petadoption.center.util.Messages.ADOPTION_FORM_DELETE_MESSAGE;
import static com.petadoption.center.util.Messages.ADOPTION_FORM_NOT_FOUND;
import static java.lang.String.format;

@Service
@CacheConfig(cacheNames = "adoptionForm")
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
    @Cacheable
    public List<AdoptionFormGetDto> getAll(Pageable pageable) {
        return adoptionFormRepository.findAll(pageable).stream().map(AdoptionFormConverter::toDto).toList();
    }

    @Override
    @Cacheable(key = "#id")
    public AdoptionFormGetDto getById(String id) {
        return toDto(findById(id));
    }

    @Override
    public AdoptionFormGetDto create(AdoptionFormCreateDto dto) {
        AdoptionForm adoptionForm = buildAdoptionFormFromDto(dto);
        return AdoptionFormConverter.toDto(adoptionFormRepository.save(adoptionForm));
    }

    @Override
    @CachePut(key = "#id")
    public AdoptionFormGetDto update(String id, AdoptionFormUpdateDto dto) {
        AdoptionForm adoptionForm = findById(id);
        updateFields(dto, adoptionForm);
        return toDto(adoptionFormRepository.save(adoptionForm));
    }

    @Override
    @CacheEvict(key = "#id")
    public String delete(String id) {
        findById(id);
        adoptionFormRepository.deleteById(id);
        return format(ADOPTION_FORM_DELETE_MESSAGE, id);
    }

    private AdoptionForm findById(String id) {
        return adoptionFormRepository.findById(id).orElseThrow(
                () -> new AdoptionFormNotFoundException(format(ADOPTION_FORM_NOT_FOUND, id)));
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