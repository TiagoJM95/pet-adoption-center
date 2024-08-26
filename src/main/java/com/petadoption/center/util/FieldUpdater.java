package com.petadoption.center.util;

import com.petadoption.center.exception.user.UserEmailDuplicateException;
import com.petadoption.center.exception.user.UserPhoneNumberDuplicateException;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FieldUpdater {

    public static <T> void updateIfChanged(Supplier<T> newValueSupplier, Supplier <T> oldValueSupplier, Consumer<T> setterField ){
        T newValue = newValueSupplier.get();
        T oldValue = oldValueSupplier.get();

        if (newValue != null && !newValue.equals(oldValue) && !newValue.toString().isEmpty()) {
            setterField.accept(newValue);
        }
    }

    public static <T> void updateIfChangedCheckDuplicates(Supplier<T> newValueSupplier, Supplier <T> oldValueSupplier, Consumer<T> setterField, Runnable checkForDuplicates) throws UserEmailDuplicateException, UserPhoneNumberDuplicateException {
        T newValue = newValueSupplier.get();
        T oldValue = oldValueSupplier.get();

        if (newValue != null && !newValue.equals(oldValue) && !newValue.toString().isEmpty()) {
            checkForDuplicates.run();
            setterField.accept(newValue);
        }

    }



}
