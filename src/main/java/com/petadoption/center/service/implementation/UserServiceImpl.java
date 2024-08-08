package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserPatchDto;
import com.petadoption.center.repository.UserRepository;
import com.petadoption.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<UserGetDto> getAllUsers() {
        return List.of();
    }

    @Override
    public UserGetDto getUserById(Long userId) {
        return null;
    }

    @Override
    public UserGetDto addNewUser(UserCreateDto userCreateDto) {
        return null;
    }

    @Override
    public UserGetDto updateUser(Long userId, UserPatchDto userPatchDto) {
        return null;
    }
}
