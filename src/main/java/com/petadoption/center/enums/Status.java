package com.petadoption.center.enums;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("Pending"),
    REJECTED("Rejected"),
    ACCEPTED("Accepted"),
    FORM_REQUESTED("Form Requested"),
    FORM_FILLED("Form Filled");

    private final String description;

    Status(String description) {
        this.description = description;
    }
}