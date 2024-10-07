package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ColorServiceI {
    List<ColorGetDto> getAll(Pageable pageable);
    ColorGetDto getById(String id);
    ColorGetDto create(ColorCreateDto dto);
    String delete(String id);
}