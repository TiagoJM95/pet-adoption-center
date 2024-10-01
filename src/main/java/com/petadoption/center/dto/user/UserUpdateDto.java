package com.petadoption.center.dto.user;

import com.petadoption.center.model.embeddable.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static com.petadoption.center.util.Messages.*;
@Builder
public record UserUpdateDto(

        @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
        @Schema(
                description = "User first name",
                example = "Manuel")
        String firstName,

        @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
        @Schema(
                description = "User last name",
                example = "Silva")
        String lastName,

        @Email
        @Size(max = 100, message = CHARACTERS_LIMIT)
        @Schema(
                description = "User email",
                example = "email@email.com")
        String email,

        @Valid
        @NotNull(message = BLANK_FIELD)
        Address address,

        @NotNull(message = BLANK_FIELD)
        @Schema(
                description = "User phone number",
                example = "918765432")
        String phoneNumber
) {}