package com.petadoption.center.dto.user;

import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.model.Pet;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record UserGetDto(

        Long id,

        String firstName,

        String lastName,

        String email,

        LocalDate dateOfBirth,

        Address address,

        String phoneCountryCode,

        Integer phoneNumber,

        // TODO change to dto when converters are done

        Set<Pet> favoritePets,

        Set<Pet> adoptedPets,

        Set<AdoptionForm> adoptionForms
) {
}
