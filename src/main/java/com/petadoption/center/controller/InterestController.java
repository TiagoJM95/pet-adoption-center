package com.petadoption.center.controller;

import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.exception.interest.InterestNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.status.InvalidStatusChangeException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.service.interfaces.InterestServiceI;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interests")
public class InterestController {

    private final InterestServiceI interestService;

    public InterestController(InterestServiceI interestService) {
        this.interestService = interestService;
    }

    @GetMapping("/organization/{organizationId}/current")
    public ResponseEntity<List<InterestGetDto>> getCurrentInterestsInOrganizationPets(@RequestParam (defaultValue = "0", required = false) int page,
                                                                                      @RequestParam (defaultValue = "5", required = false) int size,
                                                                                      @RequestParam (defaultValue = "id", required = false) String sortBy,
                                                                                      @PathVariable("organizationId") String organizationId)
            throws OrgNotFoundException {
        return new ResponseEntity<>(interestService.getCurrentInterestsInOrganizationPets(page, size, sortBy, organizationId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}/history")
    public ResponseEntity<List<InterestGetDto>> getInterestHistoryInOrganizationPets(@RequestParam (defaultValue = "0", required = false) int page,
                                                                                     @RequestParam (defaultValue = "5", required = false) int size,
                                                                                     @RequestParam (defaultValue = "id", required = false) String sortBy,
                                                                                     @PathVariable("organizationId") String organizationId)
            throws OrgNotFoundException {
        return new ResponseEntity<>(interestService.getInterestHistoryInOrganizationPets(page, size, sortBy, organizationId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/current")
    public ResponseEntity<List<InterestGetDto>> getCurrentUserInterests(@RequestParam (defaultValue = "0", required = false) int page,
                                                                        @RequestParam (defaultValue = "5", required = false) int size,
                                                                        @RequestParam (defaultValue = "id", required = false) String sortBy,
                                                                        @PathVariable("userId") String userId)
            throws UserNotFoundException {
        return new ResponseEntity<>(interestService.getCurrentUserInterests(page, size, sortBy, userId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/history")
    public ResponseEntity<List<InterestGetDto>> getUserInterestHistory(@RequestParam (defaultValue = "0", required = false) int page,
                                                                       @RequestParam (defaultValue = "5", required = false) int size,
                                                                       @RequestParam (defaultValue = "id", required = false) String sortBy,
                                                                       @PathVariable("userId") String userId)
            throws UserNotFoundException {
        return new ResponseEntity<>(interestService.getUserInterestHistory(page, size, sortBy, userId), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<InterestGetDto> getById(@PathVariable("id") String id)
            throws InterestNotFoundException {
        return new ResponseEntity<>(interestService.getInterestById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<InterestGetDto> create(@Valid @RequestBody InterestCreateDto dto)
            throws UserNotFoundException, PetNotFoundException, OrgNotFoundException {
        return new ResponseEntity<>(interestService.addNewInterest(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<InterestGetDto> update(@PathVariable("id") String id, @Valid @RequestBody InterestUpdateDto dto)
            throws InterestNotFoundException, InvalidStatusChangeException, UserNotFoundException, PetNotFoundException {
        return new ResponseEntity<>(interestService.updateInterest(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) throws InterestNotFoundException {
        return new ResponseEntity<>(interestService.deleteInterest(id), HttpStatus.OK);
    }
}