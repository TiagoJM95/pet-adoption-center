package com.petadoption.center.dto.user;

import com.petadoption.center.model.embeddable.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserGetDto(
        @Schema(description = "User id", example = "123456789-1234-1234-1234-123456789123")
        String id,

        @Schema(description = "User first name", example = "Manuel")
        String firstName,

        @Schema(description = "User last name", example = "Silva")
        String lastName,

        @Schema(description = "User email", example = "email@email.com")
        String email,

        @Schema(description = "User tax identification number", example = "123456789")
        String nif,

        @Schema(description = "User date of birth", example = "1990-01-01")
        LocalDate dateOfBirth,

        Address address,

        @Schema(description = "User phone number", example = "918765432")
        String phoneNumber
) {}