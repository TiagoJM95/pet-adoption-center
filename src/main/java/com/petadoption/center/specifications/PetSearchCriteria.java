package com.petadoption.center.specifications;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.embeddable.Attributes;
import com.petadoption.center.validator.EnumValidator;
import com.petadoption.center.validator.ListValidator;
import jakarta.validation.Valid;
import lombok.Builder;

import java.util.List;

import static com.petadoption.center.util.Messages.*;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PetSearchCriteria (

        @ListValidator(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        List<String> speciesNames,

        @ListValidator(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        List<String> breedNames,

        @ListValidator(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        List<String> colorNames,

        @EnumValidator(enumClass = Genders.class, allowNull = true, message = GENDER_INVALID)
        String gender,

        @EnumValidator(enumClass = Coats.class, allowNull = true, message = COAT_INVALID)
        List<String> coats,

        @EnumValidator(enumClass = Sizes.class, allowNull = true, message = SIZE_INVALID)
        List<String> sizes,

        @EnumValidator(enumClass = Ages.class, allowNull = true, message = AGE_INVALID)
        List<String> ages,

        Boolean isAdopted,
        Boolean isPureBreed,

        @Valid
        Attributes attributes,

        @ListValidator(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        List<String> states,

        @ListValidator(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        List<String> cities
) {}