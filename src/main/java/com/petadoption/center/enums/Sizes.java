package com.petadoption.center.enums;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum Sizes {
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large"),
    EXTRA_LARGE("Extra Large");

    final String description;

    Sizes (String description){
        this.description = description;
    }

    public static Optional<Sizes> getSizeByDescription(String description) {
        for(Sizes size : values()) {
            if(size.getDescription().equalsIgnoreCase(description)) {
                return Optional.of(size);
            }
        }
        return Optional.empty();
    }
}
