package com.petadoption.center.enums;

import com.petadoption.center.exception.pet.InvalidDescriptionException;
import lombok.Getter;

import static com.petadoption.center.util.Messages.INVALID_GENDER;

@Getter
public enum Genders {
    MALE("Male"),
    FEMALE("Female");

    final String description;

    Genders (String description){
        this.description = description;
    }

    public static Genders getGenderByDescription(String description) throws InvalidDescriptionException {
        for(Genders gender : values()) {
            if(gender.getDescription().equalsIgnoreCase(description)) {
                return gender;
            }
        }
        throw new InvalidDescriptionException(INVALID_GENDER + description);
    }
}
