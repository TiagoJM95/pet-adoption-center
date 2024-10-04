package com.petadoption.center.service;

import com.petadoption.center.converter.AdoptionFormConverter;
import com.petadoption.center.converter.PetConverter;
import com.petadoption.center.converter.UserConverter;
import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.exception.adoptionform.AdoptionFormNotFoundException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.User;
import com.petadoption.center.repository.AdoptionFormRepository;
import com.petadoption.center.service.interfaces.AdoptionFormServiceI;
import com.petadoption.center.service.interfaces.PetServiceI;
import com.petadoption.center.service.interfaces.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.converter.AdoptionFormConverter.toDto;
import static com.petadoption.center.util.Messages.ADOPTION_FORM_WITH_ID;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Utils.updateFields;


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
    public List<AdoptionFormGetDto> getAllAdoptionForms(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        return adoptionFormRepository.findAll(pageRequest).stream().map(AdoptionFormConverter::toDto).toList();
    }

    @Override
    public AdoptionFormGetDto getAdoptionFormById(String id) throws AdoptionFormNotFoundException{
        return toDto(findAdoptionFormById(id));
    }

    @Override
    public AdoptionFormGetDto addNewAdoptionForm(AdoptionFormCreateDto adoptionFormCreateDto) throws UserNotFoundException, PetNotFoundException {
        AdoptionForm adoptionForm = buildAdoptionFormFromDto(adoptionFormCreateDto);
        return AdoptionFormConverter.toDto(adoptionFormRepository.save(adoptionForm));
    }

    @Override
    public AdoptionFormGetDto updateAdoptionForm(String id, AdoptionFormUpdateDto adoptionFormUpdateDto) throws AdoptionFormNotFoundException {
        AdoptionForm adoptionForm = findAdoptionFormById(id);
        updateAdoptionFormFields(adoptionFormUpdateDto, adoptionForm);
        return toDto(adoptionFormRepository.save(adoptionForm));
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

    private AdoptionForm buildAdoptionFormFromDto(AdoptionFormCreateDto adoptionFormCreateDto) throws UserNotFoundException, PetNotFoundException {
       AdoptionForm adoptionForm = AdoptionFormConverter.toModel(adoptionFormCreateDto);
       User user = UserConverter.toModel(userServiceI.getUserById(adoptionFormCreateDto.userId()));
       Pet pet = PetConverter.toModel(petServiceI.getPetById(adoptionFormCreateDto.petId()));

       adoptionForm.setUser(user);
       adoptionForm.setPet(pet);
       return adoptionForm;
    }

    private void updateAdoptionFormFields(AdoptionFormUpdateDto adoptionFormUpdateDto, AdoptionForm adoptionForm) {
        updateFields(adoptionFormUpdateDto.userFamily(), adoptionForm.getUserFamily(), adoptionForm::setUserFamily);
        updateFields(adoptionFormUpdateDto.petVacationHome(), adoptionForm.getPetVacationHome(), adoptionForm::setPetVacationHome);
        updateFields(adoptionFormUpdateDto.isResponsibleForPet(), adoptionForm.getIsResponsibleForPet(), adoptionForm::setIsResponsibleForPet);
        updateFields(adoptionFormUpdateDto.otherNotes(), adoptionForm.getOtherNotes(), adoptionForm::setOtherNotes);
        updateFields(adoptionFormUpdateDto.petAddress(), adoptionForm.getPetAddress(), adoptionForm::setPetAddress);
    }
}
