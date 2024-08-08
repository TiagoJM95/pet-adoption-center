package com.petadoption.center.dto.user;

import com.petadoption.center.model.Address;

import java.time.LocalDate;

public record UserCreateDto(

        String firstName,

        String lastName,

        String email,

        LocalDate dateOfBirth,

        Address address,

        String phoneCountryCode,

        Integer phoneNumber
) {
}
