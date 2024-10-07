package com.petadoption.center.exception.not_found;

public class ColorNotFoundException extends ModelNotFoundException {
    public ColorNotFoundException(String message) {
        super(message);
    }
}