package com.petadoption.center.dto.email;

public record EmailDto(
        String recipient,
        String subject,
        String body
) {
}
