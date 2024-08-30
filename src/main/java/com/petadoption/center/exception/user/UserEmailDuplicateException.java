package com.petadoption.center.exception.user;

import static com.petadoption.center.util.Messages.ALREADY_EXISTS;
import static com.petadoption.center.util.Messages.USER_WITH_EMAIL;

public class UserEmailDuplicateException extends UserException {
    public UserEmailDuplicateException(String email) {
        super(USER_WITH_EMAIL + email + ALREADY_EXISTS);
    }
}
