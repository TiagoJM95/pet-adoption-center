package com.petadoption.center.exception.organization;

import static com.petadoption.center.util.Messages.ALREADY_EXISTS;
import static com.petadoption.center.util.Messages.WEBSITE;

public class OrgDuplicateWebsiteException extends OrgException {
    public OrgDuplicateWebsiteException(String websiteUrl) {
        super(WEBSITE + websiteUrl + ALREADY_EXISTS);
    }
}
