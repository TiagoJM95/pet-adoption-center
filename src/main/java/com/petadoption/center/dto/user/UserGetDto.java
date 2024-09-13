package com.petadoption.center.dto.user;

import com.petadoption.center.model.embeddable.Address;

import java.time.LocalDate;

public record UserGetDto(
        String id,
        String firstName,
        String lastName,
        String email,
        String nif,
        LocalDate dateOfBirth,
        Address address,
        String phoneNumber
) {}