package com.petadoption.center.dto.pet;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.embeddable.Attributes;
import com.petadoption.center.validator.EnumValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Regex.WEBSITE_REGEX;

@Builder
public record PetUpdateDto(

        @EnumValidator(enumClass = Sizes.class, message = SIZE_INVALID, allowNull = true)
        String size,

        @EnumValidator(enumClass = Ages.class, message = AGE_INVALID, allowNull = true)
        String age,

        String description,

        @Pattern(regexp = WEBSITE_REGEX, message = WEBSITE_URL)
        @Size(max = 100, message = CHARACTERS_LIMIT)
        String imageUrl,

        boolean isAdopted,

        @Valid
        Attributes attributes,

        @UUID(allowEmpty = true, message = ONLY_UUID)
        String organizationId
) {}