package com.petadoption.center.service.interfaces;

import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface UserServiceI {
    List<UserGetDto> getAll(Pageable pageable);
    UserGetDto getById(String id);
    UserGetDto create(UserCreateDto user);
    UserGetDto update(String id, UserUpdateDto dto);
    String delete(String id);
    String addPetToFavorites(String userId, String petId);
    Set<PetGetDto> getFavoritePets(String userId);
    String removePetFromFavorites(String userId, String petId);
}