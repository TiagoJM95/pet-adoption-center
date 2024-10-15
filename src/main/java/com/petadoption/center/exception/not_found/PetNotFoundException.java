package com.petadoption.center.exception.not_found;

public class PetNotFoundException extends ModelNotFoundException {
    public PetNotFoundException(String message) {
        super(message);
    }
}