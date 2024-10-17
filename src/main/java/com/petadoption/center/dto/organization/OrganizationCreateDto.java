package com.petadoption.center.dto.organization;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Regex.*;

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
        @Pattern(regexp = NIPC_REGEX, message = NIPC_INVALID)
        String nipc,

        @NotNull(message = BLANK_FIELD)
        @Pattern(regexp = PHONE_NUMBER_REGEX, message = PHONE_NUMBER_SIZE)
        String phoneNumber,

        @Valid
        @NotNull(message = BLANK_FIELD)
        Address address,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = WEBSITE_REGEX, message = WEBSITE_URL)
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String websiteUrl,

        @Valid
        SocialMedia socialMedia
) {}