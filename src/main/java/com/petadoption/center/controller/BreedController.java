package com.petadoption.center.controller;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.breed.BreedUpdateDto;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.service.interfaces.BreedServiceI;
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
@RequestMapping("/api/v1/breed")
@Tag(name = "Breed", description = "The Breed Endpoints")
public class BreedController {

    @Autowired
    private BreedServiceI breedServiceI;

    @Operation(
            summary = "Get all pet species",
            description = "Get all existing species from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of species with the specified page (0 as default) and size of 5 elements as default, sorted by id as default",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BreedGetDto.class))
            ),
    })
    @Parameter(name = "page", description = "The number of the page of the result", example = "3")
    @Parameter(name = "size", description = "The number of elements per page", example = "5")
    @Parameter(name = "sortBy", description = "The field to sort by", example = "id")
    @GetMapping("/")
    public ResponseEntity<List<BreedGetDto>> getAllBreeds(@RequestParam (defaultValue = "0", required = false) int page,
                                                          @RequestParam (defaultValue = "5", required = false) int size,
                                                          @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(breedServiceI.getAllBreeds(page, size, sortBy), HttpStatus.OK);
    }

    @Operation(
            summary = "Get breed by id",
            description = "Get a specific breed from database by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a breed with the specified id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BreedGetDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Breed not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Breed with id: 12789-1234-1234-12345 not found"))
            ),
    })
    @Parameter(name = "id", description = "The breed id to search for", example = "12789-1234-1234-12345", required = true)
    @GetMapping("/id/{id}")
    public ResponseEntity<BreedGetDto> getBreedById(@PathVariable("id") String id) throws BreedNotFoundException {
        return new ResponseEntity<>(breedServiceI.getBreedById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get breed by species",
            description = "Get all existing breed from database by species"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of breed with the specified species",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BreedGetDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Species not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Species with id: 12789-1234-1234-12345 not found"))
            ),
    })
    @Parameter(name = "species", description = "The species id to search for", example = "12789-1234-1234-12345", required = true)
    @GetMapping("/species/{species}")
    public ResponseEntity<List<BreedGetDto>> getBreedsBySpecies(@PathVariable("species") String species)
            throws SpeciesNotFoundException {
        return new ResponseEntity<>(breedServiceI.getBreedsBySpecies(species), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new breed",
            description = "Add new breed to database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Return the created breed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BreedGetDto.class))
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
                                    example = "breed name Huskey already exists."))
            ),
    })
    @PostMapping("/")
    public ResponseEntity<BreedGetDto> addNewBreed(@Valid @RequestBody BreedCreateDto dto)
            throws SpeciesNotFoundException {
        return new ResponseEntity<>(breedServiceI.addNewBreed(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update breed",
            description = "Update breed from database by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return the breed updated with the specified id and information",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BreedGetDto.class))
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
                    description = "Breed not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Breed with id: 12789-1234-1234-12345 not found"))
                    ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict: Field duplicated",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "breed name Huskey already exists."))
            ),
    })
    @Parameter(name = "id", description = "The breed id to update", example = "12789-1234-1234-12345", required = true)
    @PutMapping("/update/{id}")
    public ResponseEntity<BreedGetDto> updateBreed(@PathVariable ("id") String id, @Valid @RequestBody BreedUpdateDto dto)
            throws BreedNotFoundException {
        return new ResponseEntity<>(breedServiceI.updateBreed(id, dto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete breed",
            description = "Delete breed from database by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a message of successful operation",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Breed with id: 12789-1234-1234-12345 deleted successfully"))
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
                    description = "Breed not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Breed with id: 12789-1234-1234-12345 not found"))),
    })
    @Parameter(name = "id", description = "The breed id to delete", example = "12789-1234-1234-12345", required = true)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBreed(@PathVariable ("id") String id) throws BreedNotFoundException {
        return new ResponseEntity<>(breedServiceI.deleteBreed(id), HttpStatus.OK);
    }
}
