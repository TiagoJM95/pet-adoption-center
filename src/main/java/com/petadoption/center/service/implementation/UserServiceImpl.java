package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.UserConverter;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.user.UserAlreadyExistsException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.repository.UserRepository;
import com.petadoption.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserGetDto> getAllUsers() {
        List<User> usersList = userRepository.findAll().stream().toList();
        return usersList.stream().map(UserConverter::fromModelToUserGetDto).toList();
    }

    @Override
    public UserGetDto getUserById(Long id) throws UserNotFoundException {
        VerifyIfUserExists(id);
        return UserConverter.fromModelToUserGetDto(VerifyIfUserExists(id).get());
    }

    @Override
    public UserGetDto addNewUser(UserCreateDto user) throws UserAlreadyExistsException {
        verifyIfUserAlreadyExists(user);
        User userToSave = UserConverter.fromUserCreateDtoToModel(user);
        userRepository.save(userToSave);
        return UserConverter.fromModelToUserGetDto(userToSave);
    }

    @Override
    public UserGetDto updateUser(Long id, UserUpdateDto user) throws UserNotFoundException, UserAlreadyExistsException {
        User userToUpdate = VerifyIfUserExists(id).get();
        Address addressToUpdate = userToUpdate.getAddress();
        if(user.firstName() != null && !user.firstName().isEmpty() && !user.firstName().equals(userToUpdate.getFirstName())){
            userToUpdate.setFirstName(user.firstName());
        }
        if(user.lastName() != null && !user.lastName().isEmpty() && !user.lastName().equals(userToUpdate.getLastName())){
            userToUpdate.setLastName(user.lastName());
        }
        if(user.email() != null && !user.email().isEmpty() && !user.email().equals(userToUpdate.getEmail())){
            Optional<User> userEmail = userRepository.findByEMail(user.email());
            if(userEmail.isPresent()){
                throw new UserAlreadyExistsException("User with email: " + user.email() + " already exists");
            }
            userToUpdate.setEmail(user.email());
        }
        if(user.email() != null && !user.email().isEmpty() && !user.email().equals(userToUpdate.getEmail())){
            Optional<User> userEmail = userRepository.findByEMail(user.email());
            if(userEmail.isPresent()){
                throw new UserAlreadyExistsException("User with email: " + user.email() + " already exists");
            }
            userToUpdate.setEmail(user.email());
        }
        if(user.street() != null && !user.street().isEmpty() && !user.street().equals(addressToUpdate.getStreet())){
            addressToUpdate.setStreet(user.street());
        }
        if(user.city() != null && !user.city().isEmpty() && !user.city().equals(addressToUpdate.getCity())){
            addressToUpdate.setCity(user.city());
        }
        if(user.state() != null && !user.state().isEmpty() && !user.state().equals(addressToUpdate.getState())){
            addressToUpdate.setState(user.state());
        }
        if(user.postalCode() != null && !user.postalCode().isEmpty() && !user.postalCode().equals(addressToUpdate.getPostalCode())){
            addressToUpdate.setPostalCode(user.postalCode());
        }
        if(user.phoneCountryCode() != null && !user.phoneCountryCode().isEmpty() && !user.phoneCountryCode().equals(userToUpdate.getPhoneCountryCode())){
            userToUpdate.setPhoneCountryCode(user.phoneCountryCode());
        }
        if(user.phoneNumber() != null && !user.phoneNumber().equals(userToUpdate.getPhoneNumber())){
            userToUpdate.setPhoneNumber(user.phoneNumber());
        }
        return UserConverter.fromModelToUserGetDto(userToUpdate);
    }

    private Optional<User> VerifyIfUserExists(Long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("user with " + id + " doesn't exists");
        }
        return userOptional;
    }

    private void verifyIfUserAlreadyExists(UserCreateDto user) throws UserAlreadyExistsException {
        Optional<User> userEmail = userRepository.findByEMail(user.email());
        Optional<User> userPhoneNumber = userRepository.findByPhoneNumber(user.phoneNumber());
        if(userEmail.isPresent()){
            throw new UserAlreadyExistsException("User with email: " + user.email() + " already exists");
        }
        if(userPhoneNumber.isPresent()){
            throw new UserAlreadyExistsException("User with phone number: " + user.phoneNumber() + " already exists" );
        }
    }


}
