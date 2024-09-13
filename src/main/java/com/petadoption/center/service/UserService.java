package com.petadoption.center.service;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.InvalidDtoException;
import com.petadoption.center.exception.user.UserDuplicateException;
import com.petadoption.center.exception.user.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<UserGetDto> getAllUsers(int page, int size, String sortBy);
    UserGetDto getUserById(String id) throws UserNotFoundException;
    UserGetDto addNewUser(UserCreateDto user) throws UserDuplicateException, InvalidDtoException;
    UserGetDto updateUser(String id, UserUpdateDto user) throws UserNotFoundException, UserDuplicateException, InvalidDtoException;
    String deleteUser(String id) throws UserNotFoundException;
}