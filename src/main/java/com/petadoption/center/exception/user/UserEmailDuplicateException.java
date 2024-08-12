package com.petadoption.center.exception.user;

public class UserEmailDuplicateException extends UserException{
    public UserEmailDuplicateException(String email) {
        super("User with email: " + email + " already exists");
    }
}
