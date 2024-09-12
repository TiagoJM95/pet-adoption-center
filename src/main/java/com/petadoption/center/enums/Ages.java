package com.petadoption.center.enums;

import com.petadoption.center.exception.pet.InvalidDescriptionException;
import lombok.Getter;

import static com.petadoption.center.util.Messages.INVALID_AGE;

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

    public static Ages getAgeByDescription(String description) throws InvalidDescriptionException {
        for(Ages age : values()) {
            if(age.getDescription().equalsIgnoreCase(description)) {
                return age;
            }
        }
        throw new InvalidDescriptionException(INVALID_AGE + description);
    }
}
