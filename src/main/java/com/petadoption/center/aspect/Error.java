package com.petadoption.center.aspect;

import lombok.Builder;

import java.util.Date;

@Builder
public record Error(
        Date timestamp,
        String message,
        String constraint,
        String method,
        String path
) {}