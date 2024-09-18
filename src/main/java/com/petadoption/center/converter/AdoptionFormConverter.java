package com.petadoption.center.converter;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.User;

public class AdoptionFormConverter {

    public static AdoptionForm toModel(AdoptionFormCreateDto dto, User user, Pet pet) {
        if (dto == null) return null;
        return AdoptionForm.builder()
                .userId(user)
                .petId(pet)
                .userFamily(dto.userFamily())
                .petVacationHome(dto.petVacationHome())
                .isResponsibleForPet(dto.isResponsibleForPet())
                .otherNotes(dto.otherNotes())
                .petAddress(user.getAddress())
                .build();
    }

    public static AdoptionFormGetDto toDto(AdoptionForm adoptionForm,
                                           UserGetDto user,
                                           PetGetDto pet) {
        return new AdoptionFormGetDto(
                adoptionForm.getId(),
                user,
                pet,
                adoptionForm.getUserFamily(),
                adoptionForm.getPetVacationHome(),
                adoptionForm.getIsResponsibleForPet(),
                adoptionForm.getOtherNotes(),
                adoptionForm.getPetAddress()
        );
    }
}
