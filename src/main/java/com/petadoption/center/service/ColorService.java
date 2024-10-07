package com.petadoption.center.service;

import com.petadoption.center.converter.ColorConverter;
import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.not_found.ColorNotFoundException;
import com.petadoption.center.model.Color;
import com.petadoption.center.repository.ColorRepository;
import com.petadoption.center.service.interfaces.ColorServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.util.Messages.*;

@Service
public class ColorService implements ColorServiceI {

    private final ColorRepository colorRepository;

    @Autowired
    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public List<ColorGetDto> getAllColors(Pageable pageable) {
        return colorRepository.findAll(pageable).stream().map(ColorConverter::toDto).toList();
    }

    @Override
    public ColorGetDto getColorById(String id) throws ColorNotFoundException {
        return ColorConverter.toDto(findColorById(id));
    }

    @Override
    public ColorGetDto addNewColor(ColorCreateDto dto) {
        return ColorConverter.toDto(colorRepository.save(ColorConverter.toModel(dto)));
    }

    @Override
    public String deleteColor(String id) throws ColorNotFoundException {
        findColorById(id);
        colorRepository.deleteById(id);
        return COLOR_WITH_ID + id + DELETE_SUCCESS;
    }

    private Color findColorById(String id) throws ColorNotFoundException {
        return colorRepository.findById(id).orElseThrow(
                () -> new ColorNotFoundException(COLOR_WITH_ID + id + NOT_FOUND));
    }
}