package com.petadoption.center.enums;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum Ages {
    BABY("Baby"),
    YOUNG("Young"),
    ADULT("Adult"),
    SENIOR("Senior"),
    UNKNOWN("Unknown");

    final String description;

    Ages(String description) {
        this.description = description;
    }

    public static Optional<Ages> getAgeByDescription(String description) {
        for(Ages age : values()) {
            if(age.getDescription().equalsIgnoreCase(description)) {
                return Optional.of(age);
            }
        }
        return Optional.empty();
    }
}
