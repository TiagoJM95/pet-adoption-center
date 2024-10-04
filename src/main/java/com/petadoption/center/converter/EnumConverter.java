package com.petadoption.center.converter;

import static com.petadoption.center.util.Utils.formatStringForEnum;

public class EnumConverter {

    public static <E extends Enum<E>> E convertStringToEnum(String value, Class<E> enumClass) {
        return Enum.valueOf(enumClass, formatStringForEnum(value));
    }
}
