package com.petadoption.center.exception.species;

public class SpeciesNotFoundException extends SpeciesException {
    public SpeciesNotFoundException(Long id) {
        super("Species with id: " + id + " not found");
    }
}
