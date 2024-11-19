package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Attributes implements Serializable {
    private boolean sterilized;
    private boolean vaccinated;
    private boolean chipped;
    private boolean specialNeeds;
    private boolean houseTrained;
    private boolean goodWithKids;
    private boolean goodWithDogs;
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