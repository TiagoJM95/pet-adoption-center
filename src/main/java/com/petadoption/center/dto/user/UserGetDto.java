package com.petadoption.center.dto.user;

import com.petadoption.center.model.embeddable.Address;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record UserGetDto(
        String id,
        String firstName,
        String lastName,
        String email,
        String nif,
        LocalDate dateOfBirth,
        Address address,
        String phoneNumber,
        LocalDateTime createdAt
) {}