package com.petadoption.center.controller;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.color.ColorDuplicateException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/color")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping("/")
    public ResponseEntity<List<ColorGetDto>> getAllColors(){
        return new ResponseEntity<>(colorService.getAllColors(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ColorGetDto> getColorById(@PathVariable("id") Long id) throws ColorNotFoundException {
        return new ResponseEntity<>(colorService.getColorById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ColorGetDto> addNewColor(@RequestBody ColorCreateDto color) throws ColorDuplicateException {
        return new ResponseEntity<>(colorService.addNewColor(color), HttpStatus.CREATED);
    }
}
