package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.UserConverter;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.email.service.EmailService;
import com.petadoption.center.exception.user.UserDuplicateException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.repository.UserRepository;
import com.petadoption.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.converter.UserConverter.toDto;
import static com.petadoption.center.converter.UserConverter.toModel;
import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Utils.updateFields;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmailService emailService) {
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
    public UserGetDto addNewUser(UserCreateDto dto) throws UserDuplicateException {
        checkIfUserExistsByEmail(dto.email());
        checkIfUserExistsByPhoneNumber(dto.phoneNumber());
        User userSaved = userRepository.save(toModel(dto));
        //emailService.sendWelcomeMail(userSaved);
        return toDto(userSaved);
    }

    @Override
    public UserGetDto updateUser(String id, UserUpdateDto dto) throws UserNotFoundException, UserDuplicateException {
        User user = findUserById(id);
        checkUserDuplicates(dto, user);
        updateUserFields(dto, user);
        return toDto(userRepository.save(user));
    }

    @Override
    public String deleteUser(String id) throws UserNotFoundException {
        findUserById(id);
        userRepository.deleteById(id);
        return USER_WITH_ID + id + DELETE_SUCCESS;
    }

    private void checkUserDuplicates(UserUpdateDto dto, User user) throws UserDuplicateException {
        if(!dto.email().equals(user.getEmail())){
            checkIfUserExistsByEmail(dto.email());
        }
        if(!dto.phoneNumber().equals(user.getPhoneNumber())){
            checkIfUserExistsByPhoneNumber(dto.phoneNumber());
        }
    }

    private void updateUserFields(UserUpdateDto dto, User user) {
        updateFields(dto.firstName(), user.getFirstName(), user::setFirstName);
        updateFields(dto.lastName(), user.getLastName(), user::setLastName);
        updateFields(createAddress(dto), user.getAddress(), user::setAddress);
    }

    private User findUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private void checkIfUserExistsByEmail(String email) throws UserDuplicateException {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserDuplicateException(USER_WITH_EMAIL + email + ALREADY_EXISTS);
        }
    }

    private void checkIfUserExistsByPhoneNumber(String phoneNumber) throws UserDuplicateException {
        if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new UserDuplicateException(USER_WITH_PHONE_NUMBER + phoneNumber.toString() + ALREADY_EXISTS);
        }
    }

    private Address createAddress(UserUpdateDto dto) {
        return new Address(dto.street(), dto.city(), dto.state(), dto.postalCode());
    }
}
