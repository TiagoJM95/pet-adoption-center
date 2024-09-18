package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.color.ColorNotFoundException;

import java.util.List;

public interface ColorServiceI {
    List<ColorGetDto> getAllColors(int page, int size, String sortBy);
    ColorGetDto getColorById(String id) throws ColorNotFoundException;
    ColorGetDto addNewColor(ColorCreateDto color);
    String deleteColor(String id) throws ColorNotFoundException;
}