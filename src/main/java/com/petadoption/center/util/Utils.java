package com.petadoption.center.util;

import java.util.function.Consumer;

public class Utils {

    public static <Prop> void updateFields(Prop newValue, Prop oldValue, Consumer<Prop> setterField){
        if (newValue != null && !newValue.equals(oldValue) && !newValue.toString().isEmpty()) {
            setterField.accept(newValue);
        }
    }

    public static String formatStringForEnum(String value) {
        return value.trim().replace(" ", "_").toUpperCase();
    }
}