package com.petadoption.center.dto.adoptionForm;

import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;

public record AdoptionFormGetDto(
        Long id,
        UserGetDto user,
        PetGetDto pet,
        Family userFamily,
        String petVacationHome,
        Boolean isResponsibleForPet,
        String otherNotes,
        Address petAddress
) {
}
