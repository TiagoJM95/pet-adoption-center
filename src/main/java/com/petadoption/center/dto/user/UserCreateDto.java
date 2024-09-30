package com.petadoption.center.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petadoption.center.model.embeddable.Address;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(description = "User first name", example = "Manuel")
        String firstName,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
        @Schema(description = "User last name", example = "Silva")
        String lastName,

        @NotBlank(message = BLANK_FIELD)
        @Email
        @Size(max = 100, message = CHARACTERS_LIMIT)
        @Schema(description = "User email", example = "email@email.com")
        String email,

        @NotBlank(message = BLANK_FIELD)
        @Size(min = 9, max = 9, message = PHONE_NUMBER_SIZE)
        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        @Schema(description = "User tax identification number", example = "123456789")
        String nif,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
        @Schema(description = "User date of birth", example = "1990-01-01")
        LocalDate dateOfBirth,

        @Valid
        @NotNull(message = BLANK_FIELD)
        Address address,

        @NotNull(message = BLANK_FIELD)
        @Schema(description = "User phone number", example = "918765432")
        String phoneNumber
) {}