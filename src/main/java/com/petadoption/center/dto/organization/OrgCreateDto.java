package com.petadoption.center.dto.organization;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import static com.petadoption.center.util.Messages.*;

@Builder
public record OrgCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z_0-9 .-]+", message = LETTERS_AND_NUMBERS)
        @Schema(description = "Organization name", example = "Animal Rescue")
        String name,

        @NotBlank(message = BLANK_FIELD)
        @Email
        @Size(max = 100, message = CHARACTERS_LIMIT)
        @Schema(description = "Organization email", example = "email@email.com")
        String email,

        @NotBlank(message = BLANK_FIELD)
        @Size(min = 9, max = 9, message = PHONE_NUMBER_SIZE)
        @Pattern(regexp = "[0-9]+", message = ONLY_NUMBERS)
        @Schema(description = "Organization tax identification number", example = "123456789")
        String nif,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[0-9]+", message = PHONE_NUMBER_FORMAT)
        @Size(max = 10, message = PHONE_NUMBER_SIZE)
        @Schema(description = "Organization phone number", example = "918765432")
        String phoneNumber,

        @Valid
        @NotNull(message = BLANK_FIELD)
        Address address,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z_0-9.-]+", message = WEBSITE_URL)
        @Size(max = 100, message = CHARACTERS_LIMIT)
        @Schema(description = "Organization website url", example = "https://www.example.com")
        String websiteUrl,

        @Valid
        SocialMedia socialMedia
) {}