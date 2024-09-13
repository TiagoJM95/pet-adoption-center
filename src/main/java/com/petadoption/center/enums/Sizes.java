package com.petadoption.center.enums;

import com.petadoption.center.exception.pet.PetDescriptionException;
import lombok.Getter;

import static com.petadoption.center.util.Messages.INVALID_SIZE;

@Getter
public enum Sizes {
    SMALL("Small", "0-11 kg"),
    MEDIUM("Medium", "12-27 kg"),
    LARGE("Large", "28-45 kg"),
    EXTRA_LARGE("Extra Large", "46+ kg");

    final String description;
    final String range;

    Sizes (String description, String range) {
        this.description = description;
        this.range = range;
    }

    public static Sizes getSizeByDescription(String description) throws PetDescriptionException {
        for(Sizes size : values()) {
            if(size.getDescription().equalsIgnoreCase(description)) {
                return size;
            }
        }
        throw new PetDescriptionException(INVALID_SIZE + description);
    }
}