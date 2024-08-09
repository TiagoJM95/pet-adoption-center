package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.repository.UserRepository;
import com.petadoption.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserGetDto> getAllUsers() {
        return List.of();
    }

    @Override
    public UserGetDto getUserById(Long id) {
        return null;
    }

    @Override
    public UserGetDto addNewUser(UserCreateDto user) {
        return null;
    }

    @Override
    public UserGetDto updateUser(Long id, UserUpdateDto user) {
        return null;
    }
}
