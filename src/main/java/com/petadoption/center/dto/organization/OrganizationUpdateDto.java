package com.petadoption.center.dto.organization;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Regex.*;

@Builder(toBuilder = true)
public record OrganizationUpdateDto(

        @Pattern(regexp = ORG_NAME_REGEX, message = LETTERS_AND_NUMBERS)
        String name,

        @Email(regexp = EMAIL_REGEX, message = EMAIL_INVALID)
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String email,

        @Pattern(regexp = PHONE_NUMBER_REGEX, message = PHONE_NUMBER_SIZE)
        String phoneNumber,

        @Valid
        @NotNull(message = BLANK_FIELD)
        Address address,

        @Pattern(regexp = WEBSITE_REGEX, message = WEBSITE_URL)
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String websiteUrl,

        @Valid
        SocialMedia socialMedia
) {}