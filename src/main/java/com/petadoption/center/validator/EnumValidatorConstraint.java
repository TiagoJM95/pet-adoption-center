package com.petadoption.center.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.MessageFormat;
import java.util.List;

import static com.petadoption.center.util.Messages.REQUIRED_FIELD;
import static com.petadoption.center.util.Utils.formatStringForEnum;

public class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, Object> {

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
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value == null) return allowNull || addNewConstraintViolation(REQUIRED_FIELD, context);

        if (value instanceof List<?> valueList) {
            for (Object item : valueList) {
                if (!(item instanceof String))
                    return addNewConstraintViolation("All objects inside the list must be strings", context);
                if (!isValidEnum((String) item, context)) return false;
            }
            return true;
        }

        return isValidEnum(value.toString(), context);
    }

    private boolean isValidEnum(String value, ConstraintValidatorContext context) {

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
