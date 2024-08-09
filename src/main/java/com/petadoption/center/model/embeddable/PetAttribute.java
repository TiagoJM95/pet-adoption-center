package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class PetAttribute {
    private Boolean sterilized;
    private Boolean vaccinated;
    private Boolean chipped;
    private Boolean specialNeeds;
    private Boolean houseTrained;
    private Boolean goodWithKids;
    private Boolean goodWithDogs;
    private Boolean goodWithCats;
}
