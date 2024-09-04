package com.petadoption.center.service;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.user.UserEmailDuplicateException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.exception.user.UserPhoneNumberDuplicateException;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.repository.UserRepository;
import com.petadoption.center.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest {


    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User testUser;
    private User updatedUser;
    private UserCreateDto userCreateDto;
    private UserUpdateDto userUpdateDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("Manuel");
        testUser.setLastName("Silva");
        testUser.setEmail("email@email.com");
        testUser.setDateOfBirth(LocalDate.of(1990, 10, 25));
        testUser.setPhoneCountryCode("+351");
        testUser.setPhoneNumber(911234567);
        testUser.setAddress(new Address("Rua dos animais, 123", "Gondomar", "Porto", "4400-000"));

          userCreateDto = new UserCreateDto(
                "Manuel",
                "Silva",
                "email@email.com",
                LocalDate.of(1990, 10, 25),
                "Rua dos animais, 123", "Gondomar",
                "Porto",
                "4400-000",
                "+351",
                912354678);
          userUpdateDto = new UserUpdateDto("Tiago",
                "Moreira",
                "tm@email.com",
                "Rua dos bandidos, 123",
                "Rio Tinto",
                "Porto",
                "4100-001",
                "+351",
                934587967);

        updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setFirstName("Tiago");
        updatedUser.setLastName("Moreira");
        updatedUser.setEmail("tm@email.com");
        updatedUser.setDateOfBirth(LocalDate.of(1990, 10, 25));
        updatedUser.setPhoneCountryCode("+351");
        updatedUser.setPhoneNumber(934587967);
        updatedUser.setAddress(new Address("Rua dos animais, 123", "Rio Tinto", "Porto", "4100-001"));

    }

    @Test
    @DisplayName("Test if get all users works correctly")
    void getAllUsersShouldReturnListOfUsers() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<User> pagedUsers = new PageImpl<>(List.of(testUser));

        when(userRepository.findAll(pageRequest)).thenReturn(pagedUsers);

        List<UserGetDto> result = userService.getAllUsers(0, 10, "id");

        assertEquals(1, result.size());
        assertEquals(testUser.getEmail(), result.getFirst().email());
    }


    @Test
    @DisplayName("Test if get user by id works correctly")
    void getUserByIdShouldReturnUser() throws UserNotFoundException {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(testUser));

            UserGetDto result = userService.getUserById(id);
            assertEquals(testUser.getEmail(), result.email());
    }

    @Test
    @DisplayName("Test if get user by id throws UserNotFoundException")
    void getUserByIdShouldThrowUserNotFoundException() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(id));
    }

    @Test
    @DisplayName("Test if add new user saves and returns UserGetDto")
    void addNewUserShouldSaveAndReturnUserGetDto() throws UserEmailDuplicateException, UserPhoneNumberDuplicateException {

        when(userRepository.save(any(User.class))).thenReturn(testUser);
        UserGetDto result = userService.addNewUser(userCreateDto);

        assertNotNull(userCreateDto);
        assertEquals(userCreateDto.email(), result.email());

   }


    @Test
    @DisplayName("Test if throw exception if email is duplicated")
    void addNewUserShouldThrowUserEmailDuplicateException() {

        when(userRepository.findByEmail(userCreateDto.email())).thenReturn(Optional.of(testUser));

        assertThrows(UserEmailDuplicateException.class, () -> userService.addNewUser(userCreateDto));
    }

    @Test
    @DisplayName("Test if throw exception if phone number is duplicated")
    void addNewUserShouldThrowUserPhoneNumberDuplicateException() {

        when(userRepository.findByPhoneNumber(userCreateDto.phoneNumber())).thenReturn(Optional.of(testUser));

        assertThrows(UserPhoneNumberDuplicateException.class, () -> userService.addNewUser(userCreateDto));
    }

    @Test
    @DisplayName("Test if update user saves all fields and returns UserGetDto")
    void updateUserShouldSaveAllFieldsAndReturnUserGetDto() throws UserNotFoundException, UserEmailDuplicateException, UserPhoneNumberDuplicateException {

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        UserGetDto result = userService.updateUser(testUser.getId(), userUpdateDto);

        assertNotNull(userUpdateDto);
        assertEquals(userUpdateDto.email(), result.email());
    }


    //    @Test
//    @DisplayName("Test if validator works correctly")
//    void testIfAcceptNullFirstName() {
//        UserCreateDto userCreateDto = new UserCreateDto(
//                null,
//                "Guedes",
//                "teste@email.com",
//                LocalDate.of(1990, 10, 25),
//                "Rua das Andorinhas, 123",
//                "Vila Nova de Gaia",
//                "Porto",
//                "4410-000",
//                "+351",
//                912345678);
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
//        Set<ConstraintViolation<UserCreateDto>> violations = validator.validate(userCreateDto);
//
//        assertFalse(violations.isEmpty());
//
//        boolean hasFirstNameViolation = violations.stream()
//                .anyMatch(violation -> violation.getPropertyPath().toString().equals("firstName"));
//        assertTrue(hasFirstNameViolation, "There should be a violation for the 'firstName' field.");
//    }
}
