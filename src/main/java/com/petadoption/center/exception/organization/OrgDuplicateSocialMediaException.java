package com.petadoption.center.exception.organization;

import static com.petadoption.center.util.Messages.ALREADY_EXISTS;

public class OrgDuplicateSocialMediaException extends OrgException {
    public OrgDuplicateSocialMediaException(String socialMediaName,String socialMedia) {
        super(socialMediaName + socialMedia + ALREADY_EXISTS);
    }
}
