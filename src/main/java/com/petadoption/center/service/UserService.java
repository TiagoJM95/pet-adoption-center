package com.petadoption.center.service;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.user.UserEmailDuplicateException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.exception.user.UserPhoneNumberDuplicateException;
import jakarta.mail.internet.AddressException;

import java.util.List;

public interface UserService {

    List<UserGetDto> getAllUsers(int page, int size, String sortBy);

    UserGetDto getUserById(Long id) throws UserNotFoundException;

    UserGetDto addNewUser(UserCreateDto user) throws UserEmailDuplicateException, UserPhoneNumberDuplicateException;

    UserGetDto updateUser(Long id, UserUpdateDto user) throws UserNotFoundException, UserEmailDuplicateException, UserPhoneNumberDuplicateException;

    String deleteUser(Long id) throws UserNotFoundException;
}
