package com.petadoption.center.exception.interest;

import static com.petadoption.center.util.Messages.ALREADY_EXISTS;

public class InterestDuplicateException extends InterestException {
    public InterestDuplicateException(String message) {
        super(message + ALREADY_EXISTS);
    }
}
