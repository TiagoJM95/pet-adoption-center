package com.petadoption.center.service;

import com.petadoption.center.converter.PetConverter;
import com.petadoption.center.converter.UserConverter;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.not_found.UserNotFoundException;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.User;
import com.petadoption.center.repository.UserRepository;
import com.petadoption.center.service.interfaces.PetServiceI;
import com.petadoption.center.service.interfaces.UserServiceI;
import com.petadoption.center.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.petadoption.center.converter.UserConverter.toDto;
import static com.petadoption.center.converter.UserConverter.toModel;
import static com.petadoption.center.util.Messages.*;

@Service
public class UserService implements UserServiceI {

    private final UserRepository userRepository;
    private final PetServiceI petService;

    @Autowired
    public UserService(UserRepository userRepository, PetServiceI petService) {
        this.userRepository = userRepository;
        this.petService = petService;
    }

    @Override
    public List<UserGetDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).stream().map(UserConverter::toDto).toList();
    }

    @Override
    public UserGetDto getById(String id) {
        return toDto(findById(id));
    }

    @Override
    public UserGetDto create(UserCreateDto dto) {
        return toDto(userRepository.save(toModel(dto)));
    }

    @Override
    public UserGetDto update(String id, UserUpdateDto dto) {
        User user = findById(id);
        updateFields(dto,user);
        return toDto(userRepository.save(user));
    }

    @Override
    public String delete(String id) {
        findById(id);
        userRepository.deleteById(id);
        return USER_WITH_ID + id + DELETE_SUCCESS;
    }

    @Override
    public String addPetToFavorites(String userId, String petId) {
        User user = findById(userId);
        Pet pet = PetConverter.toModel(petService.getById(petId));
        user.getFavoritePets().add(pet);
        userRepository.save(user);
        return ADDED_TO_FAVORITE_SUCCESS;
    }

    @Override
    public Set<PetGetDto> getFavoritePets(String userId) {
        User user = findById(userId);
        return user.getFavoritePets().stream().map(PetConverter::toDto).collect(Collectors.toSet());
    }

    @Override
    public String removePetFromFavorites(String userId, String petId) {
        User user = findById(userId);
        Pet pet = PetConverter.toModel(petService.getById(petId));
        user.getFavoritePets().remove(pet);
        userRepository.save(user);
        return REMOVED_FROM_FAVORITE_SUCCESS;
    }

    private void updateFields(UserUpdateDto dto, User user) {
        Utils.updateFields(dto.firstName(), user.getFirstName(), user::setFirstName);
        Utils.updateFields(dto.lastName(), user.getLastName(), user::setLastName);
        Utils.updateFields(dto.email(), user.getEmail(), user::setEmail);
        Utils.updateFields(dto.address(), user.getAddress(), user::setAddress);
        Utils.updateFields(dto.phoneNumber(), user.getPhoneNumber(), user::setPhoneNumber);
    }

    private User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
