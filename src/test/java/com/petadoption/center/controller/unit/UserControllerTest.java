package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.UserController;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.user.UserDuplicateException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.service.interfaces.UserServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.USER_WITH_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Mock
    private UserServiceI userServiceI;

    @InjectMocks
    private UserController userController;

    private UserGetDto userGetDto;
    private UserCreateDto userCreateDto;
    private UserUpdateDto userUpdateDto;


    @BeforeEach
    void setUp() {
        userGetDto = createUserGetDto();
        userCreateDto = createUserCreateDto();
        userUpdateDto = createUserUpdateDto();
    }

    @Test
    @DisplayName("Test if get all users works correctly")
    void getAllUsersShouldReturnUsers() {

        int page = 0;
        int size = 5;
        String sortBy = "id";

        List<UserGetDto> expectedUsers = List.of(userGetDto);

        when(userServiceI.getAllUsers(page, size, sortBy)).thenReturn(expectedUsers);

        ResponseEntity<List<UserGetDto>> response = userController.getAllUsers(page, size, sortBy);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUsers, response.getBody());
        verify(userServiceI).getAllUsers(page, size, sortBy);
    }

    @Test
    @DisplayName("Test if get user by id works correctly")
    void getUserByIdShouldReturnUser() throws UserNotFoundException {

        String id = "1111-1111-2222";

        when(userServiceI.getUserById(id)).thenReturn(userGetDto);

        ResponseEntity<UserGetDto> response = userController.getUserById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userGetDto, response.getBody());
        verify(userServiceI).getUserById(id);

    }

    @Test
    @DisplayName("Test if add new user works correctly")
    void addNewUserShouldReturnUser() throws UserDuplicateException {

        when(userServiceI.addNewUser(userCreateDto)).thenReturn(userGetDto);

        ResponseEntity<UserGetDto> response = userController.addNewUser(userCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userGetDto, response.getBody());
        verify(userServiceI).addNewUser(userCreateDto);
    }

    @Test
    @DisplayName("Test if update user works correctly")
    void updateUserShouldReturnUser() throws UserNotFoundException, UserDuplicateException {

        when(userServiceI.updateUser(userGetDto.id(), userUpdateDto)).thenReturn(userGetDto);

        ResponseEntity<UserGetDto> response = userController.updateUser(userGetDto.id(), userUpdateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userGetDto, response.getBody());
        verify(userServiceI).updateUser(userGetDto.id(), userUpdateDto);
    }

    @Test
    @DisplayName("Test if delete user works correctly")
    void deleteUserShouldReturnUser() throws UserNotFoundException {

        when(userServiceI.deleteUser(userGetDto.id())).thenReturn(USER_WITH_ID + userGetDto.id() + DELETE_SUCCESS);

        ResponseEntity<String> response = userController.deleteUser(userGetDto.id());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(USER_WITH_ID + userGetDto.id() + DELETE_SUCCESS, response.getBody());
        verify(userServiceI).deleteUser(userGetDto.id());
    }

}