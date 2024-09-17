package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.user.UserNotFoundException;

import java.util.List;

public interface UserServiceI {
    List<UserGetDto> getAllUsers(int page, int size, String sortBy);
    UserGetDto getUserById(String id) throws UserNotFoundException;
    UserGetDto addNewUser(UserCreateDto user);
    UserGetDto updateUser(String id, UserUpdateDto user) throws UserNotFoundException;
    String deleteUser(String id) throws UserNotFoundException;
}