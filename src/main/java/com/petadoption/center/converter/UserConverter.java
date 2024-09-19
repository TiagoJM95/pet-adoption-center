package com.petadoption.center.converter;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.User;

import static com.petadoption.center.factory.AddressFactory.createAddress;

public class UserConverter {

    public static User toModel(UserCreateDto dto){
        if (dto == null) return null;
        return User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .nif(dto.nif())
                .dateOfBirth(dto.dateOfBirth())
                .address(dto.address())
                .phoneNumber(dto.phoneNumber())
                .build();

    }

    public static User toModel(UserGetDto dto){
        if (dto == null) return null;
        return User.builder()
                .id(dto.id())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .nif(dto.nif())
                .dateOfBirth(dto.dateOfBirth())
                .address(dto.address())
                .phoneNumber(dto.phoneNumber())
                .build();

    }

    public static UserGetDto toDto(User user){
        if (user == null) return null;
        return UserGetDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .nif(user.getNif())
                .dateOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}