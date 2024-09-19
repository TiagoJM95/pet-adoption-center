package com.petadoption.center.exception.interest;

import static com.petadoption.center.util.Messages.*;

public class InterestNotFoundException extends InterestException {
    public InterestNotFoundException(String message) {
        super(INTEREST_WITH_ID + message + NOT_FOUND);
    }
}
