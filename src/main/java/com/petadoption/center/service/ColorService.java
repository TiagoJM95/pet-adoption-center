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

import static com.petadoption.center.util.Messages.COLOR_DELETE_MESSAGE;
import static com.petadoption.center.util.Messages.COLOR_NOT_FOUND;
import static java.lang.String.format;

@Service
public class ColorService implements ColorServiceI {

    private final ColorRepository colorRepository;

    @Autowired
    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public List<ColorGetDto> getAll(Pageable pageable) {
        return colorRepository.findAll(pageable).stream().map(ColorConverter::toDto).toList();
    }

    @Override
    public ColorGetDto getById(String id) {
        return ColorConverter.toDto(findById(id));
    }

    @Override
    public ColorGetDto create(ColorCreateDto dto) {
        return ColorConverter.toDto(colorRepository.save(ColorConverter.toModel(dto)));
    }

    @Override
    public String delete(String id) {
        findById(id);
        colorRepository.deleteById(id);
        return format(COLOR_DELETE_MESSAGE, id);
    }

    private Color findById(String id) {
        return colorRepository.findById(id).orElseThrow(
                () -> new ColorNotFoundException(format(COLOR_NOT_FOUND, id)));
    }
}