package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.not_found.PetNotFoundException;
import com.petadoption.center.exception.not_found.UserNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserServiceI {
    List<UserGetDto> getAllUsers(int page, int size, String sortBy);
    UserGetDto getUserById(String id) throws UserNotFoundException;
    UserGetDto addNewUser(UserCreateDto user);
    UserGetDto updateUser(String id, UserUpdateDto user) throws UserNotFoundException;
    String deleteUser(String id) throws UserNotFoundException;
    String addPetToFavorites(String userId, String petId) throws UserNotFoundException, PetNotFoundException;
    Set<PetGetDto> getFavoritePets(String userId) throws UserNotFoundException;
    String removePetFromFavorites(String userId, String petId) throws UserNotFoundException, PetNotFoundException;
}