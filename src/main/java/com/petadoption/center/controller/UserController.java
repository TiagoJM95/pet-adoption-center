package com.petadoption.center.controller;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserPatchDto;
import com.petadoption.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping("/")
    public ResponseEntity<List<UserGetDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable("userId") Long userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserGetDto> addNewUser(@RequestBody UserCreateDto userCreateDto){
        return new ResponseEntity<>(userService.addNewUser(userCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserGetDto> updateUser(@PathVariable ("userId") Long userId, @RequestBody UserPatchDto userPatchDto){
        return new ResponseEntity<>(userService.updateUser(userId, userPatchDto), HttpStatus.OK);
    }


}
