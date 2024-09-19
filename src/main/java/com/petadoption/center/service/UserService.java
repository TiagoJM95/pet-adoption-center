package com.petadoption.center.service;

import com.petadoption.center.converter.UserConverter;
import com.petadoption.center.dto.email.EmailDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.model.User;
import com.petadoption.center.repository.UserRepository;
import com.petadoption.center.service.interfaces.UserServiceI;
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
import static com.petadoption.center.factory.AddressFactory.createAddress;

@Service
public class UserService implements UserServiceI {

    private final UserRepository userRepository;

    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
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
        emailService.sendEmail(new EmailDto(dto.email(), "Welcome!", "Welcome to Pet Adoption Center!"));
        return toDto(userRepository.save(toModel(dto)));
    }

    @Override
    public UserGetDto updateUser(String id, UserUpdateDto dto) throws UserNotFoundException {
        User user = findUserById(id);
        updateUserFields(dto, user);
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
        updateFields(dto.email(), user.getEmail(), user::setEmail);
        updateFields(createAddress(dto), user.getAddress(), user::setAddress);
        updateFields(dto.phoneNumber(), user.getPhoneNumber(), user::setPhoneNumber);
    }

    private User findUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
