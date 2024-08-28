package com.petadoption.center.controller;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.organization.OrgUpdateDto;
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
    public ResponseEntity<List<OrgGetDto>> getAllOrganizations(){
        return new ResponseEntity<>(organizationService.getAllOrganizations(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OrgGetDto> getOrganizationById(@PathVariable("id") Long id) throws OrgNotFoundException {
        return new ResponseEntity<>(organizationService.getOrganizationById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<OrgGetDto> addNewOrganization(@RequestBody OrgCreateDto organization) throws OrgDuplicateSocialMediaException, OrgDuplicatePhoneNumberException, OrgDuplicateAddressException, OrgDuplicateWebsiteException, OrgDuplicateEmailException {
        return new ResponseEntity<>(organizationService.addNewOrganization(organization), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrgGetDto> updateOrganization(@PathVariable ("id") Long id,
                                                        @RequestBody OrgUpdateDto organization) throws OrgDuplicateSocialMediaException, OrgNotFoundException, OrgDuplicatePhoneNumberException, OrgDuplicateAddressException, OrgDuplicateWebsiteException, OrgDuplicateEmailException {
        return new ResponseEntity<>(organizationService.updateOrganization(id, organization), HttpStatus.OK);
    }

}
