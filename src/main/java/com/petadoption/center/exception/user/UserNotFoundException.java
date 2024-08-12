package com.petadoption.center.exception.user;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(Long id) {
        super("user with " + id + " doesn't exists");
    }
}
