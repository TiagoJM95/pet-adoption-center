package com.petadoption.center.exception.not_found;

public class InvalidStatusChangeException extends RuntimeException {
    public InvalidStatusChangeException(String message) {
        super(message);
    }
}