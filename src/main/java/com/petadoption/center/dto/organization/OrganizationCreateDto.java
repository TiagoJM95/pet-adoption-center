package com.petadoption.center.dto.organization;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import static com.petadoption.center.util.Messages.*;

@Builder(toBuilder = true)
public record OrganizationCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z_0-9 .-]+", message = LETTERS_AND_NUMBERS)
        String name,

        @NotBlank(message = BLANK_FIELD)
        @Email
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String email,

        @NotBlank(message = BLANK_FIELD)
        @Size(min = 9, max = 9, message = PHONE_NUMBER_SIZE)
        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        String nif,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[0-9]+", message = PHONE_NUMBER_FORMAT)
        @Size(max = 10, message = PHONE_NUMBER_SIZE)
        String phoneNumber,

        @Valid
        @NotNull(message = BLANK_FIELD)
        Address address,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "^[a-zA-Z0-9.@_:/-]*$", message = WEBSITE_URL)
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String websiteUrl,

        @Valid
        SocialMedia socialMedia
) {}