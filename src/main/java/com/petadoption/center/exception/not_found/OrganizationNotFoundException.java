package com.petadoption.center.exception.not_found;

public class OrganizationNotFoundException extends ModelNotFoundException {
    public OrganizationNotFoundException(String message) {
        super(message);
    }
}