package com.petadoption.center.dto.adoptionForm;

import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AdoptionFormGetDto(
        String id,
        UserGetDto user,
        PetGetDto pet,
        Family userFamily,
        String petVacationHome,
        Boolean isResponsibleForPet,
        String otherNotes,
        Address petAddress,
        LocalDateTime createdAt
) {}