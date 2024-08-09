package com.petadoption.center.controller;


import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationPatchDto;
import com.petadoption.center.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/")
    public ResponseEntity<List<OrganizationGetDto>> getAllOrganizations(){
        return new ResponseEntity<>(organizationService.getAllOrganizations(), HttpStatus.OK);
    }

    @GetMapping("/id/{organizationId}")
    public ResponseEntity<OrganizationGetDto> getOrganizationById(@PathVariable("organizationId") Long organizationId){
        return new ResponseEntity<>(organizationService.getOrganizationById(organizationId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<OrganizationGetDto> addNewOrganization(@RequestBody OrganizationCreateDto organizationCreateDto){
        return new ResponseEntity<>(organizationService.addNewOrganization(organizationCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{organizationId}")
    public ResponseEntity<OrganizationGetDto> updateOrganization(@PathVariable ("organizationId") Long organizationId, @RequestBody OrganizationPatchDto organizationPatchDto){
        return new ResponseEntity<>(organizationService.updateOrganization(organizationId, organizationPatchDto), HttpStatus.OK);
    }

}
