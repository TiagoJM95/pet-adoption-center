package com.petadoption.center.exception.organization;

import static com.petadoption.center.util.Messages.NOT_FOUND;
import static com.petadoption.center.util.Messages.ORG_WITH_ID;

public class OrgNotFoundException extends OrgException {
    public OrgNotFoundException(Long id) {
        super(ORG_WITH_ID + id + NOT_FOUND);
    }
}
