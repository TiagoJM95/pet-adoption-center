package com.petadoption.center.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Utils {

    public static <Prop> void updateFields(Prop newValue, Prop oldValue, Consumer<Prop> setterField){
        if (newValue != null && !newValue.equals(oldValue) && !newValue.toString().isEmpty()) {
            setterField.accept(newValue);
        }
    }

    /*
    public static <Model, Prop, E extends Exception> void validateFields(Prop newValue, Optional<Prop> oldValue,
                                                                  Supplier<Optional<Model>> findMethod, Supplier<E> exceptionSupplier) throws E {

        if (oldValue.isEmpty() || !newValue.equals(oldValue.get())) {
            if (findMethod.get().isPresent()) {
                throw exceptionSupplier.get();
            }
        }
    }*/
}
