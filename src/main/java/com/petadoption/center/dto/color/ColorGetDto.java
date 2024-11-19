package com.petadoption.center.dto.color;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record ColorGetDto(
        String id,
        String name,
        LocalDateTime createdAt
)implements Serializable {}