package com.petadoption.center.controller;

import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.exception.not_found.InterestNotFoundException;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;
import com.petadoption.center.exception.not_found.PetNotFoundException;
import com.petadoption.center.exception.not_found.InvalidStatusChangeException;
import com.petadoption.center.exception.not_found.UserNotFoundException;
import com.petadoption.center.service.interfaces.InterestServiceI;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<List<InterestGetDto>> getCurrentByOrganizationId(@PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable,
                                                                           @PathVariable("organizationId") String organizationId)
            throws OrganizationNotFoundException {
        return new ResponseEntity<>(interestService.getCurrentInterestsInOrganizationPets(pageable, organizationId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}/history")
    public ResponseEntity<List<InterestGetDto>> getHistoryByOrganizationId(@RequestParam (defaultValue = "0", required = false) int page,
                                                                           @RequestParam (defaultValue = "5", required = false) int size,
                                                                           @RequestParam (defaultValue = "id", required = false) String sortBy,
                                                                           @PathVariable("organizationId") String organizationId)
            throws OrganizationNotFoundException {
        return new ResponseEntity<>(interestService.getInterestHistoryInOrganizationPets(page, size, sortBy, organizationId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/current")
    public ResponseEntity<List<InterestGetDto>> getCurrentByUserId(@RequestParam (defaultValue = "0", required = false) int page,
                                                                   @RequestParam (defaultValue = "5", required = false) int size,
                                                                   @RequestParam (defaultValue = "id", required = false) String sortBy,
                                                                   @PathVariable("userId") String userId)
            throws UserNotFoundException {
        return new ResponseEntity<>(interestService.getCurrentUserInterests(page, size, sortBy, userId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/history")
    public ResponseEntity<List<InterestGetDto>> getHistoryByUserIda(@RequestParam (defaultValue = "0", required = false) int page,
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
            throws UserNotFoundException, PetNotFoundException, OrganizationNotFoundException {
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