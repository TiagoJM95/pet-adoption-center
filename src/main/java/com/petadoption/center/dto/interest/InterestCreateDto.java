package com.petadoption.center.dto.interest;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_UUID;

@Builder(toBuilder = true)
public record InterestCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @UUID(message = ONLY_UUID)
        String userId,

        @NotBlank(message = BLANK_FIELD)
        @UUID(message = ONLY_UUID)
        String petId,

        @NotBlank(message = BLANK_FIELD)
        @UUID(message = ONLY_UUID)
        String organizationId
) {}