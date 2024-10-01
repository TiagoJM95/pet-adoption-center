package com.petadoption.center.model.embeddable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Attributes {
    @Schema(description = "Is the animal sterilized?", example = "true")
    private boolean sterilized;

    @Schema(description = "Is the animal vaccinated?", example = "true")
    private boolean vaccinated;

    @Schema(description = "Is the animal chipped?", example = "true")
    private boolean chipped;

    @Schema(description = "Is the animal special needs?", example = "true")
    private boolean specialNeeds;

    @Schema(description = "Is the animal house trained?", example = "true")
    private boolean houseTrained;

    @Schema(description = "Is the animal good with kids?", example = "true")
    private boolean goodWithKids;

    @Schema(description = "Is the animal good with dogs?", example = "true")
    private boolean goodWithDogs;

    @Schema(description = "Is the animal good with cats?", example = "true")
    private boolean goodWithCats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attributes that = (Attributes) o;
        return sterilized == that.sterilized && vaccinated == that.vaccinated && chipped == that.chipped && specialNeeds == that.specialNeeds && houseTrained == that.houseTrained && goodWithKids == that.goodWithKids && goodWithDogs == that.goodWithDogs && goodWithCats == that.goodWithCats;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sterilized, vaccinated, chipped, specialNeeds, houseTrained, goodWithKids, goodWithDogs, goodWithCats);
    }
}