package com.petadoption.center.exception.organization;

import static com.petadoption.center.util.Messages.ALREADY_EXISTS;
import static com.petadoption.center.util.Messages.ORG_WITH_EMAIL;

public class OrgDuplicateEmailException extends Exception {
    public OrgDuplicateEmailException(String message) {
        super(ORG_WITH_EMAIL + message + ALREADY_EXISTS);
    }
}
