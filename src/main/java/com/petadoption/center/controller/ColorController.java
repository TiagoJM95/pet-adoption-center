package com.petadoption.center.controller;

import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.service.interfaces.ColorServiceI;
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
@RequestMapping("/api/v1/color")
@Tag(name = "Color", description = "The Color Endpoints")
public class ColorController {

    @Autowired
    private ColorServiceI colorServiceI;

    @Operation(
            summary = "Get all colors",
            description = "Get all existing colors from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of colors with the specified page (0 as default) and size of 5 elements as default, sorted by id as default",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ColorGetDto.class))
            ),
    })
    @Parameter(name = "page", description = "The number of the page of the result", example = "3")
    @Parameter(name = "size", description = "The number of elements per page", example = "5")
    @Parameter(name = "sortBy", description = "The field to sort by", example = "id")
    @GetMapping("/")
    public ResponseEntity<List<ColorGetDto>> getAllColors(@RequestParam (defaultValue = "0", required = false) int page,
                                                          @RequestParam (defaultValue = "5", required = false) int size,
                                                          @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(colorServiceI.getAllColors(page, size, sortBy), HttpStatus.OK);
    }

    @Operation(
            summary = "Get color by id",
            description = "Get color from database by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return the found color",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ColorGetDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Color not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Color with id: 12789-1234-1234-12345 not found"))
            ),
    })
    @Parameter(name = "id", description = "The color id to search for", example = "12789-1234-1234-12345", required = true)
    @GetMapping("/id/{id}")
    public ResponseEntity<ColorGetDto> getColorById(@PathVariable("id") String id)
            throws ColorNotFoundException {
        return new ResponseEntity<>(colorServiceI.getColorById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new color",
            description = "Add new color to database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return the created color",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ColorGetDto.class))
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
                                    example = "Color name Red already exists."))
            )
    })
    @PostMapping("/")
    public ResponseEntity<ColorGetDto> addNewColor(@Valid @RequestBody ColorCreateDto dto) {
        return new ResponseEntity<>(colorServiceI.addNewColor(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete color",
            description = "Delete color from database by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a message of successful operation",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Color with id: 12789-1234-1234-12345 deleted successfully"))
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
                    description = "Color not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Color with id: 12789-1234-1234-12345 not found"))),
    })
    @Parameter(name = "id", description = "The color id to delete", example = "12789-1234-1234-12345", required = true)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteColor(@PathVariable ("id") String id)
            throws ColorNotFoundException {
        return new ResponseEntity<>(colorServiceI.deleteColor(id), HttpStatus.OK);
    }
}
