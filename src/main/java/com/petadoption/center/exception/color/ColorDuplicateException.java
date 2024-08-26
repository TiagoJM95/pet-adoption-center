package com.petadoption.center.exception.color;

public class ColorDuplicateException extends ColorException {
    public ColorDuplicateException(String name) {
        super("Color with name " + name + " already exists");
    }
}
