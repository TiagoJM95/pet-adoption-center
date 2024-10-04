package com.petadoption.center.specifications;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Color;
import com.petadoption.center.model.Species;
import com.petadoption.center.validator.EnumValidator;
import lombok.Builder;

import static com.petadoption.center.util.Messages.*;

@Builder
public record PetSearchCriteria(
        String nameLike,
        Species species,
        Breed breed,
        Color color,

        @EnumValidator(enumClass = Genders.class, allowNull = true, message = GENDER_INVALID)
        String gender,

        @EnumValidator(enumClass = Coats.class, allowNull = true, message = COAT_INVALID)
        String coat,

        @EnumValidator(enumClass = Sizes.class, allowNull = true, message = SIZE_INVALID)
        String size,

        @EnumValidator(enumClass = Ages.class, allowNull = true, message = AGE_INVALID)
        String age,

        Boolean isAdopted,
        Boolean isSterilized,
        Boolean isVaccinated,
        Boolean isChipped,
        Boolean isSpecialNeeds,
        Boolean isHouseTrained,
        Boolean goodWithKids,
        Boolean goodWithDogs,
        Boolean goodWithCats,
        Boolean isPureBreed,
        String state,
        String city
) {}