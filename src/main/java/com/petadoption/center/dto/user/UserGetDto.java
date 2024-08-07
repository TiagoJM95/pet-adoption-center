package com.petadoption.center.dto.user;

public record UserGetDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String dateOfBirth,
        String address
) {
}
