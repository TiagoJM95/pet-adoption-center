package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.UserConverter;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.email.service.EmailService;
import com.petadoption.center.exception.user.UserEmailDuplicateException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.exception.user.UserPhoneNumberDuplicateException;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.repository.UserRepository;
import com.petadoption.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.converter.UserConverter.fromModelToUserGetDto;
import static com.petadoption.center.converter.UserConverter.fromUserCreateDtoToModel;
import static com.petadoption.center.util.FieldUpdater.updateIfChanged;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.USER_WITH_ID;

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
        return userRepository.findAll(pageRequest).stream().map(UserConverter::fromModelToUserGetDto).toList();
    }

    @Override
    public UserGetDto getUserById(Long id) throws UserNotFoundException {
        return fromModelToUserGetDto(findUserById(id));
    }

    @Override
    public UserGetDto addNewUser(UserCreateDto user) throws UserEmailDuplicateException, UserPhoneNumberDuplicateException {
        checkIfUserExistsByEmail(user.email());
        checkIfUserExistsByPhoneNumber(user.phoneNumber());
        User userSaved = userRepository.save(fromUserCreateDtoToModel(user));
        emailService.sendWelcomeMail(userSaved);
        return fromModelToUserGetDto(userSaved);
    }

    @Override
    public UserGetDto updateUser(Long id, UserUpdateDto user)
            throws UserNotFoundException, UserEmailDuplicateException, UserPhoneNumberDuplicateException {
        User userToUpdate = findUserById(id);
        checkUserDuplicates(user, userToUpdate);
        updateUserFields(user, userToUpdate);
        return fromModelToUserGetDto(userRepository.save(userToUpdate));
    }

    @Override
    public String deleteUser(Long id) throws UserNotFoundException {
        findUserById(id);
        userRepository.deleteById(id);
        return USER_WITH_ID + id + DELETE_SUCCESS;
    }

    private void checkUserDuplicates(UserUpdateDto user, User userToUpdate) throws UserEmailDuplicateException, UserPhoneNumberDuplicateException {
        if(!user.email().equals(userToUpdate.getEmail())){
            checkIfUserExistsByEmail(user.email());
        }
        if(!user.phoneNumber().equals(userToUpdate.getPhoneNumber())){
            checkIfUserExistsByPhoneNumber(user.phoneNumber());
        }
    }

    private void updateUserFields(UserUpdateDto user, User userToUpdate) {
        updateIfChanged(user::firstName, userToUpdate::getFirstName, userToUpdate::setFirstName);
        updateIfChanged(user::lastName, userToUpdate::getLastName, userToUpdate::setLastName);
        updateIfChanged(user::phoneCountryCode, userToUpdate::getPhoneCountryCode, userToUpdate::setPhoneCountryCode);
        updateIfChanged(() -> new Address(user.street(), user.city(), user.state(), user.postalCode()),
                userToUpdate::getAddress, userToUpdate::setAddress);
    }

    private User findUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private void checkIfUserExistsByEmail(String email) throws UserEmailDuplicateException {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserEmailDuplicateException(email);
        }
    }

    private void checkIfUserExistsByPhoneNumber(Integer phoneNumber) throws UserPhoneNumberDuplicateException {
        if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new UserPhoneNumberDuplicateException(phoneNumber);
        }
    }
}
