package com.petadoption.center.dto.user;

import com.petadoption.center.model.embeddable.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Regex.EMAIL_REGEX;
import static com.petadoption.center.util.Regex.PHONE_NUMBER_REGEX;

@Builder(toBuilder = true)
public record UserUpdateDto(

        @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
        String firstName,

        @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
        String lastName,

        @Email(regexp = EMAIL_REGEX, message = EMAIL_INVALID)
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String email,

        Address address,

        @Pattern(regexp = PHONE_NUMBER_REGEX, message = PHONE_NUMBER_SIZE)
        String phoneNumber
) {}