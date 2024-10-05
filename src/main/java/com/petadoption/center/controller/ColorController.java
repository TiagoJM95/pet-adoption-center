package com.petadoption.center.controller;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.service.interfaces.ColorServiceI;
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
    private ColorServiceI colorServiceI;

    @GetMapping("/")
    public ResponseEntity<List<ColorGetDto>> getAll(@RequestParam (defaultValue = "0", required = false) int page,
                                                    @RequestParam (defaultValue = "5", required = false) int size,
                                                    @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(colorServiceI.getAllColors(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ColorGetDto> getById(@PathVariable("id") String id)
            throws ColorNotFoundException {
        return new ResponseEntity<>(colorServiceI.getColorById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ColorGetDto> create(@Valid @RequestBody ColorCreateDto dto) {
        return new ResponseEntity<>(colorServiceI.addNewColor(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable ("id") String id)
            throws ColorNotFoundException {
        return new ResponseEntity<>(colorServiceI.deleteColor(id), HttpStatus.OK);
    }
}