package com.petadoption.center.enums;

import lombok.Getter;

@Getter
public enum Genders {
    MALE("Male"),
    FEMALE("Female");

    final String description;

    Genders (String description){
        this.description = description;
    }
}
