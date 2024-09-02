package com.petadoption.center.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FieldUpdater {

    public static <T> void updateIfChanged(Supplier<T> newValueSupplier, Supplier <T> oldValueSupplier, Consumer<T> setterField){
        T newValue = newValueSupplier.get();
        T oldValue = oldValueSupplier.get();

        if (newValue != null && !newValue.equals(oldValue) && !newValue.toString().isEmpty()) {
            setterField.accept(newValue);
        }
    }
}
