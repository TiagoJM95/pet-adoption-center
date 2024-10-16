package com.petadoption.center.dto.user;

import com.petadoption.center.model.embeddable.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static com.petadoption.center.util.Messages.*;
@Builder(toBuilder = true)
public record UserUpdateDto(

        @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
        String firstName,

        @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
        String lastName,

        @Email
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String email,

        @Valid
        @NotNull(message = BLANK_FIELD)
        Address address,

        @NotNull(message = BLANK_FIELD)
        String phoneNumber
) {}