package com.petadoption.center.converter;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Address;

public class UserConverter {

    public static User toModel(UserCreateDto dto){
        if (dto == null) return null;
        Address address = new Address(
                dto.street(),
                dto.city(),
                dto.state(),
                dto.postalCode()
        );
        return User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .nif(dto.nif())
                .dateOfBirth(dto.dateOfBirth())
                .address(address)
                .phoneNumber(dto.phoneNumber())
                .build();

    }

    public static UserGetDto toDto(User user){
        if (user == null) return null;
        return new UserGetDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getNif(),
                user.getDateOfBirth(),
                user.getAddress(),
                user.getPhoneNumber()
        );
    }
}