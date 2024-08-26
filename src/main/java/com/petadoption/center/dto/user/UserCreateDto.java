package com.petadoption.center.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petadoption.center.model.embeddable.Address;

import java.time.LocalDate;

public record UserCreateDto(

        String firstName,

        String lastName,

        String email,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
        LocalDate dateOfBirth,

        String street,

        String city,

        String state,

        String postalCode,

        String phoneCountryCode,

        Integer phoneNumber
) {
}
