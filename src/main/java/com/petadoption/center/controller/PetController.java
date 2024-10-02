package com.petadoption.center.controller;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.exception.breed.BreedMismatchException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetDescriptionException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.service.interfaces.PetServiceI;
import com.petadoption.center.specifications.PetSearchCriteria;
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
@RequestMapping("/api/v1/pet")
@Tag(name = "Pet", description = "The Pet Endpoints")
public class PetController {

    @Autowired
    private PetServiceI petServiceI;

    @Operation(
            summary = "Get pet by id",
            description = "Get pet by id from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a pet with the specified id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PetGetDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pet not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Pet with id: 12789-1234-1234-12345 not found"))
            ),
    })
    @Parameter(name = "id", description = "The pet id to search for", example = "12789-1234-1234-12345", required = true)
    @GetMapping("/id/{id}")
    public ResponseEntity<PetGetDto> getPetById(@PathVariable("id") String id) throws PetNotFoundException {
        return new ResponseEntity<>(petServiceI.getPetById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get all pets",
            description = "Get all pets from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of all pets according to the search criteria, sorted by the specified field and with the specified page and size",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PetGetDto.class))
            ),
    })
    @Parameter(name = "page", description = "The number of the page of the result", example = "3")
    @Parameter(name = "size", description = "The number of elements per page", example = "5")
    @Parameter(name = "sortBy", description = "The field to sort by", example = "id")
    @PostMapping("/search")
    public ResponseEntity<List<PetGetDto>> searchPets(@RequestBody PetSearchCriteria searchCriteria,
                                                      @RequestParam (defaultValue = "0", required = false) int page,
                                                      @RequestParam (defaultValue = "5", required = false) int size,
                                                      @RequestParam (defaultValue = "id", required = false) String sortBy)
            throws SpeciesNotFoundException, PetDescriptionException {
        return new ResponseEntity<>(petServiceI.searchPets(searchCriteria, page, size, sortBy), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new pet",
            description = "Add new pet to database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Return the created pet",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PetGetDto.class))
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
                                    example = "A pet already exists with the same specifications"))
                    )
    })
    @PostMapping("/addSingle")
    public ResponseEntity<PetGetDto> addNewPet(@Valid @RequestBody PetCreateDto pet) throws OrgNotFoundException, BreedNotFoundException, ColorNotFoundException, SpeciesNotFoundException, BreedMismatchException, PetDescriptionException {
        return new ResponseEntity<>(petServiceI.addNewPet(pet), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Add list of new pets",
            description = "Add list of new pets to database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Return the list of created pets",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PetGetDto.class))
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
                                    example = "A pet already exists with the same specifications"))
            )
    })
    @PostMapping("/addList")
    public ResponseEntity<String> addListOfNewPets(@Valid @RequestBody List<PetCreateDto> pets) throws OrgNotFoundException, BreedNotFoundException, ColorNotFoundException, SpeciesNotFoundException, BreedMismatchException, PetDescriptionException {
        petServiceI.addListOfNewPets(pets);
        return new ResponseEntity<>("Added", HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update pet by id",
            description = "Update pet by id in database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return the pet updated with the specified id and information",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PetGetDto.class))
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
                    description = "Pet not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Pet with id: 12789-1234-1234-12345 not found"))
            )
    })
    @Parameter(name = "id", description = "The pet id to update", example = "12789-1234-1234-12345", required = true)
    @PutMapping("/update/{id}")
    public ResponseEntity<PetGetDto> updatePet(@Valid @PathVariable ("id") String id, @RequestBody PetUpdateDto pet) throws OrgNotFoundException, PetNotFoundException, PetDescriptionException {
        return new ResponseEntity<>(petServiceI.updatePet(id, pet), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete pet by id",
            description = "Delete pet by id in database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a message of successful operation",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Pet with id: 12789-1234-1234-12345 deleted successfully"))
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
                    description = "Pet not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Pet with id: 12789-1234-1234-12345 not found"))
            )
    })
    @Parameter(name = "id", description = "The pet id to delete", example = "12789-1234-1234-12345", required = true)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePet(@PathVariable ("id") String id) throws PetNotFoundException {
        return new ResponseEntity<>(petServiceI.deletePet(id), HttpStatus.OK);
    }
}
