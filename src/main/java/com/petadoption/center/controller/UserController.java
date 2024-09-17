package com.petadoption.center.controller;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.service.interfaces.UserServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;

    @GetMapping("/")
    public ResponseEntity<List<UserGetDto>> getAllUsers(@RequestParam (defaultValue = "0", required = false) int page,
                                                        @RequestParam (defaultValue = "5", required = false) int size,
                                                        @RequestParam (defaultValue = "id", required = false) String sortBy) {
        return new ResponseEntity<>(userServiceI.getAllUsers(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable("id") String id) throws UserNotFoundException {
        return new ResponseEntity<>(userServiceI.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserGetDto> addNewUser(@Valid @RequestBody UserCreateDto dto) {
        return new ResponseEntity<>(userServiceI.addNewUser(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserGetDto> updateUser(@PathVariable ("id") String id, @Valid @RequestBody UserUpdateDto dto) throws UserNotFoundException {
        return new ResponseEntity<>(userServiceI.updateUser(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable ("id") String id) throws UserNotFoundException {
        return new ResponseEntity<>(userServiceI.deleteUser(id), HttpStatus.OK);
    }

    @PostMapping("/addPetToFavorites/{userId}/{petId}")
    public ResponseEntity<String> addPetToFavorites(@PathVariable("userId") String userId, @PathVariable("petId") String petId) throws UserNotFoundException,
            PetNotFoundException {
        return new ResponseEntity<>(userServiceI.addPetToFavorites(userId, petId), HttpStatus.ACCEPTED);
    }
}