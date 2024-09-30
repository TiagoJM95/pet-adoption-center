package com.petadoption.center.controller;

import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.service.interfaces.UserServiceI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.examples.Example;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "The User Endpoints")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;

    @Operation(
            summary = "Get all users",
            description = "Get all existing users from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of user with the specified page (0 as default) and size of 5 elements as default, sorted by id as default",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserGetDto.class))
            ),
    })
    @Parameter(name = "firstName", description = "The user first name to search for", example = "Manuel")
    @Parameter(name = "page", description = "The number of the page of the result", example = "3")
    @Parameter(name = "size", description = "The number of elements per page", example = "5")
    @GetMapping("/")
    public ResponseEntity<List<UserGetDto>> getAllUsers(@RequestParam (defaultValue = "0", required = false) int page,
                                                        @RequestParam (defaultValue = "5", required = false) int size,
                                                        @RequestParam (defaultValue = "id", required = false) String sortBy) {
        return new ResponseEntity<>(userServiceI.getAllUsers(page, size, sortBy), HttpStatus.OK);
    }

    @Operation(
            summary = "Get user by id",
            description = "Get user from database by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a user with the specified id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserGetDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            ),
    })
    @Parameter(name = "id", description = "The user id to search for", example = "123124-12313-321312-3123", required = true)
    @GetMapping("/id/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable("id") String id) throws UserNotFoundException {
        return new ResponseEntity<>(userServiceI.getUserById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new user",
            description = "Add new user to database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Return the user created with the specified id and information",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserGetDto.class))
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
                        schema = @Schema(type = "string",
                            example = "email is already in use."))
            ),
    })
    @PostMapping("/")
    public ResponseEntity<UserGetDto> addNewUser(@Valid @RequestBody UserCreateDto dto) {
        return new ResponseEntity<>(userServiceI.addNewUser(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update user",
            description = "Update user from database by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return the user updated with the specified id and information",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserGetDto.class))
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
                    description = "User not found",
                    content = @Content(mediaType = "text/plain",
                        schema = @Schema(type = "string",
                            example = "User with id 123124-12313-321312-3123 not found"))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User already exists",
                    content = @Content(mediaType = "text/plain",
                        schema = @Schema(type = "string",
                            example = "email is already in use."))
            ),
    })
    @Parameter(name = "id", description = "The user id to update", example = "123124-12313-321312-3123", required = true)
    @PutMapping("/update/{id}")
    public ResponseEntity<UserGetDto> updateUser(@PathVariable ("id") String id, @Valid @RequestBody UserUpdateDto dto) throws UserNotFoundException {
        return new ResponseEntity<>(userServiceI.updateUser(id, dto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete user",
            description = "Delete user from database by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a message of successful operation",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string",
                                    example = "User with id: 123124-12313-321312-3123 deleted successfully"))
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
                        schema = @Schema(type = "string",
                                    example = "User with id 123124-12313-321312-3123 not found"))
            ),
            })
    @Parameter(name = "id", description = "The user id to delete", example = "123124-12313-321312-3123", required = true)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable ("id") String id) throws UserNotFoundException {
        return new ResponseEntity<>(userServiceI.deleteUser(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Add pet to user favorites",
            description = "Add pet to user favorites"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a message of successful operation",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string",
                                    example = "Pet added to favorites"))
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
                    description = "User or pet not found",
                    content = @Content(mediaType = "text/plain",
                        schema = @Schema(type = "string",
                                    example = "User with id: 123124-12313-321312-3123 or pet with id: 123124-12313-321312-3123 not found"))
            ),
    })
    @Parameter(name = "userId", description = "The user id to add pet", example = "123124-12313-321312-3123", required = true)
    @Parameter(name = "petId", description = "The pet id to add", example = "123124-12313-321312-3123", required = true)
    @PostMapping("/addPetToFavorites/{userId}/{petId}")
    public ResponseEntity<String> addPetToFavorites(@PathVariable("userId") String userId, @PathVariable("petId") String petId) throws UserNotFoundException,
            PetNotFoundException {
        return new ResponseEntity<>(userServiceI.addPetToFavorites(userId, petId), HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Remove pet from user favorites",
            description = "Remove pet from user favorites"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a message of successful operation",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string",
                                    example = "Pet removed from favorites"))
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
                    description = "User or pet not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string",
                                    example = "User with id: 123124-12313-321312-3123 or pet with id: 123124-12313-321312-3123 not found"))
            ),
    })
    @Parameter(name = "userId", description = "The user id to remove pet", example = "123124-12313-321312-3123", required = true)
    @Parameter(name = "petId", description = "The pet id to remove", example = "123124-12313-321312-3123", required = true)
    @PostMapping("/removePetFromFavorites/{userId}/{petId}")
    public ResponseEntity<String> removePetFromFavorites(@PathVariable("userId") String userId, @PathVariable("petId") String petId) throws UserNotFoundException,
            PetNotFoundException {
        return new ResponseEntity<>(userServiceI.removePetFromFavorites(userId, petId), HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Get user favorite pets",
            description = "Get user favorite pets"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a list of pets",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PetGetDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "unauthenticated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "unauthorized"
            ),
    })
    @Parameter(name = "userId", description = "The user id to search for", example = "123124-12313-321312-3123", required = true)
    @GetMapping("/getFavoritePets/{userId}")
    public ResponseEntity<Set<PetGetDto>> getFavoritePets(@PathVariable("userId") String userId) throws UserNotFoundException {
        return new ResponseEntity<>(userServiceI.getFavoritePets(userId), HttpStatus.OK);
    }
}