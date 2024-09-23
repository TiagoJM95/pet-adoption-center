package com.petadoption.center.converter;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.model.AdoptionForm;

public class AdoptionFormConverter {

    public static AdoptionForm toModel(AdoptionFormCreateDto dto) {
        if (dto == null) return null;
        return AdoptionForm.builder()
                .userFamily(dto.userFamily())
                .petVacationHome(dto.petVacationHome())
                .isResponsibleForPet(dto.isResponsibleForPet())
                .otherNotes(dto.otherNotes())
                .petAddress(dto.petAddress())
                .build();
    }

    public static AdoptionForm toModel(AdoptionFormGetDto dto) {
        if (dto == null) return null;
        return AdoptionForm.builder()
                .id(dto.id())
                .user(UserConverter.toModel(dto.user()))
                .pet(PetConverter.toModel(dto.pet()))
                .userFamily(dto.userFamily())
                .petVacationHome(dto.petVacationHome())
                .isResponsibleForPet(dto.isResponsibleForPet())
                .otherNotes(dto.otherNotes())
                .petAddress(dto.petAddress())
                .build();
    }

    public static AdoptionFormGetDto toDto(AdoptionForm adoptionForm) {
        if (adoptionForm == null) return null;
        return AdoptionFormGetDto.builder()
                .id(adoptionForm.getId())
                .user(UserConverter.toDto(adoptionForm.getUser()))
                .pet(PetConverter.toDto(adoptionForm.getPet()))
                .userFamily(adoptionForm.getUserFamily())
                .petVacationHome(adoptionForm.getPetVacationHome())
                .isResponsibleForPet(adoptionForm.getIsResponsibleForPet())
                .otherNotes(adoptionForm.getOtherNotes())
                .petAddress(adoptionForm.getPetAddress())
                .build();
    }
}
