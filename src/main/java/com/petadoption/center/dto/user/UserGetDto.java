package com.petadoption.center.dto.user;

import com.petadoption.center.model.Address;
import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.model.Pet;

import java.time.LocalDate;
import java.util.List;

public record UserGetDto(

        Long id,

        String firstName,

        String lastName,

        String email,

        LocalDate dateOfBirth,

        Address address,

        String phoneCountryCode,

        Integer phoneNumber,

        List<Pet> favoritePets,

        List<Pet> adoptedPets,

        List<AdoptionForm> adoptionForms
) {
}
