package com.petadoption.center.controller;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.organization.OrgUpdateDto;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;
import com.petadoption.center.service.interfaces.OrganizationServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organization")
public class OrganizationController {

    @Autowired
    private OrganizationServiceI organizationServiceI;

    @GetMapping("/")
    public ResponseEntity<List<OrgGetDto>> getAll(@RequestParam (defaultValue = "0", required = false) int page,
                                                  @RequestParam (defaultValue = "5", required = false) int size,
                                                  @RequestParam (defaultValue = "id", required = false) String sortBy) {
        return new ResponseEntity<>(organizationServiceI.getAllOrganizations(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OrgGetDto> getById(@PathVariable("id") String id)
            throws OrganizationNotFoundException {
        return new ResponseEntity<>(organizationServiceI.getOrganizationById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<OrgGetDto> create(@Valid @RequestBody OrgCreateDto organization) {
        return new ResponseEntity<>(organizationServiceI.addNewOrganization(organization), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrgGetDto> update(@PathVariable ("id") String id, @Valid @RequestBody OrgUpdateDto organization)
            throws OrganizationNotFoundException {
        return new ResponseEntity<>(organizationServiceI.updateOrganization(id, organization), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable ("id") String id)
            throws OrganizationNotFoundException {
        return new ResponseEntity<>(organizationServiceI.deleteOrganization(id), HttpStatus.OK);
    }
}