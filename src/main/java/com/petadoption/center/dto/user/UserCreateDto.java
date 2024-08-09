package com.petadoption.center.dto.user;

import com.petadoption.center.model.embeddable.Address;

import java.time.LocalDate;

public record UserCreateDto(

        String firstName,

        String lastName,

        String email,

        LocalDate dateOfBirth,

        String street,

        String city,

        String state,

        String postalCode,

        String phoneCountryCode,

        Integer phoneNumber
) {
}
