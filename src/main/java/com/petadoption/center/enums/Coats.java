package com.petadoption.center.enums;

import lombok.Getter;
import java.util.Optional;

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

    public static Optional<Coats> getCoatByDescription(String description) {
        for(Coats coat : values()) {
            if(coat.getDescription().equalsIgnoreCase(description)) {
                return Optional.of(coat);
            }
        }
        return Optional.empty();
    }
}
