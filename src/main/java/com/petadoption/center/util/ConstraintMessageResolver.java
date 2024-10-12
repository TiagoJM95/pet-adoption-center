package com.petadoption.center.util;

import com.petadoption.center.config.ConstraintProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstraintMessageResolver {

    private final ConstraintProperties constraintProperties;

    @Autowired
    public ConstraintMessageResolver(ConstraintProperties constraintProperties) {
        this.constraintProperties = constraintProperties;
    }

    public String getMessage(String constraint) {
        return constraintProperties.getMessages().getOrDefault(constraint, "Duplicate entry detected.");
    }

    public String getHint(String constraint) {
        return constraintProperties.getHints().get(constraint);
    }
}