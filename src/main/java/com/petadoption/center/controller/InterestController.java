package com.petadoption.center.controller;

import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
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
                                                                           @PathVariable("organizationId") String organizationId) {
        return new ResponseEntity<>(interestService.getCurrentByOrganizationId(pageable, organizationId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}/history")
    public ResponseEntity<List<InterestGetDto>> getHistoryByOrganizationId(@PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable,
                                                                           @PathVariable("organizationId") String organizationId) {
        return new ResponseEntity<>(interestService.getHistoryByOrganizationId(pageable, organizationId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/current")
    public ResponseEntity<List<InterestGetDto>> getCurrentByUserId(@PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable,
                                                                   @PathVariable("userId") String userId) {
        return new ResponseEntity<>(interestService.getCurrentByUserId(pageable, userId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/history")
    public ResponseEntity<List<InterestGetDto>> getHistoryByUserId(@PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable,
                                                                   @PathVariable("userId") String userId) {
        return new ResponseEntity<>(interestService.getHistoryByUserId(pageable, userId), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<InterestGetDto> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(interestService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<InterestGetDto> create(@Valid @RequestBody InterestCreateDto dto) {
        return new ResponseEntity<>(interestService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<InterestGetDto> update(@PathVariable("id") String id, @Valid @RequestBody InterestUpdateDto dto) {
        return new ResponseEntity<>(interestService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        return new ResponseEntity<>(interestService.delete(id), HttpStatus.OK);
    }
}