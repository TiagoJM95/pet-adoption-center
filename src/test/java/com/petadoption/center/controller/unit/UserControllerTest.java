package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.UserController;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.not_found.UserNotFoundException;
import com.petadoption.center.service.interfaces.UserServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

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
        userGetDto = userGetDto();
        userCreateDto = userCreateDto();
        userUpdateDto = userUpdateDto();
    }

    @Test
    @DisplayName("Test if get all users works correctly")
    void getAllUsersShouldReturn() {

        int page = 0;
        int size = 10;
        String sort = "created_at";
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        List<UserGetDto> expectedUsers = List.of(userGetDto);

        when(userServiceI.getAll(pageable)).thenReturn(expectedUsers);

        ResponseEntity<List<UserGetDto>> response = userController.getAll(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUsers, response.getBody());
        verify(userServiceI).getAll(pageable);
    }

    @Test
    @DisplayName("Test if get user by id works correctly")
    void getUserByIdShouldReturn() throws UserNotFoundException {

        String id = "1111-1111-2222";

        when(userServiceI.getById(id)).thenReturn(userGetDto);

        ResponseEntity<UserGetDto> response = userController.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userGetDto, response.getBody());
        verify(userServiceI).getById(id);

    }

    @Test
    @DisplayName("Test if add new user works correctly")
    void create() {

        when(userServiceI.create(userCreateDto)).thenReturn(userGetDto);

        ResponseEntity<UserGetDto> response = userController.create(userCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userGetDto, response.getBody());
        verify(userServiceI).create(userCreateDto);
    }

    @Test
    @DisplayName("Test if update user works correctly")
    void updateUserShouldReturn() throws UserNotFoundException {

        when(userServiceI.update(userGetDto.id(), userUpdateDto)).thenReturn(userGetDto);

        ResponseEntity<UserGetDto> response = userController.update(userGetDto.id(), userUpdateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userGetDto, response.getBody());
        verify(userServiceI).update(userGetDto.id(), userUpdateDto);
    }

    @Test
    @DisplayName("Test if delete user works correctly")
    void deleteUserShouldReturn() throws UserNotFoundException {

        when(userServiceI.delete(userGetDto.id())).thenReturn(USER_WITH_ID + userGetDto.id() + DELETE_SUCCESS);

        ResponseEntity<String> response = userController.delete(userGetDto.id());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(USER_WITH_ID + userGetDto.id() + DELETE_SUCCESS, response.getBody());
        verify(userServiceI).delete(userGetDto.id());
    }

}