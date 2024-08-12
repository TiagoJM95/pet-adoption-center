package com.petadoption.center.service;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;

import java.util.List;

public interface ColorService {

    List<ColorGetDto> getAllColors();

    ColorGetDto getColorById(Long id);

    ColorGetDto addNewColor(ColorCreateDto color);
}
