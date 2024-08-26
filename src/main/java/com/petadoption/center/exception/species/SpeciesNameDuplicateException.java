package com.petadoption.center.exception.species;

public class SpeciesNameDuplicateException extends SpeciesException {
    public SpeciesNameDuplicateException(String name) {
        super("Species with name " + name + " already exists");
    }
}
