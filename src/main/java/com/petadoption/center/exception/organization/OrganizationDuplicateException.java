package com.petadoption.center.exception.organization;

import static com.petadoption.center.util.Messages.ALREADY_EXISTS;

public class OrganizationDuplicateException extends OrgException {
    public OrganizationDuplicateException(String message) {
        super(message + ALREADY_EXISTS);
    }
}
