package com.petadoption.center.exception.breed;

public class BreedNotFoundException extends BreedException {
    public BreedNotFoundException(Long id) {
        super("Breed with id " + id + " not found");
    }
}
