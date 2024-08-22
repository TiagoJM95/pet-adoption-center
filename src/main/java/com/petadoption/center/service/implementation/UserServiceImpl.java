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
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static com.petadoption.center.converter.UserConverter.fromModelToUserGetDto;
import static com.petadoption.center.converter.UserConverter.fromUserCreateDtoToModel;
import static com.petadoption.center.util.Utils.checkDbConnection;

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
    public UserGetDto updateUser(Long id, UserUpdateDto user) throws UserNotFoundException, UserEmailDuplicateException, UserPhoneNumberDuplicateException {
        User userToUpdate = findUserById(id);
        if(!userToUpdate.getEmail().equals(user.email())){
            checkIfUserExistsByEmail(user.email());
        }
        if(!userToUpdate.getPhoneNumber().equals(user.phoneNumber())){
            checkIfUserExistsByPhoneNumber(user.phoneNumber());
        }
        updateUserFields(user, userToUpdate);
        updateAddressFields(user, userToUpdate);

        return fromModelToUserGetDto(userRepository.save(userToUpdate));
    }

    private void updateUserFields(UserUpdateDto user, User userToUpdate) {
        updateFieldIfChanged(user.firstName(), userToUpdate.getFirstName(), userToUpdate::setFirstName);
        updateFieldIfChanged(user.lastName(), userToUpdate.getLastName(), userToUpdate::setLastName);
        updateFieldIfChanged(user.email(), userToUpdate.getEmail(), userToUpdate::setEmail);
        updateFieldIfChanged(user.phoneNumber(), userToUpdate.getPhoneNumber(), userToUpdate::setPhoneNumber);
        updateFieldIfChanged(user.phoneCountryCode(), userToUpdate.getPhoneCountryCode(), userToUpdate::setPhoneCountryCode);
    }

    private void updateAddressFields(UserUpdateDto user, User userToUpdate){
        Address addressToUpdate = userToUpdate.getAddress();
        updateFieldIfChanged(user.street(), addressToUpdate.getStreet(), addressToUpdate::setStreet);
        updateFieldIfChanged(user.city(), addressToUpdate.getCity(), addressToUpdate::setCity);
        updateFieldIfChanged(user.state(), addressToUpdate.getState(), addressToUpdate::setState);
        updateFieldIfChanged(user.postalCode(), addressToUpdate.getPostalCode(), addressToUpdate::setPostalCode);
    }

    private <T> void updateFieldIfChanged(T newValue, T oldValue, Consumer<T> updateField) {
        if (!newValue.equals(oldValue) && !newValue.equals("")) {
            updateField.accept(newValue);
        }
    }

    private User findUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private void checkIfUserExistsByEmail(String email) throws UserEmailDuplicateException {
        Optional<User> userEmail = userRepository.findByEmail(email);
        if (userEmail.isPresent()) {
            throw new UserEmailDuplicateException(email);
        }
    }

    private void checkIfUserExistsByPhoneNumber(Integer phoneNumber) throws UserPhoneNumberDuplicateException {
        Optional<User> userPhoneNumber = userRepository.findByPhoneNumber(phoneNumber);
        if (userPhoneNumber.isPresent()) {
            throw new UserPhoneNumberDuplicateException(phoneNumber);
        }
    }

}
