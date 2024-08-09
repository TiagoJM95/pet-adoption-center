package com.petadoption.center.dto.adoptionForm;

import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserGetDto;

public record AdoptionFormGetDto(
        Long id,
        UserGetDto user,
        PetGetDto pet
) {
}
