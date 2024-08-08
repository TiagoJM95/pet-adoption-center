package com.petadoption.center.dto.user;

import com.petadoption.center.model.Address;

public record UserPatchDto(

        String firstName,

        String lastName,

        String email,

        Address address,

        String phoneCountryCode,

        Integer phoneNumber
) {
}
