package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.petadoption.center.testUtils.TestDtoFactory.userCreateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.userUpdateDto;
import static com.petadoption.center.util.Messages.USER_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UserControllerTest extends TestContainerConfig{


    private UserGetDto userGetDto;
    private static UserCreateDto userCreateDto;
    private static UserUpdateDto userUpdateDto;
    private static UserGetDto expectedUserGetDto;
    private static UserGetDto expectedUpdatedUserGetDto;

    private String userId;

    @BeforeAll
    static void setUp() {
        userCreateDto = userCreateDto();
        userUpdateDto = userUpdateDto();
        expectedUserGetDto = UserGetDto.builder()
                .firstName(userCreateDto.firstName())
                .lastName(userCreateDto.lastName())
                .email(userCreateDto.email())
                .nif(userCreateDto.nif())
                .dateOfBirth(userCreateDto.dateOfBirth())
                .address(userCreateDto.address())
                .phoneNumber(userCreateDto.phoneNumber())
                .build();
        expectedUpdatedUserGetDto = UserGetDto.builder()
                .firstName(userUpdateDto.firstName())
                .lastName(userUpdateDto.lastName())
                .email(userUpdateDto.email())
                .address(userUpdateDto.address())
                .phoneNumber(userUpdateDto.phoneNumber())
                .build();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    private UserGetDto persistUser() throws Exception {

        var result = mockMvc.perform(post("/api/v1/user/")
                        .content(objectMapper.writeValueAsString(userCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        UserGetDto createResultDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto.class);
        userId = createResultDto.id();
        return createResultDto;
    }


    @Test
    @DisplayName("Test if create user works correctly")
    void createUserShouldReturnUser() throws Exception {

        UserGetDto userCreatedGetDto = persistUser();

        assertThat(userCreatedGetDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(expectedUserGetDto);

        assertNotNull(userCreatedGetDto.createdAt());
        assertTrue(userCreatedGetDto.id().matches("^[0-9a-fA-F-]{36}$"));
    }

    @Test
    @DisplayName("Test if get all users works correctly")
    void getAllAfterCreatingUser() throws Exception {

        UserGetDto userCreatedGetDto = persistUser();

        var result = mockMvc.perform(get("/api/v1/user/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserGetDto[] userGetDtoArray = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto[].class);
        assertThat(userGetDtoArray).hasSize(1);
        assertThat(userGetDtoArray[0])
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(userCreatedGetDto);

    }

    @Test
    @DisplayName("Test if get user by id works correctly")
    void getUserByIdShouldReturn() throws Exception {

        UserGetDto userCreatedGetDto = persistUser();

        var result = mockMvc.perform(get("/api/v1/user/id/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserGetDto getResultDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto.class);
        assertThat(getResultDto)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(userCreatedGetDto);
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

        persistUser();

        var result = mockMvc.perform(put("/api/v1/user/update/{id}", userId)
                        .content(objectMapper.writeValueAsString(userUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserGetDto updateResultDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto.class);
        assertThat(updateResultDto)
                .usingRecursiveComparison()
                .ignoringFields("id", "dateOfBirth", "nif")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(expectedUpdatedUserGetDto);


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

        persistUser();

        mockMvc.perform(delete("/api/v1/user/delete/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(USER_DELETE_MESSAGE, userId)));
    }

    @Test
    @DisplayName("Test if delete user throws exception user not found")
    void deleteUserShouldThrowException() throws Exception {

        mockMvc.perform(delete("/api/v1/user/delete/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}