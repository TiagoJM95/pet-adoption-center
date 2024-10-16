package com.petadoption.center.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.regex.Pattern;

public class ListValidatorConstraint implements ConstraintValidator<ListValidator, List<String>> {

    private Pattern pattern;

    @Override
    public void initialize(ListValidator constraintAnnotation) {
        pattern = Pattern.compile(constraintAnnotation.regexp());
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.stream().allMatch(s -> pattern.matcher(s).matches());
    }
}