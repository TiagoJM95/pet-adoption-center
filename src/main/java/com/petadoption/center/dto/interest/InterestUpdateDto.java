package com.petadoption.center.dto.interest;

import com.petadoption.center.enums.Status;
import com.petadoption.center.validator.EnumValidator;
import lombok.Builder;

import static com.petadoption.center.util.Messages.INVALID_STATUS;

@Builder
public record InterestUpdateDto(

        @EnumValidator(enumClass = Status.class, message = INVALID_STATUS)
        String status
) {}