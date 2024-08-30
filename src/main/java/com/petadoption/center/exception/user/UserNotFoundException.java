package com.petadoption.center.exception.user;

import static com.petadoption.center.util.Messages.DOESNT_EXIST;
import static com.petadoption.center.util.Messages.USER_WITH_ID;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(Long id) {
        super(USER_WITH_ID + id + DOESNT_EXIST);
    }
}
