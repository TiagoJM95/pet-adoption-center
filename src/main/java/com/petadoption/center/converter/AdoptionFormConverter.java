package com.petadoption.center.converter;

import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.User;

public class AdoptionFormConverter {

    public static AdoptionForm toModel(User user, Pet pet) {
        return AdoptionForm.builder()
                .userId(user)
                .petId(pet)
                .build();
    }

    public static AdoptionFormGetDto toDto(AdoptionForm adoptionForm,
                                           UserGetDto user,
                                           PetGetDto pet) {
        return new AdoptionFormGetDto(
                adoptionForm.getId(),
                user,
                pet
        );
    }
}
