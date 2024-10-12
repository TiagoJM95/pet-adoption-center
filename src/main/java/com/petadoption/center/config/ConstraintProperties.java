package com.petadoption.center.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "exception")
@Getter
@Setter
public class ConstraintProperties {
    private Map<String, String> messages;
    private Map<String, String> hints;
}