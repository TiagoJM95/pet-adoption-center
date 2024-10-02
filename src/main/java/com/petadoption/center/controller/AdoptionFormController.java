package com.petadoption.center.controller;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.exception.adoptionform.AdoptionFormNotFoundException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.service.interfaces.AdoptionFormServiceI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/adoption-form")
@Tag(name = "Adoption Form", description = "The Adoption Form Endpoints")
public class AdoptionFormController {

    @Autowired
    private AdoptionFormServiceI adoptionFormServiceI;

    @Operation(
            summary = "Get all adoption forms",
            description = "Get all adoption forms from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of adoption forms with the specified page (0 as default) and size of 5 elements as default, sorted by id as default",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdoptionFormGetDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "unauthenticated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "unauthorized"
            )
    })
    @Parameter(name = "page", description = "The page number to retrieve", example = "0", required = false)
    @Parameter(name = "size", description = "The number of items per page", example = "5", required = false)
    @Parameter(name = "sortBy", description = "The field to sort by", example = "id", required = false)
    @GetMapping("/")
    public ResponseEntity<List<AdoptionFormGetDto>> getAllAdoptionForms(@RequestParam (defaultValue = "0", required = false) int page,
                                                                        @RequestParam (defaultValue = "5", required = false) int size,
                                                                        @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(adoptionFormServiceI.getAllAdoptionForms(page, size, sortBy), HttpStatus.OK);
    }

    @Operation(
            summary = "Get adoption form by id",
            description = "Get adoption form by id from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return the adoption form with the specified id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdoptionFormGetDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "unauthenticated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "unauthorized"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Adoption form not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Adoption form with id: 12789-1234-1234-12345 not found"))
            )
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<AdoptionFormGetDto> getAdoptionFormById(@PathVariable("id") String id) throws AdoptionFormNotFoundException {
        return new ResponseEntity<>(adoptionFormServiceI.getAdoptionFormById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new adoption form",
            description = "Add new adoption form to database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Return the created adoption form",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdoptionFormGetDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "unauthenticated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "unauthorized"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict: Field duplicated",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "adoption form user id: 12789-1234-1234-12345 and pet id: 12789-1234-1234-12345 already exists."))
            )
    })
    @PostMapping("/")
    public ResponseEntity<AdoptionFormGetDto> addNewAdoptionForm(@RequestBody @Valid AdoptionFormCreateDto adoptionForm) throws UserNotFoundException, PetNotFoundException {
        return new ResponseEntity<>(adoptionFormServiceI.addNewAdoptionForm(adoptionForm), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update adoption form by id",
            description = "Update adoption form by id in database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return the adoption form updated with the specified id and information",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdoptionFormGetDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "unauthenticated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "unauthorized"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Adoption form not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Adoption form with id: 12789-1234-1234-12345 not found"))
            )
    })
    @Parameter(name = "id", description = "The id of the adoption form to be updated", example = "12789-1234-1234-12345", required = true)
    @PutMapping("/update/{id}")
    public ResponseEntity<AdoptionFormGetDto> updateAdoptionForm(@PathVariable ("id") String id,
                                                                 @RequestBody @Valid AdoptionFormUpdateDto adoptionForm) throws AdoptionFormNotFoundException {
        return new ResponseEntity<>(adoptionFormServiceI.updateAdoptionForm(id, adoptionForm), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete adoption form by id",
            description = "Delete adoption form by id in database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a message of successful operation",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Adoption form with id: 12789-1234-1234-12345 deleted successfully"))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "unauthenticated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "unauthorized"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Adoption form not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Adoption form with id: 12789-1234-1234-12345 not found"))
            )
    })
    @Parameter(name = "id", description = "The adoption form id to delete", example = "12789-1234-1234-12345", required = true)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdoptionForm(@PathVariable ("id") String id) throws AdoptionFormNotFoundException {
        return new ResponseEntity<>(adoptionFormServiceI.deleteAdoptionForm(id), HttpStatus.OK);
    }
}
