package com.petadoption.center.validator;

import com.petadoption.center.util.Messages;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.MessageFormat;

import static com.petadoption.center.util.Messages.REQUIRED_FIELD;

public class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, String> {

    private Class<? extends Enum<?>> enumClass;
    private String messageTemplate;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.messageTemplate = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(REQUIRED_FIELD)
                    .addConstraintViolation();
            return false;
        }

        String editedValue = value.trim().replace(" ", "_").toUpperCase();
        Object[] enumValues = enumClass.getEnumConstants();
        for (Object enumValue : enumValues) {
            if (editedValue.equals(enumValue.toString())) return true;
        }

        context.disableDefaultConstraintViolation();
        String formattedMessage = MessageFormat.format(messageTemplate, value);
        context.buildConstraintViolationWithTemplate(formattedMessage)
                .addConstraintViolation();

        return false;
    }
}
