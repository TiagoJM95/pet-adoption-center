package com.petadoption.center.enums;

import com.petadoption.center.exception.pet.PetDescriptionException;
import lombok.Getter;

import static com.petadoption.center.util.Messages.INVALID_AGE;

@Getter
public enum Ages {
    BABY("Baby", "0-6 months"),
    YOUNG("Young", "6 months - 2 years"),
    ADULT("Adult", "2-8 years"),
    SENIOR("Senior", "8+ years"),
    UNKNOWN("Unknown", "Unknown");

    final String description;
    final String range;

    Ages(String description, String range) {
        this.description = description;
        this.range = range;
    }

    public static Ages getAgeByDescription(String description) throws PetDescriptionException {
        for(Ages age : values()) {
            if(age.getDescription().equalsIgnoreCase(description)) {
                return age;
            }
        }
        throw new PetDescriptionException(INVALID_AGE + description);
    }
}