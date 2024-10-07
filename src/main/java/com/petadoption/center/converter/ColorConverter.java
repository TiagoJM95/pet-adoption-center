package com.petadoption.center.converter;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.model.Color;

public class ColorConverter {

    public static Color toModel(ColorCreateDto dto) {
        if (dto == null) return null;
        return Color.builder()
                .name(dto.name())
                .build();
    }

    public static Color toModel(ColorGetDto dto) {
        if (dto == null) return null;
        return Color.builder()
                .id(dto.id())
                .name(dto.name())
                .createdAt(dto.createdAt())
                .build();
    }

    public static ColorGetDto toDto(Color color) {
        if (color == null) return null;
        return ColorGetDto.builder()
                .id(color.getId())
                .name(color.getName())
                .createdAt(color.getCreatedAt())
                .build();
    }
}