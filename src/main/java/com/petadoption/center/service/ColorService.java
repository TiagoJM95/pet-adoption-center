package com.petadoption.center.service;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.color.ColorDuplicateException;
import com.petadoption.center.exception.color.ColorNotFoundException;

import java.util.List;

public interface ColorService {

    List<ColorGetDto> getAllColors(int page, int size, String sortBy);
    ColorGetDto getColorById(Long id) throws ColorNotFoundException;
    ColorGetDto addNewColor(ColorCreateDto color) throws ColorDuplicateException;
    String deleteColor(Long id) throws ColorNotFoundException;
}
