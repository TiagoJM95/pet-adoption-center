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

@Builder(toBuilder = true)
public record OrganizationUpdateDto(

        @Pattern(regexp = "[a-zA-Z_0-9 .-]+", message = LETTERS_AND_NUMBERS)
        String name,

        @Email
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String email,

        @Pattern(regexp = "[0-9]+", message = PHONE_NUMBER_FORMAT)
        @Size(max = 10, message = PHONE_NUMBER_SIZE)
        String phoneNumber,

        @Valid
        @NotNull(message = BLANK_FIELD)
        Address address,

        @Pattern(regexp = "^[a-zA-Z0-9.@_:/-]*$", message = WEBSITE_URL)
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String websiteUrl,

        @Valid
        SocialMedia socialMedia
) {}