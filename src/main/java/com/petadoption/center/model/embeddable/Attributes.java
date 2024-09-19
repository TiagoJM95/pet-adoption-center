package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Attributes {
    private boolean sterilized;
    private boolean vaccinated;
    private boolean chipped;
    private boolean specialNeeds;
    private boolean houseTrained;
    private boolean goodWithKids;
    private boolean goodWithDogs;
    private boolean goodWithCats;
}