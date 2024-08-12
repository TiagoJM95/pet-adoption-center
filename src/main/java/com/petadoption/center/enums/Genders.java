package com.petadoption.center.enums;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum Genders {
    MALE("Male"),
    FEMALE("Female");

    final String description;

    Genders (String description){
        this.description = description;
    }

    public static Optional<Genders> getGenderByDescription(String description) {
        for(Genders gender : values()) {
            if(gender.getDescription().equalsIgnoreCase(description)) {
                return Optional.of(gender);
            }
        }
        return Optional.empty();
    }
}
