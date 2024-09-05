package com.petadoption.center.controller.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.model.embeddable.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.USER_WITH_ID;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


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

        userUpdateDto = new UserUpdateDto(
                "Tiago",
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
    void getAllUsersShouldReturnUsers() throws Exception {

        mockMvc.perform(get("/api/v1/user/")
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].firstName", is(userGetDto.firstName())))
                .andExpect(jsonPath("$[0].lastName", is(userGetDto.lastName())));

    }

    @Test
    @DisplayName("Test if get user by id works correctly")
    void getUserByIdShouldReturnUser() throws Exception {

        mockMvc.perform(get("/api/v1/user/id/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userGetDto.id().intValue())))
                .andExpect(jsonPath("$.firstName", is(userGetDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userGetDto.lastName())));
    }

    @Test
    @DisplayName("Test if create user works correctly")
    void createUserShouldReturnUser() throws Exception {

        mockMvc.perform(post("/api/v1/user/")
                .content(objectMapper.writeValueAsString(userCreateDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(userGetDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userGetDto.lastName())));
    }

    @Test
    @DisplayName("Test if update user works correctly")
    void updateUserShouldReturnUser() throws Exception {

        mockMvc.perform(put("/api/v1/user/update/{id}", 1L)
                        .content(objectMapper.writeValueAsString(userUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(userUpdateDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userUpdateDto.lastName())));
    }

    @Test
    @DisplayName("Test if delete user works correctly")
    void deleteUserShouldReturnUser() throws Exception {

        mockMvc.perform(delete("/api/v1/user/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(USER_WITH_ID + 1L + DELETE_SUCCESS));
    }
}