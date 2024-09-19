package com.petadoption.center.exception.interest;

import static com.petadoption.center.util.Messages.INTEREST_WITH_ID;
import static com.petadoption.center.util.Messages.NOT_FOUND;

public class InterestNotFoundException extends InterestException {
    public InterestNotFoundException(String message) {
        super(INTEREST_WITH_ID + message + NOT_FOUND);
    }
}
