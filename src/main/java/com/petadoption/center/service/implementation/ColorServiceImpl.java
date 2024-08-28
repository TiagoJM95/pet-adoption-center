package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.ColorConverter;
import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.color.ColorDuplicateException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.model.Color;
import com.petadoption.center.repository.ColorRepository;
import com.petadoption.center.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.converter.ColorConverter.fromModelToColorGetDto;

@Service
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Autowired
    public ColorServiceImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public List<ColorGetDto> getAllColors() {
        return colorRepository.findAll().stream().map(ColorConverter::fromModelToColorGetDto).toList();
    }

    @Override
    public ColorGetDto getColorById(Long id) throws ColorNotFoundException {
        return fromModelToColorGetDto(findColorById(id));
    }

    @Override
    public ColorGetDto addNewColor(ColorCreateDto color) throws ColorDuplicateException {
        checkIfColorExistsByName(color.name());
        return fromModelToColorGetDto(colorRepository.save(ColorConverter.fromColorCreateDtoToModel(color)));
    }

    Color findColorById(Long id) throws ColorNotFoundException {
        return colorRepository.findById(id).orElseThrow(() -> new ColorNotFoundException("id"));
    }

    private void checkIfColorExistsByName(String name) throws ColorDuplicateException {
        if (colorRepository.findByName(name).isPresent()) {
            throw new ColorDuplicateException(name);
        }
    }
}
