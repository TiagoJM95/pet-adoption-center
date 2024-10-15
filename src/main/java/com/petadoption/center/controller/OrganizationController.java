package com.petadoption.center.controller;

import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import com.petadoption.center.service.interfaces.OrganizationServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organization")
public class OrganizationController {

    private final OrganizationServiceI organizationServiceI;

    @Autowired
    public OrganizationController(OrganizationServiceI organizationServiceI) {
        this.organizationServiceI = organizationServiceI;
    }

    @GetMapping("/public/")
    public ResponseEntity<List<OrganizationGetDto>> getAll(@PageableDefault(sort = "createdAt") Pageable pageable) {
        return new ResponseEntity<>(organizationServiceI.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/public/id/{id}")
    public ResponseEntity<OrganizationGetDto> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(organizationServiceI.getById(id), HttpStatus.OK);
    }

    @PostMapping("/public/")
    public ResponseEntity<OrganizationGetDto> create(@Valid @RequestBody OrganizationCreateDto dto) {
        return new ResponseEntity<>(organizationServiceI.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/public/update/{id}")
    public ResponseEntity<OrganizationGetDto> update(@PathVariable ("id") String id, @Valid @RequestBody OrganizationUpdateDto dto) {
        return new ResponseEntity<>(organizationServiceI.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/public/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable ("id") String id) {
        return new ResponseEntity<>(organizationServiceI.delete(id), HttpStatus.OK);
    }
}