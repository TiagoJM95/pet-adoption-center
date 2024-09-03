package com.petadoption.center.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

import static com.petadoption.center.util.Messages.*;

public record UserCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
        @NotNull
        String firstName,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
        String lastName,

        @NotBlank(message = BLANK_FIELD)
        @Email
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String email,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
        LocalDate dateOfBirth,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[ a-zA-Z_0-9,.-]+", message = STREET_CHARACTERS)
        String street,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[ a-zA-Z]+", message = ONLY_LETTERS)
        String city,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[ a-zA-Z]+", message = ONLY_LETTERS)
        String state,

        @NotBlank(message = BLANK_FIELD)
        @Size(max = 8, min = 8, message = POSTAL_CODE_SIZE)
        @Pattern(regexp = "[0-9-]+", message = POSTAL_CODE_FORMAT)
        String postalCode,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[0-9+]{1,4}", message = PHONE_COUNTRY_CODE)
        String phoneCountryCode,

        @NotNull(message = BLANK_FIELD)
        Integer phoneNumber
) {
}
