package com.petadoption.center.controller.integration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.petadoption.center.testUtils.TestDtoFactory.userCreateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.userUpdateDto;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.USER_WITH_ID;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private UserGetDto userGetDto;
    private static UserCreateDto userCreateDto;
    private static UserUpdateDto userUpdateDto;

    @BeforeAll
    static void setUp() {
        userCreateDto = userCreateDto();
        userUpdateDto = userUpdateDto();
    }


    @Test
    @DisplayName("Test if create user works correctly")
    void createUserShouldReturnUser() throws Exception {

       var result = mockMvc.perform(post("/api/v1/user/")
                        .content(objectMapper.writeValueAsString(userCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(userCreateDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userCreateDto.lastName())))
                .andReturn();

       userGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto.class);

    }

    @Test
    @DisplayName("Test if get all users works correctly")
    void getAllAfterCreatingUser() throws Exception {

        createUserShouldReturnUser();

        mockMvc.perform(get("/api/v1/user/")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(userGetDto.id())))
                .andExpect(jsonPath("$[0].firstName", is(userGetDto.firstName())))
                .andExpect(jsonPath("$[0].lastName", is(userGetDto.lastName())))
                .andExpect(jsonPath("$[0].nif", is(userGetDto.nif())));
    }

    @Test
    @DisplayName("Test if get user by id works correctly")
    void getUserByIdShouldReturn() throws Exception {

        createUserShouldReturnUser();

        mockMvc.perform(get("/api/v1/user/id/{id}", userGetDto.id())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userGetDto.id())))
                .andExpect(jsonPath("$.firstName", is(userGetDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userGetDto.lastName())));
    }


    @Test
    @DisplayName("Test if get user by id throws exception user not found")
    void getUserByIdShouldThrowException() throws Exception {

        mockMvc.perform(get("/api/v1/user/id/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Test if update user works correctly")
    void updateUserShouldReturn() throws Exception {

        createUserShouldReturnUser();

        mockMvc.perform(put("/api/v1/user/update/{id}", userGetDto.id())
                        .content(objectMapper.writeValueAsString(userUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(userUpdateDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userUpdateDto.lastName())));
    }

    @Test
    @DisplayName("Test if update user throws exception user not found")
    void updateUserShouldThrowException() throws Exception {

        mockMvc.perform(put("/api/v1/user/update/{id}", "11111-11111-1111-1111")
                        .content(objectMapper.writeValueAsString(userUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Test if delete user works correctly")
    void deleteUserShouldReturn() throws Exception {

        createUserShouldReturnUser();

        mockMvc.perform(delete("/api/v1/user/delete/{id}", userGetDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(USER_WITH_ID + userGetDto.id() + DELETE_SUCCESS));
    }

    @Test
    @DisplayName("Test if delete user throws exception user not found")
    void deleteUserShouldThrowException() throws Exception {

        mockMvc.perform(delete("/api/v1/user/delete/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}