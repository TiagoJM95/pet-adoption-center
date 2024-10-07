package com.petadoption.center.exception.not_found;

public class UserNotFoundException extends ModelNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}