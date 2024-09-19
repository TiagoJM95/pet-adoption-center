package com.petadoption.center.enums;

import com.petadoption.center.exception.InvalidStatusException;
import lombok.Getter;

import static com.petadoption.center.util.Messages.INVALID_STATUS;

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

    public static Status getStatusByDescription(String description) throws InvalidStatusException {
        for(Status status : values()) {
            if(status.getDescription().equalsIgnoreCase(description)) {
                return status;
            }
        }
        throw new InvalidStatusException(INVALID_STATUS + description);
    }
}