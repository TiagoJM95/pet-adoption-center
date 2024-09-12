package com.petadoption.center.controller;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.user.UserDuplicateException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.service.UserService;
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
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserGetDto>> getAllUsers(@RequestParam (defaultValue = "0", required = false) int page,
                                                        @RequestParam (defaultValue = "5", required = false) int size,
                                                        @RequestParam (defaultValue = "id", required = false) String sortBy) {
        return new ResponseEntity<>(userService.getAllUsers(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable("id") Long id) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserGetDto> addNewUser(@Valid @RequestBody UserCreateDto dto) throws UserDuplicateException {
        return new ResponseEntity<>(userService.addNewUser(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserGetDto> updateUser(@PathVariable ("id") Long id, @Valid @RequestBody UserUpdateDto dto) throws UserNotFoundException, UserDuplicateException {
        return new ResponseEntity<>(userService.updateUser(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable ("id") Long id) throws UserNotFoundException {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

}
