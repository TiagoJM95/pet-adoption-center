package com.petadoption.center.service;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.db.DatabaseConnectionException;
import com.petadoption.center.exception.user.UserEmailDuplicateException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.exception.user.UserPhoneNumberDuplicateException;

import java.util.List;

public interface UserService {

    List<UserGetDto> getAllUsers();

    UserGetDto getUserById(Long id) throws UserNotFoundException, DatabaseConnectionException;

    UserGetDto addNewUser(UserCreateDto user) throws UserEmailDuplicateException, UserPhoneNumberDuplicateException, DatabaseConnectionException;

    UserGetDto updateUser(Long id, UserUpdateDto user) throws UserNotFoundException, UserEmailDuplicateException, UserPhoneNumberDuplicateException;
}
