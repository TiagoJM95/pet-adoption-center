package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String street;
    private String city;
    private String state;
    private String postalCode;
}
