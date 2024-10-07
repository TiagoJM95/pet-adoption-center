package com.petadoption.center.controller;

import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.service.interfaces.UserServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserServiceI userServiceI;

    @Autowired
    public UserController(UserServiceI userServiceI) {
        this.userServiceI = userServiceI;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserGetDto>> getAll(@PageableDefault(sort = "created_at") Pageable pageable) {
        return new ResponseEntity<>(userServiceI.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserGetDto> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(userServiceI.getById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserGetDto> create(@Valid @RequestBody UserCreateDto dto) {
        return new ResponseEntity<>(userServiceI.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserGetDto> update(@PathVariable ("id") String id, @Valid @RequestBody UserUpdateDto dto) {
        return new ResponseEntity<>(userServiceI.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable ("id") String id) {
        return new ResponseEntity<>(userServiceI.delete(id), HttpStatus.OK);
    }

    @PostMapping("/addPetToFavorites/{userId}/{petId}")
    public ResponseEntity<String> addPetToFavorites(@PathVariable("userId") String userId, @PathVariable("petId") String petId) {
        return new ResponseEntity<>(userServiceI.addPetToFavorites(userId, petId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/removePetFromFavorites/{userId}/{petId}")
    public ResponseEntity<String> removePetFromFavorites(@PathVariable("userId") String userId, @PathVariable("petId") String petId) {
        return new ResponseEntity<>(userServiceI.removePetFromFavorites(userId, petId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getFavoritePets/{userId}")
    public ResponseEntity<Set<PetGetDto>> getFavoritePets(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(userServiceI.getFavoritePets(userId), HttpStatus.OK);
    }
}