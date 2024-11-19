package com.petadoption.center.enums;

import lombok.Getter;

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
}