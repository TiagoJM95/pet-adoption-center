package com.petadoption.center.dto.user;

public record UserUpdateDto(

        String firstName,

        String lastName,

        String email,

        String street,

        String city,

        String state,

        String postalCode,

        String phoneCountryCode,

        Integer phoneNumber
) {
}
