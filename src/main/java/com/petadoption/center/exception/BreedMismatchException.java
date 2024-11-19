package com.petadoption.center.exception;

public class BreedMismatchException extends RuntimeException {
    public BreedMismatchException(String message) {
        super(message);
    }
}