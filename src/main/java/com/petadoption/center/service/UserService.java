package com.petadoption.center.service;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserPatchDto;

import java.util.List;

public interface UserService {


    List<UserGetDto> getAllUsers();

    UserGetDto getUserById(Long userId);

    UserGetDto addNewUser(UserCreateDto userCreateDto);

    UserGetDto updateUser(Long userId, UserPatchDto userPatchDto);
}
