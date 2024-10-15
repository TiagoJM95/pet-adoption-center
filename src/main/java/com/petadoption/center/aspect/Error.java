package com.petadoption.center.aspect;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.Date;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record Error(
        Date timestamp,
        int status,
        String error,
        String method,
        String path,
        String constraint,
        String message,
        String hint,
        Map<String, String> validationIssue
) {}