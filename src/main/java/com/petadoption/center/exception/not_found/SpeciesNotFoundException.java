package com.petadoption.center.exception.not_found;

public class SpeciesNotFoundException extends ModelNotFoundException {
    public SpeciesNotFoundException(String message) {
        super(message);
    }
}