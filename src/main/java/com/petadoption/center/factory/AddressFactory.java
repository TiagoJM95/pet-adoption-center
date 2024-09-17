package com.petadoption.center.factory;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgUpdateDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.model.embeddable.Address;

public class AddressFactory {

    public static Address createAddress(UserCreateDto dto) {
        return Address.builder()
                .city(dto.city())
                .postalCode(dto.postalCode())
                .state(dto.state())
                .street(dto.street())
                .build();
    }

    public static Address createAddress(UserUpdateDto dto) {
        return Address.builder()
                .city(dto.city())
                .postalCode(dto.postalCode())
                .state(dto.state())
                .street(dto.street())
                .build();
    }

    public static Address createAddress(OrgCreateDto dto) {
        return Address.builder()
                .city(dto.city())
                .postalCode(dto.postalCode())
                .state(dto.state())
                .street(dto.street())
                .build();
    }

    public static Address createAddress(OrgUpdateDto dto) {
        return Address.builder()
                .city(dto.city())
                .postalCode(dto.postalCode())
                .state(dto.state())
                .street(dto.street())
                .build();
    }
}