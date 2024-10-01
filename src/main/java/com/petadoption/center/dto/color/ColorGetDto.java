package com.petadoption.center.dto.color;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ColorGetDto(
        @Schema(description = "Color id", example = "12345678-1234-123456")
        String id,
        @Schema(description = "Color name", example = "Black")
        String name
) {}