package com.petadoption.center.enums;

import lombok.Getter;

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
}