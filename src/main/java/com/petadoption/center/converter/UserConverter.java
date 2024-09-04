package com.petadoption.center.converter;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Address;

public class UserConverter {

    public static User fromUserCreateDtoToModel(UserCreateDto user){
        if (user == null) return null;
        Address address = new Address(
                user.street(),
                user.city(),
                user.state(),
                user.postalCode()
        );
        return User.builder()
                .firstName(user.firstName())
                .lastName(user.lastName())
                .email(user.email())
                .dateOfBirth(user.dateOfBirth())
                .address(address)
                .phoneCountryCode(user.phoneCountryCode())
                .phoneNumber(user.phoneNumber())
                .build();

    }

    public static UserGetDto fromModelToUserGetDto(User user){
        if (user == null) return null;
        return new UserGetDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getAddress(),
                user.getPhoneCountryCode(),
                user.getPhoneNumber(),
                user.getFavoritePets(),
                user.getAdoptedPets(),
                user.getUserAdoptionForms()
        );
    }


}
