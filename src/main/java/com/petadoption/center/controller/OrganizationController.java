package com.petadoption.center.controller;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.organization.OrgUpdateDto;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.service.interfaces.OrganizationServiceI;
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
@RequestMapping("/api/v1/organization")
@Tag(name = "Organization", description = "The Organization Endpoints")
public class OrganizationController {

    @Autowired
    private OrganizationServiceI organizationServiceI;

    @Operation(
            summary = "Get all organizations",
            description = "Get all organizations from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of organizations with the specified page (0 as default) and size of 5 elements as default, sorted by id as default",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrgGetDto.class))
            ),
    })
    @Parameter(name = "page", description = "The number of the page of the result", example = "3")
    @Parameter(name = "size", description = "The number of elements per page", example = "5")
    @Parameter(name = "sortBy", description = "The field to sort by", example = "id")
    @GetMapping("/")
    public ResponseEntity<List<OrgGetDto>> getAllOrganizations(@RequestParam (defaultValue = "0", required = false) int page,
                                                               @RequestParam (defaultValue = "5", required = false) int size,
                                                               @RequestParam (defaultValue = "id", required = false) String sortBy){
        return new ResponseEntity<>(organizationServiceI.getAllOrganizations(page, size, sortBy), HttpStatus.OK);
    }

    @Operation(
            summary = "Get organization by id",
            description = "Get organization by id from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of organizations with the specified page (0 as default) and size of 5 elements as default, sorted by id as default",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrgGetDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Organization not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Organization with id: 12789-1234-1234-12345 not found"))
            )
    })
    @Parameter(name = "id", description = "The id of the organization", example = "12789-1234-1234-12345")
    @GetMapping("/id/{id}")
    public ResponseEntity<OrgGetDto> getOrganizationById(@PathVariable("id") String id) throws OrgNotFoundException {
        return new ResponseEntity<>(organizationServiceI.getOrganizationById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new organization",
            description = "Add a new organization to database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Return the created organization",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrgGetDto.class))
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
                                    example = "organization name Pet Adoption Center already exists."))
            )
    })
    @PostMapping("/")
    public ResponseEntity<OrgGetDto> addNewOrganization(@Valid @RequestBody OrgCreateDto organization) {
        return new ResponseEntity<>(organizationServiceI.addNewOrganization(organization), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update organization by id",
            description = "Update organization by id in database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return the organization updated with the specified id and information",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrgGetDto.class))
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
                    description = "Organization not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Organization with id: 12789-1234-1234-12345 not found"))
            )
    })
    @Parameter(name = "id", description = "The id of the organization to update", example = "12789-1234-1234-12345")
    @PutMapping("/update/{id}")
    public ResponseEntity<OrgGetDto> updateOrganization(@PathVariable ("id") String id,
                                                        @Valid @RequestBody OrgUpdateDto organization) throws OrgNotFoundException {
        return new ResponseEntity<>(organizationServiceI.updateOrganization(id, organization), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete organization by id",
            description = "Delete organization by id from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a message of successful operation",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Organization with id: 12789-1234-1234-12345 deleted successfully"))
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
                    description = "Organization not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Organization with id: 12789-1234-1234-12345 not found"))),
    })
    @Parameter(name = "id", description = "The id of the organization to delete", example = "12789-1234-1234-12345")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrganization(@PathVariable ("id") String id) throws OrgNotFoundException {
        return new ResponseEntity<>(organizationServiceI.deleteOrganization(id), HttpStatus.OK);
    }
}
