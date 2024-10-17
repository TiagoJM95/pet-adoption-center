package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import com.petadoption.center.aspect.Error;


import java.util.stream.Stream;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.util.Messages.USER_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UserControllerTest extends TestContainerConfig{

    private final String GET_OR_CREATE = URL + "user/";
    private final String GET_BY_ID = URL + "user/id/{id}";
    private final String UPDATE = URL + "user/update/{id}";
    private final String DELETE = URL + "user/delete/{id}";

    private static UserCreateDto userCreateDto;
    private static UserUpdateDto userUpdateDto;
    private String userId;

    @BeforeAll
    static void setUp() {
        userCreateDto = userCreateDto();
        userUpdateDto = userUpdateDto();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    static Stream<Arguments> userCreateDtoProvider() {

        UserCreateDto baseUser = otherUserCreateDto();

        return Stream.of(
                Arguments.of(baseUser.toBuilder().email("user@email.com").build(), "repeated email", "uniqueuseremail"),
                Arguments.of(baseUser.toBuilder().nif("987654321").build(), "repeated nif", "uniqueusernif"),
                Arguments.of(baseUser.toBuilder().phoneNumber("987654321").build(), "repeated phone number", "uniqueuserphonenumber"));
    }

    static Stream<Arguments> userUpdateDtoProvider() {

        UserUpdateDto baseUser = otherUserUpdateDto();

        return Stream.of(
                Arguments.of(baseUser.toBuilder().email("user@email.com").build(), "repeated email", "uniqueuseremail"),
                Arguments.of(baseUser.toBuilder().phoneNumber("987654321").build(), "repeated phone number", "uniqueuserphonenumber"));
    }

    private UserGetDto persistUser() throws Exception {

        var result = mockMvc.perform(post(GET_OR_CREATE)
                        .content(objectMapper.writeValueAsString(userCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        UserGetDto createResultDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto.class);
        userId = createResultDto.id();
        return createResultDto;
    }

    private void persistUserToUpdate() throws Exception {

        var result = mockMvc.perform(post(GET_OR_CREATE)
                        .content(objectMapper.writeValueAsString(otherUserCreateDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        UserGetDto createResultDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto.class);
        userId = createResultDto.id();
    }

    @Test
    @DisplayName("Test if create user return the created user in userGetDto")
    void createUserReturnUserGetDto() throws Exception {

        UserGetDto expectedUserGetDto = UserGetDto.builder()
                .firstName(userCreateDto.firstName())
                .lastName(userCreateDto.lastName())
                .email(userCreateDto.email())
                .nif(userCreateDto.nif())
                .dateOfBirth(userCreateDto.dateOfBirth())
                .address(userCreateDto.address())
                .phoneNumber(userCreateDto.phoneNumber())
                .build();

        UserGetDto userCreatedGetDto = persistUser();

        assertThat(userCreatedGetDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(expectedUserGetDto);

        assertNotNull(userCreatedGetDto.createdAt());
        assertTrue(userCreatedGetDto.id().matches("^[0-9a-fA-F-]{36}$"));
    }

    @ParameterizedTest(name = "Test {index}: Creating user with {1}")
    @MethodSource("userCreateDtoProvider")
    @DisplayName("Test if create user with duplicated fields of an existing user throws DataIntegrityViolationException")
    void createUserThrowsDataIntegrityException(UserCreateDto userCreateDto, String fieldBeingTested, String constraint) throws Exception {

        persistUser();

        var result = mockMvc.perform(post(GET_OR_CREATE)
                        .content(objectMapper.writeValueAsString(userCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertEquals(error.constraint(), constraint);

    }

    @Test
    @DisplayName("Test if create user throw HttpMessageNotReadableException if request body is empty")
    void shouldThrowHttpMessageNotReadableException_WhenCreateIsCalledWithNoBody() throws Exception {

        var result = mockMvc.perform(post(GET_OR_CREATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertTrue(error.message().contains("Required request body is missing"));
    }

    @Test
    @DisplayName("Test if get all returns a list of UserGetDto")
    void getAllUsersReturnsListOfUserGetDto() throws Exception {

        UserGetDto userCreatedGetDto = persistUser();

        var result = mockMvc.perform(get(GET_OR_CREATE)
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
    @DisplayName("Test if get all users pagination return the number of elements requested")
    void getAllReturnNumberOfElementsOfRequest() throws Exception {

        UserGetDto firstUser = persistUser();
        persistUserToUpdate();

        var result = mockMvc.perform(get(GET_OR_CREATE)
                        .param("page", "0")
                        .param("size", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserGetDto[] userGetDtoArray = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto[].class);
        assertThat(userGetDtoArray).hasSize(1);
        assertThat(userGetDtoArray[0])
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(firstUser);
    }

    @Test
    @DisplayName("Test if get all users return empty list if no users in database")
    void getAllReturnEmptyList() throws Exception {

        var result = mockMvc.perform(get(GET_OR_CREATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserGetDto[] userGetDtoArray = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto[].class);
        assertThat(userGetDtoArray).isEmpty();
    }

    @Test
    @DisplayName("Test if get user by id return the userGetDto of the requested user")
    void getUserByIdReturnUserGetDtoOfRequestedUser() throws Exception {

        UserGetDto userCreatedGetDto = persistUser();

        var result = mockMvc.perform(get(GET_BY_ID, userId)
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
    @DisplayName("Test if get user by id throws exception user not found if user does not exist")
    void getUserByIdShouldThrowExceptionIfUserDoesNotExist() throws Exception {

        mockMvc.perform(get(GET_BY_ID, "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
  
    @Test
    @DisplayName("Test if update user changes fields of the requested user and return the updated user in userGetDto")
    void updateUserChangesFieldsOfRequestedUserAndReturnUpdatedUserGetDto() throws Exception {

        UserGetDto expectedUpdatedUserGetDto = UserGetDto.builder()
                .firstName(userUpdateDto.firstName())
                .lastName(userUpdateDto.lastName())
                .email(userUpdateDto.email())
                .address(userUpdateDto.address())
                .phoneNumber(userUpdateDto.phoneNumber())
                .build();

        persistUser();

        var result = mockMvc.perform(put(UPDATE, userId)
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

    @ParameterizedTest(name = "Test {index}: updating user with {1}")
    @MethodSource("userUpdateDtoProvider")
    @DisplayName("Test if update user throws DataIntegrityViolationException if duplicated fields of an existing user")
    void updateUserThrowsDataIntegrityException(UserUpdateDto userUpdateDto, String fieldBeingTested, String constraint) throws Exception {

        persistUser();

        persistUserToUpdate();

       var result = mockMvc.perform(put(UPDATE, userId)
                        .content(objectMapper.writeValueAsString(userUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertEquals(error.constraint(), constraint);
    }

    @Test
    @DisplayName("Test if update user throws exception user not found if user does not exist")
    void updateUserShouldThrowExceptionIfUserDoesNotExist() throws Exception {

        mockMvc.perform(put(UPDATE, "11111-11111-1111-1111")
                        .content(objectMapper.writeValueAsString(userUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Test if delete user removes user from database and return message of success")
    void deleteUserRemovesUserAndReturnSuccessMessage() throws Exception {

        persistUser();

        mockMvc.perform(delete(DELETE, userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(USER_DELETE_MESSAGE, userId)));
    }

    @Test
    @DisplayName("Test if delete user throws exception user not found if user does not exist")
    void deleteUserShouldThrowExceptionIfUserDoesNotExist() throws Exception {

        mockMvc.perform(delete(DELETE, "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}