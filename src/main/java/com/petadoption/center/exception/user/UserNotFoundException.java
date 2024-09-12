package com.petadoption.center.exception.user;

import static com.petadoption.center.util.Messages.NOT_FOUND;
import static com.petadoption.center.util.Messages.USER_WITH_ID;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
