package com.petadoption.center.dto.organization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.petadoption.center.util.Messages.*;

public record OrgUpdateDto(

        @Pattern(regexp = "[a-zA-Z_0-9]+", message = LETTERS_AND_NUMBERS)
        String name,

        @Email
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String email,

        @Size(min = 9, max = 9, message = PHONE_NUMBER_SIZE)
        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        String nif,

        @Pattern(regexp = "[0-9]+", message = PHONE_NUMBER_FORMAT)
        @Size(max = 10, message = PHONE_NUMBER_SIZE)
        String phoneNumber,

        @Pattern(regexp = "[a-zA-Z_0-9,.-]+", message = STREET_CHARACTERS)
        String street,

        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        String city,

        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        String state,

        @Size(max = 9, message = POSTAL_CODE_SIZE)
        @Pattern(regexp = "[0-9]-]+", message = POSTAL_CODE_FORMAT)
        String postalCode,

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

) {}