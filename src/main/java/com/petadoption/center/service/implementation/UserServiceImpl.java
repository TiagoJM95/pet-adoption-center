package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.UserConverter;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.model.User;
import com.petadoption.center.repository.UserRepository;
import com.petadoption.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.converter.UserConverter.toDto;
import static com.petadoption.center.converter.UserConverter.toModel;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.USER_WITH_ID;
import static com.petadoption.center.util.Utils.updateFields;
import static com.petadoption.center.util.factory.AddressFactory.createAddress;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserGetDto> getAllUsers(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        return userRepository.findAll(pageRequest).stream().map(UserConverter::toDto).toList();
    }

    @Override
    public UserGetDto getUserById(String id) throws UserNotFoundException {
        return toDto(findUserById(id));
    }

    @Override
    public UserGetDto addNewUser(UserCreateDto dto) {
        return toDto(userRepository.save(toModel(dto)));
    }

    @Override
    public UserGetDto updateUser(String id, UserUpdateDto dto) throws UserNotFoundException {
        User user = findUserById(id);
        return toDto(userRepository.save(user));
    }

    @Override
    public String deleteUser(String id) throws UserNotFoundException {
        findUserById(id);
        userRepository.deleteById(id);
        return USER_WITH_ID + id + DELETE_SUCCESS;
    }

    private void updateUserFields(UserUpdateDto dto, User user) {
        updateFields(dto.firstName(), user.getFirstName(), user::setFirstName);
        updateFields(dto.lastName(), user.getLastName(), user::setLastName);
        updateFields(createAddress(dto), user.getAddress(), user::setAddress);
    }

    private User findUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
