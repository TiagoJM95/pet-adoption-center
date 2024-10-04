package com.petadoption.center.enums;

import lombok.Getter;

@Getter
public enum Coats {
    HAIRLESS("Hairless"),
    SHORT("Short"),
    MEDIUM("Medium"),
    LONG("Long");

    final String description;

    Coats(String description){
        this.description = description;
    }
}
