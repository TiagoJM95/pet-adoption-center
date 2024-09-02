package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Attributes {
    private Boolean sterilized;
    private Boolean vaccinated;
    private Boolean chipped;
    private Boolean specialNeeds;
    private Boolean houseTrained;
    private Boolean goodWithKids;
    private Boolean goodWithDogs;
    private Boolean goodWithCats;
}
