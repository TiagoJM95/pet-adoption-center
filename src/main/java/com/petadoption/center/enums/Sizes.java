package com.petadoption.center.enums;

import com.petadoption.center.exception.pet.InvalidDescriptionException;
import lombok.Getter;

import static com.petadoption.center.util.Messages.INVALID_SIZE;

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

    public static Sizes getSizeByDescription(String description) throws InvalidDescriptionException {
        for(Sizes size : values()) {
            if(size.getDescription().equalsIgnoreCase(description)) {
                return size;
            }
        }
        throw new InvalidDescriptionException(INVALID_SIZE + description);
    }
}
