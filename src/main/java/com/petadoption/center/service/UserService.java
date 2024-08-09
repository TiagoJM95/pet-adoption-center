package com.petadoption.center.service;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;

import java.util.List;

public interface UserService {

    List<UserGetDto> getAllUsers();

    UserGetDto getUserById(Long id);

    UserGetDto addNewUser(UserCreateDto user);

    UserGetDto updateUser(Long id, UserUpdateDto user);
}
