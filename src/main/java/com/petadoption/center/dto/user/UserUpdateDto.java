package com.petadoption.center.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.petadoption.center.util.Messages.*;

public record UserUpdateDto(

        @Pattern(regexp = "[a-zA-Z]+", message = FIRSTNAME_ONLY_LETTERS)
        String firstName,

        @Pattern(regexp = "[a-zA-Z]+", message = LASTNAME_ONLY_LETTERS)
        String lastName,

        @Email
        @Size(max = 100, message = EMAIL_CHARACTERS)
        String email,

        @Pattern(regexp = "[a-zA-Z_0-9,.-]+", message = STREET_CHARACTERS)
        String street,

        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        String city,

        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        String state,

        @Size(max = 9, message = POSTAL_CODE_SIZE)
        @Pattern(regexp = "[0-9]-]+", message = POSTAL_CODE_FORMAT)
        String postalCode,

        @Pattern(regexp = "[0-9+]{1,4}", message = PHONE_COUNTRY_CODE)
        String phoneCountryCode,

        @Pattern(regexp = "[0-9]+", message = PHONE_NUMBER_FORMAT)
        @Size(max = 10, message = PHONE_NUMBER_SIZE)
        Integer phoneNumber
) {
}
