package com.petadoption.center.exception.organization;

import static com.petadoption.center.util.Messages.ALREADY_EXISTS;
import static com.petadoption.center.util.Messages.ORG_WITH_PHONE_NUMBER;

public class OrgDuplicatePhoneNumberException extends OrgException {
    public OrgDuplicatePhoneNumberException(String phoneNumber) {
        super(ORG_WITH_PHONE_NUMBER + phoneNumber + ALREADY_EXISTS);
    }
}
