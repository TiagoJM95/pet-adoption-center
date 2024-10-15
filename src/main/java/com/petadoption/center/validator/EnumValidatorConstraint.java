package com.petadoption.center.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.MessageFormat;

import static com.petadoption.center.util.Messages.REQUIRED_FIELD;
import static com.petadoption.center.util.Utils.formatStringForEnum;

public class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, String> {

    private Class<? extends Enum<?>> enumClass;
    private boolean allowNull;
    private String messageTemplate;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.allowNull = constraintAnnotation.allowNull();
        this.messageTemplate = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return allowNull || addNewConstraintViolation(REQUIRED_FIELD, context);

        String formattedValue = formatStringForEnum(value);
        Object[] enumValues = enumClass.getEnumConstants();
        for (Object enumValue : enumValues) {
            if (formattedValue.equals(enumValue.toString())) return true;
        }

        String formattedMessage = MessageFormat.format(messageTemplate, value);

        return addNewConstraintViolation(formattedMessage, context);
    }

    private boolean addNewConstraintViolation(String message, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
