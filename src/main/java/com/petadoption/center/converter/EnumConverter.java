package com.petadoption.center.converter;

import java.util.ArrayList;
import java.util.List;

import static com.petadoption.center.util.Utils.formatStringForEnum;

public class EnumConverter {

    public static <E extends Enum<E>> E convertStringToEnum(String value, Class<E> enumClass) {
        return Enum.valueOf(enumClass, formatStringForEnum(value));
    }

    public static <E extends Enum<E>> List<E> convertListOfStringToEnum(List<String> value, Class<E> enumClass) {
        List<E> list = new ArrayList<>();
        for (String s : value){
            list.add(Enum.valueOf(enumClass, formatStringForEnum(s)));
        }
        return list;
    }
}