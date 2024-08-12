package com.petadoption.center.converter;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.model.Color;

public class ColorConverter {

    public static Color fromColorCreateDtoToModel(ColorCreateDto color) {
        return Color.builder()
                .name(color.name())
                .build();
    }

    public static ColorGetDto fromModelToColorGetDto(Color color) {
        return new ColorGetDto(
                color.getId(),
                color.getName());
    }
}
