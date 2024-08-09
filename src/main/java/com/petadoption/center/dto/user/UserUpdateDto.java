package com.petadoption.center.dto.user;

import com.petadoption.center.model.embeddable.Address;

public record UserUpdateDto(

        String firstName,

        String lastName,

        String email,

        Address address,

        String phoneCountryCode,

        Integer phoneNumber
) {
}
