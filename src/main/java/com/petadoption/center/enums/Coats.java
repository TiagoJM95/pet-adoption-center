package com.petadoption.center.enums;

import com.petadoption.center.exception.pet.InvalidDescriptionException;
import lombok.Getter;

import static com.petadoption.center.util.Messages.INVALID_COAT;

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

    public static Coats getCoatByDescription(String description) throws InvalidDescriptionException {
        for(Coats coat : values()) {
            if(coat.getDescription().equalsIgnoreCase(description)) {
                return coat;
            }
        }
        throw new InvalidDescriptionException(INVALID_COAT + description);
    }
}
