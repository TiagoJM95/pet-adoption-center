package com.petadoption.center.controller;

import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.species.SpeciesUpdateDto;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.service.interfaces.SpeciesServiceI;
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
@RequestMapping("/api/v1/pet-species")
@Tag(name = "Species", description = "The Species Endpoints")
public class SpeciesController {

    @Autowired
    private SpeciesServiceI speciesServiceI;

    @Operation(
            summary = "Get all pet species",
            description = "Get all existing species from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of species with the specified page (0 as default) and size of 5 elements as default, sorted by id as default",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpeciesGetDto.class))
            ),
    })
    @Parameter(name = "page", description = "The number of the page of the result", example = "3")
    @Parameter(name = "size", description = "The number of elements per page", example = "5")
    @Parameter(name = "sortBy", description = "The field to sort by", example = "id")
    @GetMapping("/")
    public ResponseEntity<List<SpeciesGetDto>> getAllPetSpecies(@RequestParam (defaultValue = "0", required = false) int page,
                                                                @RequestParam (defaultValue = "5", required = false) int size,
                                                                @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(speciesServiceI.getAllSpecies(page, size, sortBy), HttpStatus.OK);
    }

    @Operation(
            summary = "Get pet species by id",
            description = "Get pet species with the specified id from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a species with the specified id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpeciesGetDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Species not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Species with id: 12789-1234-1234-12345 not found"))
            ),
    })
    @Parameter(name = "id", description = "The species id to search for", example = "12789-1234-1234-12345", required = true)
    @GetMapping("/id/{id}")
    public ResponseEntity<SpeciesGetDto> getPetSpeciesById(@PathVariable("id") String id) throws SpeciesNotFoundException {
        return new ResponseEntity<>(speciesServiceI.getSpeciesById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new pet species",
            description = "Add new species to database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Return the created species",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpeciesGetDto.class))
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
                                    example = "species name Dog already exists."))
            )
    })
    @PostMapping("/")
    public ResponseEntity<SpeciesGetDto> addNewPetSpecies(@Valid @RequestBody SpeciesCreateDto dto) {
        return new ResponseEntity<>(speciesServiceI.addNewSpecies(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update pet species",
            description = "Update species from database by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return the species updated with the specified id and information",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpeciesGetDto.class))
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
                    description = "Species not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Species with id: 12789-1234-1234-12345 not found"))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict: Field duplicated",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "species name Dog already exists."))
            )})
    @Parameter(name = "id", description = "The species id to update", example = "12789-1234-1234-12345", required = true)
    @PutMapping("/update/{id}")
    public ResponseEntity<SpeciesGetDto> updatePetSpecies(@PathVariable ("id") String id, @Valid @RequestBody SpeciesUpdateDto dto)
            throws SpeciesNotFoundException {
        return new ResponseEntity<>(speciesServiceI.updateSpecies(id, dto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete pet species",
            description = "Delete species from database by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a message of successful operation",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Species with id: 12789-1234-1234-12345 deleted successfully"))
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
                    description = "Species not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Species with id: 12789-1234-1234-12345 not found"))
            ),
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSpecies(@PathVariable ("id") String id) throws SpeciesNotFoundException {
        return new ResponseEntity<>(speciesServiceI.deleteSpecies(id), HttpStatus.OK);
    }
}
