package com.petadoption.center.controller;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.organization.OrgUpdateDto;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.organization.OrganizationDuplicateException;
import com.petadoption.center.service.OrganizationService;
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
    private OrganizationService organizationService;

    @GetMapping("/")
    public ResponseEntity<List<OrgGetDto>> getAllOrganizations(@RequestParam (defaultValue = "0", required = false) int page,
                                                               @RequestParam (defaultValue = "5", required = false) int size,
                                                               @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(organizationService.getAllOrganizations(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OrgGetDto> getOrganizationById(@PathVariable("id") Long id) throws OrgNotFoundException {
        return new ResponseEntity<>(organizationService.getOrganizationById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<OrgGetDto> addNewOrganization(@Valid @RequestBody OrgCreateDto organization) throws OrganizationDuplicateException {
        return new ResponseEntity<>(organizationService.addNewOrganization(organization), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrgGetDto> updateOrganization(@PathVariable ("id") Long id,
                                                        @Valid @RequestBody OrgUpdateDto organization) throws OrgNotFoundException, OrganizationDuplicateException {
        return new ResponseEntity<>(organizationService.updateOrganization(id, organization), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrganization(@PathVariable ("id") Long id) throws OrgNotFoundException {
        return new ResponseEntity<>(organizationService.deleteOrganization(id), HttpStatus.OK);
    }
}
