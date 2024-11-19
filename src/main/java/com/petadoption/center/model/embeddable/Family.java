package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_NUMBERS;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Family implements Serializable {

    @Min(value = 0, message = ONLY_NUMBERS)
    private Integer familyCount;

    @NotBlank(message = BLANK_FIELD)
    private Boolean likesPets;

    @NotBlank(message = BLANK_FIELD)
    private Boolean hasOtherPets;

    @Min(value = 0, message = ONLY_NUMBERS)
    private Integer numberOfPets;

    private List<String> familyPets; //talvez fazer um enum com muitas espécies

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Family family = (Family) o;
        return Objects.equals(familyCount, family.familyCount) && Objects.equals(likesPets, family.likesPets) && Objects.equals(hasOtherPets, family.hasOtherPets) && Objects.equals(numberOfPets, family.numberOfPets) && Objects.equals(familyPets, family.familyPets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(familyCount, likesPets, hasOtherPets, numberOfPets, familyPets);
    }
}
