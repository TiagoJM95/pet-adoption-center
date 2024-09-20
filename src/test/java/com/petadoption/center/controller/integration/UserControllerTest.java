package com.petadoption.center.controller.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.model.embeddable.Address;
import jakarta.transaction.Transactional;
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

import java.time.LocalDate;

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
    private UserCreateDto userCreateDto;
    private UserUpdateDto userUpdateDto;
    private Address createAddress;
    private Address updateAddress;

    private String userId;

    @BeforeEach
    void setUp() {
        createAddress = new Address("Rua das Andorinhas, 123",
                "Vila Nova de Gaia",
                "Porto",
                "4410-000");

        updateAddress = new Address("Rua das Gaivotas, 456",
                "Vila Nova de Gaia",
                "Porto",
                "4410-000");

        userCreateDto = UserCreateDto.builder()
                .firstName("Manuel")
                .lastName("Silva")
                .email("email@email.com")
                .nif("123456789")
                .dateOfBirth(LocalDate.of(1990, 10, 25))
                .address(createAddress)
                .phoneNumber("123456789")
                .build();

        userUpdateDto = new UserUpdateDto(
                "Tiago",
                "Moreira",
                "tm@email.com",
                updateAddress,
                "934587967");

    }


    @Test
    @DisplayName("Test if create user works correctly")
    @DirtiesContext
    void createUserShouldReturnUser() throws Exception {

       var result = mockMvc.perform(post("/api/v1/user/")
                        .content(objectMapper.writeValueAsString(userCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(userCreateDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userCreateDto.lastName())))
                .andReturn();

       UserGetDto userCreated = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto.class);

       userId = userCreated.id();

       userGetDto = new UserGetDto(
               userId,
               userCreateDto.firstName(),
               userCreateDto.lastName(),
               userCreateDto.email(),
               userCreateDto.nif(),
               userCreateDto.dateOfBirth(),
               userCreateDto.address(),
               userCreateDto.phoneNumber()
       );

    }

    @Test
    @DisplayName("Test if get all users works correctly")
    @DirtiesContext
    void getAllUsersAfterCreatingUser() throws Exception {

        createUserShouldReturnUser();

        mockMvc.perform(get("/api/v1/user/")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "id")
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
    @DirtiesContext
    void getUserByIdShouldReturnUser() throws Exception {

        createUserShouldReturnUser();

        mockMvc.perform(get("/api/v1/user/id/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userGetDto.id())))
                .andExpect(jsonPath("$.firstName", is(userGetDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userGetDto.lastName())));
    }



    @Test
    @DisplayName("Test if update user works correctly")
    @DirtiesContext
    void updateUserShouldReturnUser() throws Exception {

        createUserShouldReturnUser();

        mockMvc.perform(put("/api/v1/user/update/{id}", userId)
                        .content(objectMapper.writeValueAsString(userUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(userUpdateDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userUpdateDto.lastName())));
    }

    @Test
    @DisplayName("Test if delete user works correctly")
    @DirtiesContext
    void deleteUserShouldReturnUser() throws Exception {

        createUserShouldReturnUser();

        mockMvc.perform(delete("/api/v1/user/delete/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(USER_WITH_ID + userId + DELETE_SUCCESS));
    }
}