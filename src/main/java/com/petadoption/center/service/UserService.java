package com.petadoption.center.service;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.user.UserAlreadyExistsException;
import com.petadoption.center.exception.user.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<UserGetDto> getAllUsers();

    UserGetDto getUserById(Long id) throws UserNotFoundException;

    UserGetDto addNewUser(UserCreateDto user) throws UserAlreadyExistsException;

    UserGetDto updateUser(Long id, UserUpdateDto user) throws UserNotFoundException, UserAlreadyExistsException;
}
