package com.petadoption.center.dto.adoptionForm;

public record AdoptionFormCreateDto(
        Long userId,
        Long petId
) {
}
