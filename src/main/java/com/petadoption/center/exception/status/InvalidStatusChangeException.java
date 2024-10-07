package com.petadoption.center.exception.status;

public class InvalidStatusChangeException extends StatusException {
    public InvalidStatusChangeException(String message) {
        super(message);
    }
}