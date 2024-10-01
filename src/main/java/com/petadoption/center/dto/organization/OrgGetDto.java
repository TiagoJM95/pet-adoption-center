package com.petadoption.center.dto.organization;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record OrgGetDto(
        @Schema(description = "Organization id", example = "12345678-1234-123456")
        String id,
        @Schema(description = "Organization name", example = "Animal Rescue")
        String name,
        @Schema(description = "Organization email", example = "email@email.com")
        String email,
        @Schema(description = "Organization tax identification number", example = "123456789")
        String nif,
        @Schema(description = "Organization phone number", example = "227628976")
        String phoneNumber,
        Address address,
        @Schema(description = "Organization website url", example = "https://www.example.com")
        String websiteUrl,
        SocialMedia socialMedia
) {}