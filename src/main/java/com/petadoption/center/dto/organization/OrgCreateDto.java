package com.petadoption.center.dto.organization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Messages.PHONE_NUMBER_SIZE;

public record OrgCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z_0-9]+", message = LETTERS_AND_NUMBERS)
        String name,

        @NotBlank(message = BLANK_FIELD)
        @Email
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String email,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[0-9]+", message = PHONE_NUMBER_FORMAT)
        @Size(max = 10, message = PHONE_NUMBER_SIZE)
        String phoneNumber,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z_0-9,.-]+", message = STREET_CHARACTERS)
        String street,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        String city,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        String state,

        @NotBlank(message = BLANK_FIELD)
        @Size(max = 9, message = POSTAL_CODE_SIZE)
        @Pattern(regexp = "[0-9]-]+", message = POSTAL_CODE_FORMAT)
        String postalCode,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z_0-9.-]", message = WEBSITE_URL)
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String websiteUrl,

        @Pattern(regexp = "[a-zA-Z_0-9.-/]", message = FACEBOOK_VALID)
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String facebook,

        @Pattern(regexp = "[a-zA-Z_0-9.-@]", message = INSTAGRAM_VALID)
        @Size(max = 30, message = CHARACTERS_LIMIT)
        String instagram,

        @Pattern(regexp = "[a-zA-Z_0-9.-/]", message = TWITTER_VALID)
        @Size(max = 30, message = CHARACTERS_LIMIT)
        String twitter,

        @Pattern(regexp = "[a-zA-Z_0-9.-/]", message = YOUTUBE_VALID)
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String youtube

) implements OrgDto {}
