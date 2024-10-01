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

    @GetMapping("/id/{id}")
    public ResponseEntity<SpeciesGetDto> getPetSpeciesById(@PathVariable("id") String id) throws SpeciesNotFoundException {
        return new ResponseEntity<>(speciesServiceI.getSpeciesById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<SpeciesGetDto> addNewPetSpecies(@Valid @RequestBody SpeciesCreateDto dto) {
        return new ResponseEntity<>(speciesServiceI.addNewSpecies(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SpeciesGetDto> updatePetSpecies(@PathVariable ("id") String id, @Valid @RequestBody SpeciesUpdateDto dto)
            throws SpeciesNotFoundException {
        return new ResponseEntity<>(speciesServiceI.updateSpecies(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSpecies(@PathVariable ("id") String id) throws SpeciesNotFoundException {
        return new ResponseEntity<>(speciesServiceI.deleteSpecies(id), HttpStatus.OK);
    }
}
