package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static com.petadoption.center.util.Messages.BLANK_FIELD;
import static com.petadoption.center.util.Messages.ONLY_NUMBERS;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Family {

    @Min(value = 0, message = ONLY_NUMBERS)
    private Integer familyCount;

    @NotBlank(message = BLANK_FIELD)
    private Boolean likesPets;

    @NotBlank(message = BLANK_FIELD)
    private Boolean hasOtherPets;

    @Min(value = 0, message = ONLY_NUMBERS)
    private Integer numberOfPets;

    private List<String> familyPets; //talvez fazer um enum com muitas espécies
}
