package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.UserController;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.user.UserEmailDuplicateException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.exception.user.UserPhoneNumberDuplicateException;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.service.UserService;
import com.petadoption.center.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.USER_WITH_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserGetDto userGetDto;
    private UserCreateDto userCreateDto;
    private UserUpdateDto userUpdateDto;

    @BeforeEach
    void setUp() {
        userGetDto = new UserGetDto(
                1L,
                "Manuel",
                "Silva",
                "email@email.com",
                LocalDate.of(1990, 10, 25),
                new Address("Rua das Andorinhas, 123",
                        "Vila Nova de Gaia",
                        "Porto",
                        "4410-000"),
                "+351",
                123456789,
                null,
                null,
                null
        );

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

    }

    @Test
    @DisplayName("Test if get all users works correctly")
    void getAllUsersShouldReturnUsers() {

        int page = 0;
        int size = 5;
        String sortBy = "id";

        List<UserGetDto> expectedUsers = List.of(userGetDto);

        when(userService.getAllUsers(page, size, sortBy)).thenReturn(expectedUsers);

        ResponseEntity<List<UserGetDto>> response = userController.getAllUsers(page, size, sortBy);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUsers, response.getBody());
        verify(userService).getAllUsers(page, size, sortBy);
    }

    @Test
    @DisplayName("Test if get user by id works correctly")
    void getUserByIdShouldReturnUser() throws UserNotFoundException {

        Long id = 1L;

        when(userService.getUserById(id)).thenReturn(userGetDto);

        ResponseEntity<UserGetDto> response = userController.getUserById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userGetDto, response.getBody());
        verify(userService).getUserById(id);

    }

    @Test
    @DisplayName("Test if add new user works correctly")
    void addNewUserShouldReturnUser() throws UserEmailDuplicateException, UserPhoneNumberDuplicateException {

        when(userService.addNewUser(userCreateDto)).thenReturn(userGetDto);

        ResponseEntity<UserGetDto> response = userController.addNewUser(userCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userGetDto, response.getBody());
        verify(userService).addNewUser(userCreateDto);
    }

    @Test
    @DisplayName("Test if update user works correctly")
    void updateUserShouldReturnUser() throws UserNotFoundException, UserEmailDuplicateException, UserPhoneNumberDuplicateException {

        when(userService.updateUser(1L, userUpdateDto)).thenReturn(userGetDto);

        ResponseEntity<UserGetDto> response = userController.updateUser(1L, userUpdateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userGetDto, response.getBody());
        verify(userService).updateUser(1L, userUpdateDto);
    }

    @Test
    @DisplayName("Test if delete user works correctly")
    void deleteUserShouldReturnUser() throws UserNotFoundException {

        when(userService.deleteUser(1L)).thenReturn(USER_WITH_ID + 1L + DELETE_SUCCESS);

        ResponseEntity<String> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(USER_WITH_ID + 1L + DELETE_SUCCESS, response.getBody());
        verify(userService).deleteUser(1L);
    }

}
