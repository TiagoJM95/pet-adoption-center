package com.petadoption.center.exception.user;

public class UserPhoneNumberDuplicateException extends UserException{
    public UserPhoneNumberDuplicateException(Integer phoneNumber) {
        super("User with phone number: " + phoneNumber + " already exists");
    }
}
