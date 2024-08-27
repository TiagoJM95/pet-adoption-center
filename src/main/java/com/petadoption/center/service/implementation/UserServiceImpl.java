package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.UserConverter;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.db.DatabaseConnectionException;
import com.petadoption.center.exception.user.UserEmailDuplicateException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.exception.user.UserPhoneNumberDuplicateException;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.repository.UserRepository;
import com.petadoption.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.petadoption.center.converter.UserConverter.fromModelToUserGetDto;
import static com.petadoption.center.converter.UserConverter.fromUserCreateDtoToModel;
import static com.petadoption.center.util.FieldUpdater.updateIfChanged;
import static com.petadoption.center.util.FieldUpdater.updateIfChangedCheckDuplicates;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserGetDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserConverter::fromModelToUserGetDto).toList();
    }

    @Override
    public UserGetDto getUserById(Long id) throws UserNotFoundException {
        return fromModelToUserGetDto(findUserById(id));
    }

    @Override
    public UserGetDto addNewUser(UserCreateDto user) throws UserEmailDuplicateException, UserPhoneNumberDuplicateException, DatabaseConnectionException {
        //checkDbConnection();
        checkIfUserExistsByEmail(user.email());
        checkIfUserExistsByPhoneNumber(user.phoneNumber());
        return fromModelToUserGetDto(userRepository.save(fromUserCreateDtoToModel(user)));
    }

    @Override
    public UserGetDto updateUser(Long id, UserUpdateDto user)
            throws UserNotFoundException, UserEmailDuplicateException, UserPhoneNumberDuplicateException {
        User userToUpdate = findUserById(id);
        updateIfChangedCheckDuplicates(user::email, userToUpdate::getEmail,
                userToUpdate::setEmail, checkIfUserExistsByEmail(user.email()));

        updateIfChangedCheckDuplicates(user::phoneNumber, userToUpdate::getPhoneNumber,
                userToUpdate::setPhoneNumber,checkIfUserExistsByPhoneNumber(user.phoneNumber()));

        updateUserFields(user, userToUpdate);
        updateAddressFields(user, userToUpdate);
        return fromModelToUserGetDto(userRepository.save(userToUpdate));
    }

    private void updateUserFields(UserUpdateDto user, User userToUpdate) {
        updateIfChanged(user::firstName, userToUpdate::getFirstName, userToUpdate::setFirstName);
        updateIfChanged(user::lastName, userToUpdate::getLastName, userToUpdate::setLastName);
        updateIfChanged(user::phoneCountryCode, userToUpdate::getPhoneCountryCode, userToUpdate::setPhoneCountryCode);
    }

    private void updateAddressFields(UserUpdateDto user, User userToUpdate) {
        Address addressToUpdate = userToUpdate.getAddress();
        updateIfChanged(user::street, addressToUpdate::getStreet, addressToUpdate::setStreet);
        updateIfChanged(user::city, addressToUpdate::getCity, addressToUpdate::setCity);
        updateIfChanged(user::state, addressToUpdate::getState, addressToUpdate::setState);
        updateIfChanged(user::postalCode, addressToUpdate::getPostalCode, addressToUpdate::setPostalCode);
    }

    private User findUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private Runnable checkIfUserExistsByEmail(String email) throws UserEmailDuplicateException {
        Optional<User> userEmail = userRepository.findByEmail(email);
        if (userEmail.isPresent()) {
            throw new UserEmailDuplicateException(email);
        }
        return null;
    }

    private Runnable checkIfUserExistsByPhoneNumber(Integer phoneNumber) throws UserPhoneNumberDuplicateException {
        Optional<User> userPhoneNumber = userRepository.findByPhoneNumber(phoneNumber);
        if (userPhoneNumber.isPresent()) {
            throw new UserPhoneNumberDuplicateException(phoneNumber);
        }
        return null;
    }

}
