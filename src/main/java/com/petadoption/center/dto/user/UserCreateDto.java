package com.petadoption.center.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petadoption.center.model.embeddable.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

import static com.petadoption.center.util.Messages.*;

@Builder
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

        @NotBlank(message = BLANK_FIELD)
        @Size(min = 9, max = 9, message = PHONE_NUMBER_SIZE)
        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        String nif,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
        LocalDate dateOfBirth,

        @Valid
        @NotNull(message = BLANK_FIELD)
        Address address,

        @NotNull(message = BLANK_FIELD)
        String phoneNumber
) {}