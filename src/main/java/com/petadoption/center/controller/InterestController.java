package com.petadoption.center.controller;

import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.exception.status.InvalidStatusChangeException;
import com.petadoption.center.exception.status.InvalidStatusException;
import com.petadoption.center.exception.interest.InterestNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.service.interfaces.InterestServiceI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interests")
@Tag(name = "Interest", description = "The Interest Endpoints")
public class InterestController {

    private final InterestServiceI interestService;

    public InterestController(InterestServiceI interestService) {
        this.interestService = interestService;
    }

    @Operation(
            summary = "Get all interests in pets for specified organization",
            description = "Get all interests in pets for specified organization from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of interests with the specified page (0 as default) and size of 5 elements as default, sorted by id as default",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InterestGetDto.class))
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
                                    example = "Organization with id: 2133-21313-32131-3213 not found"))
            )
    })
    @Parameter(name = "page", description = "The number of the page of the result", example = "3")
    @Parameter(name = "size", description = "The number of elements per page", example = "5")
    @Parameter(name = "sortBy", description = "The field to sort by", example = "id")
    @Parameter(name = "organizationId", description = "The organization id of the pets", example = "2133-21313-32131-3213")
    @GetMapping("/organization/{organizationId}/current")
    public ResponseEntity<List<InterestGetDto>> getCurrentInterestsInOrganizationPets(@RequestParam (defaultValue = "0", required = false) int page,
                                                                                      @RequestParam (defaultValue = "5", required = false) int size,
                                                                                      @RequestParam (defaultValue = "id", required = false) String sortBy,
                                                                                      @PathVariable("organizationId") String organizationId) throws OrgNotFoundException {
        return new ResponseEntity<>(interestService.getCurrentInterestsInOrganizationPets(page, size, sortBy, organizationId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get history of interests in pets for specified organization",
            description = "Get history of interests in pets for specified organization from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of interests with the specified page (0 as default) and size of 5 elements as default, sorted by id as default",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InterestGetDto.class))
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
                                    example = "Organization with id: 2133-21313-32131-3213 not found"))
            )
    })
    @Parameter(name = "page", description = "The number of the page of the result", example = "3")
    @Parameter(name = "size", description = "The number of elements per page", example = "5")
    @Parameter(name = "sortBy", description = "The field to sort by", example = "id")
    @Parameter(name = "organizationId", description = "The organization id of the pets", example = "2133-21313-32131-3213")
    @GetMapping("/organization/{organizationId}/history")
    public ResponseEntity<List<InterestGetDto>> getInterestHistoryInOrganizationPets(@RequestParam (defaultValue = "0", required = false) int page,
                                                                                     @RequestParam (defaultValue = "5", required = false) int size,
                                                                                     @RequestParam (defaultValue = "id", required = false) String sortBy,
                                                                                     @PathVariable("organizationId") String organizationId) throws OrgNotFoundException {
        return new ResponseEntity<>(interestService.getInterestHistoryInOrganizationPets(page, size, sortBy, organizationId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get all interests for specified user",
            description = "Get all interests for specified user from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of interests with the specified page (0 as default) and size of 5 elements as default, sorted by id as default",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InterestGetDto.class))
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
                    description = "User not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "User with id: 2133-21313-32131-3213 not found"))
            )
    })
    @Parameter(name = "page", description = "The number of the page of the result", example = "3")
    @Parameter(name = "size", description = "The number of elements per page", example = "5")
    @Parameter(name = "sortBy", description = "The field to sort by", example = "id")
    @Parameter(name = "userId", description = "The user id to search for", example = "2133-21313-32131-3213")
    @GetMapping("/user/{userId}/current")
    public ResponseEntity<List<InterestGetDto>> getCurrentUserInterests(@RequestParam (defaultValue = "0", required = false) int page,
                                                                        @RequestParam (defaultValue = "5", required = false) int size,
                                                                        @RequestParam (defaultValue = "id", required = false) String sortBy,
                                                                        @PathVariable("userId") String userId) throws UserNotFoundException {
        return new ResponseEntity<>(interestService.getCurrentUserInterests(page, size, sortBy, userId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get history of interests for specified user",
            description = "Get history of interests for specified user from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of interests with the specified page (0 as default) and size of 5 elements as default, sorted by id as default",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InterestGetDto.class))
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
                    description = "User not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "User with id: 2133-21313-32131-3213 not found"))
            )
    })
    @Parameter(name = "page", description = "The number of the page of the result", example = "3")
    @Parameter(name = "size", description = "The number of elements per page", example = "5")
    @Parameter(name = "sortBy", description = "The field to sort by", example = "id")
    @Parameter(name = "userId", description = "The user id to search for", example = "2133-21313-32131-3213")
    @GetMapping("/user/{userId}/history")
    public ResponseEntity<List<InterestGetDto>> getUserInterestHistory(@RequestParam (defaultValue = "0", required = false) int page,
                                                                       @RequestParam (defaultValue = "5", required = false) int size,
                                                                       @RequestParam (defaultValue = "id", required = false) String sortBy,
                                                                       @PathVariable("userId") String userId) throws UserNotFoundException {
        return new ResponseEntity<>(interestService.getUserInterestHistory(page, size, sortBy, userId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get interest by id",
            description = "Get interest by id from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return interest with specified id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InterestGetDto.class))
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
                    description = "Interest not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Interest with id: 2133-21313-32131-3213 not found"))
            )
    })
    @Parameter(name = "id", description = "The interest id to search for", example = "2133-21313-32131-3213", required = true)
    @GetMapping("/id/{id}")
    public ResponseEntity<InterestGetDto> getInterestById(@PathVariable("id") String id) throws InterestNotFoundException {
        return new ResponseEntity<>(interestService.getInterestById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "add new interest",
            description = "add new interest in database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Return the created interest",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InterestGetDto.class))
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
                                    example = "pet id 2133-21313-32131-3213, organization id 2133-21313-32131-3213 already in use."))
                    )
    })
    @PostMapping("/")
    public ResponseEntity<InterestGetDto> addNewInterest(@Valid @RequestBody InterestCreateDto dto) throws UserNotFoundException, PetNotFoundException, OrgNotFoundException {
        return new ResponseEntity<>(interestService.addNewInterest(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update interest by id",
            description = "Update interest by id in database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return the interest updated with the specified id and information",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InterestGetDto.class))
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
                    description = "Interest not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Interest with id: 2133-21313-32131-3213 not found"))
            )
    })
    @Parameter(name = "id", description = "The interest id to update", example = "2133-21313-32131-3213", required = true)
    @PutMapping("/update/{id}")
    public ResponseEntity<InterestGetDto> updateInterest(@PathVariable("id") String id, @Valid @RequestBody InterestUpdateDto dto) throws InterestNotFoundException, InvalidStatusException, InvalidStatusChangeException, UserNotFoundException, PetNotFoundException {
        return new ResponseEntity<>(interestService.updateInterest(id, dto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete interest by id",
            description = "Delete interest by id in database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a message of successful operation",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Interest with id: 2133-21313-32131-3213 deleted successfully"))
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
                    description = "Interest not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "String",
                                    example = "Interest with id: 2133-21313-32131-3213 not found"))
            )
    })
    @Parameter(name = "id", description = "The interest id to delete", example = "2133-21313-32131-3213", required = true)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInterest(@PathVariable("id") String id) throws InterestNotFoundException {
        return new ResponseEntity<>(interestService.deleteInterest(id), HttpStatus.OK);
    }

}
