package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.repository.ColorRepository;
import com.petadoption.center.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<ColorGetDto> getAllColors() {
        return List.of();
    }

    @Override
    public ColorGetDto getColorById(Long id) {
        return null;
    }

    @Override
    public ColorGetDto addNewColor(ColorCreateDto color) {
        return null;
    }
}
