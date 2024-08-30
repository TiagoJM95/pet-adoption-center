package com.petadoption.center.exception.organization;

import static com.petadoption.center.util.Messages.*;

public class OrgDuplicateAddressException extends OrgException {
    public OrgDuplicateAddressException(String street, String postalCode) {
        super(ADDRESS_STREET + street + ADDRESS_POSTAL_CODE + postalCode + ALREADY_EXISTS);
    }
}
