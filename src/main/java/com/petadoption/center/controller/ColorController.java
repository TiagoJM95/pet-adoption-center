package com.petadoption.center.controller;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.color.ColorDuplicateException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.service.ColorService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<ColorGetDto>> getAllColors(@RequestParam (defaultValue = "0", required = false) int page,
                                                          @RequestParam (defaultValue = "5", required = false) int size,
                                                          @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(colorService.getAllColors(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ColorGetDto> getColorById(@PathVariable("id") Long id) throws ColorNotFoundException {
        return new ResponseEntity<>(colorService.getColorById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ColorGetDto> addNewColor(@Valid @RequestBody ColorCreateDto color) throws ColorDuplicateException {
        return new ResponseEntity<>(colorService.addNewColor(color), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteColor(@PathVariable ("id") Long id) throws ColorNotFoundException {
        return new ResponseEntity<>(colorService.deleteColor(id), HttpStatus.OK);
    }
}
