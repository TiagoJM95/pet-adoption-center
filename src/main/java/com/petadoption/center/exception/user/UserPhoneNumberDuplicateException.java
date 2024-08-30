package com.petadoption.center.exception.user;

import static com.petadoption.center.util.Messages.ALREADY_EXISTS;
import static com.petadoption.center.util.Messages.USER_WITH_PHONE_NUMBER;

public class UserPhoneNumberDuplicateException extends UserException{
    public UserPhoneNumberDuplicateException(Integer phoneNumber) {
        super(USER_WITH_PHONE_NUMBER + phoneNumber + ALREADY_EXISTS);
    }
}
