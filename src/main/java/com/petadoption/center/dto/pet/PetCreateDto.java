package com.petadoption.center.dto.pet;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.embeddable.Attributes;
import com.petadoption.center.validator.EnumValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Regex.USER_NAME_REGEX;
import static com.petadoption.center.util.Regex.WEBSITE_REGEX;

@Builder(toBuilder = true)
public record PetCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = USER_NAME_REGEX, message = ONLY_LETTERS)
        String name,

        @UUID(message = ONLY_UUID)
        String speciesId,

        @UUID(message = ONLY_UUID)
        String primaryBreedId,

        @UUID(allowEmpty = true, message = ONLY_UUID)
        String secondaryBreedId,

        @UUID(message = ONLY_UUID)
        String primaryColor,

        @UUID(allowEmpty = true, message = ONLY_UUID)
        String secondaryColor,

        @UUID(allowEmpty = true, message = ONLY_UUID)
        String tertiaryColor,

        @EnumValidator(enumClass = Genders.class, message = GENDER_INVALID)
        String gender,

        @EnumValidator(enumClass = Coats.class, message = COAT_INVALID)
        String coat,

        @EnumValidator(enumClass = Sizes.class, message = SIZE_INVALID)
        String size,

        @EnumValidator(enumClass = Ages.class, message = AGE_INVALID)
        String age,

        @NotBlank(message = BLANK_FIELD)
        String description,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = WEBSITE_REGEX, message = WEBSITE_URL)
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String imageUrl,

        Boolean isAdopted,

        @Valid
        Attributes attributes,

        @UUID(message = ONLY_UUID)
        String organizationId
) {}