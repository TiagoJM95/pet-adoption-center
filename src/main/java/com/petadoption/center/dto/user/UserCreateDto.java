package com.petadoption.center.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petadoption.center.model.embeddable.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Regex.*;

@Builder(toBuilder = true)
public record UserCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = USER_NAME_REGEX, message = ONLY_LETTERS)
        @NotNull
        String firstName,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = USER_NAME_REGEX, message = ONLY_LETTERS)
        String lastName,

        @NotBlank(message = BLANK_FIELD)
        @Email(regexp = EMAIL_REGEX, message = EMAIL_INVALID)
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String email,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = NIF_REGEX, message = NIF_INVALID)
        String nif,

        @JsonFormat(pattern = DATE_REGEX, shape = JsonFormat.Shape.STRING)
        LocalDate dateOfBirth,

        @Valid
        @NotNull(message = BLANK_FIELD)
        Address address,

        @NotNull(message = BLANK_FIELD)
        @Pattern(regexp = PHONE_NUMBER_REGEX, message = PHONE_NUMBER_SIZE)
        String phoneNumber
) {}