package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Family {
    private Integer familyCount;
    private Boolean likesPets;
    private Boolean hasOtherPets;
    private Integer numberOfPets;
    private List<String> familyPets; //talvez fazer um enum com muitas espécies
}
