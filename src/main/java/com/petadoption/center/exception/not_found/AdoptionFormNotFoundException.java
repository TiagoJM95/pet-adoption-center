package com.petadoption.center.exception.not_found;

public class AdoptionFormNotFoundException extends ModelNotFoundException {
    public AdoptionFormNotFoundException(String message) {
        super(message);
    }
}