package com.petadoption.center.controller;

import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import com.petadoption.center.exception.organization.*;
import com.petadoption.center.service.OrganizationService;
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
    public ResponseEntity<List<OrganizationGetDto>> getAllOrganizations(){
        return new ResponseEntity<>(organizationService.getAllOrganizations(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OrganizationGetDto> getOrganizationById(@PathVariable("id") Long id) throws OrganizationNotFoundException {
        return new ResponseEntity<>(organizationService.getOrganizationById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<OrganizationGetDto> addNewOrganization(@RequestBody OrganizationCreateDto organization) throws OrganizationDuplicateSocialMediaException, OrganizationDuplicatePhoneNumberException, OrganizationDuplicateAddressException, OrganizationDuplicateWebsiteException, OrganizationDuplicateEmailException {
        return new ResponseEntity<>(organizationService.addNewOrganization(organization), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrganizationGetDto> updateOrganization(@PathVariable ("id") Long id,
                                                                 @RequestBody OrganizationUpdateDto organization) throws OrganizationDuplicateSocialMediaException, OrganizationNotFoundException, OrganizationDuplicatePhoneNumberException, OrganizationDuplicateAddressException, OrganizationDuplicateWebsiteException, OrganizationDuplicateEmailException {
        return new ResponseEntity<>(organizationService.updateOrganization(id, organization), HttpStatus.OK);
    }

}
