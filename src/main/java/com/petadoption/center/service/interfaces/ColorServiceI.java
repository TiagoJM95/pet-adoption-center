package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.not_found.ColorNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ColorServiceI {
    List<ColorGetDto> getAllColors(Pageable pageable);
    ColorGetDto getColorById(String id) throws ColorNotFoundException;
    ColorGetDto addNewColor(ColorCreateDto color);
    String deleteColor(String id) throws ColorNotFoundException;
}