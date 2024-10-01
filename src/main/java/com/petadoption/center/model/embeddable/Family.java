package com.petadoption.center.model.embeddable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_NUMBERS;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Family {

    @Min(value = 0, message = ONLY_NUMBERS)
    @Schema(description = "Number of family members", example = "2")
    private Integer familyCount;

    @NotBlank(message = BLANK_FIELD)
    @Schema(description = "Is the family likes pets?", example = "true")
    private Boolean likesPets;

    @NotBlank(message = BLANK_FIELD)
    @Schema(description = "Does the family have other pets?", example = "true")
    private Boolean hasOtherPets;

    @Min(value = 0, message = ONLY_NUMBERS)
    @Schema(description = "How many pets does the family have", example = "2")
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
