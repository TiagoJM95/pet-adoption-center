package com.petadoption.center.exception.not_found;

public class BreedNotFoundException extends ModelNotFoundException {
    public BreedNotFoundException(String message) {
        super(message);
    }
}