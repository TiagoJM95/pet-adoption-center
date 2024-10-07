package com.petadoption.center.controller;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.not_found.ColorNotFoundException;
import com.petadoption.center.service.interfaces.ColorServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<List<ColorGetDto>> getAll(@PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable){
        return new ResponseEntity<>(colorServiceI.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ColorGetDto> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(colorServiceI.getById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ColorGetDto> create(@Valid @RequestBody ColorCreateDto dto) {
        return new ResponseEntity<>(colorServiceI.create(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable ("id") String id) {
        return new ResponseEntity<>(colorServiceI.delete(id), HttpStatus.OK);
    }
}