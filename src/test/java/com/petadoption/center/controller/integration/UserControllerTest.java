package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import com.petadoption.center.aspect.Error;


import java.util.stream.Stream;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.util.Messages.USER_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends TestContainerConfig{

    @Autowired
    UserService userService;

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
                Arguments.of(baseUser.toBuilder().nif("287654321").build(), "repeated nif", "uniqueusernif"),
                Arguments.of(baseUser.toBuilder().phoneNumber("917654321").build(), "repeated phone number", "uniqueuserphonenumber"));
    }

    static Stream<Arguments> userUpdateDtoProvider() {

        UserUpdateDto baseUser = otherUserUpdateDto();

        return Stream.of(
                Arguments.of(baseUser.toBuilder().email("user@email.com").build(), "repeated email", "uniqueuseremail"),
                Arguments.of(baseUser.toBuilder().phoneNumber("917654321").build(), "repeated phone number", "uniqueuserphonenumber"));
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

    private void persistUserToUpdate() throws Exception {

        var result = mockMvc.perform(post("/api/v1/user/")
                        .content(objectMapper.writeValueAsString(otherUserCreateDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        UserGetDto createResultDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto.class);
        userId = createResultDto.id();
    }

    @Test
    @DisplayName("Test if create user works correctly")
    void createUserShouldReturnUser() throws Exception {

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
    @DisplayName("Test if create user throws DataIntegrityViolationException")
    void createUserThrowsDataIntegrityException(UserCreateDto userCreateDto, String fieldBeingTested, String constraint) throws Exception {

        persistUser();

        var result = mockMvc.perform(post("/api/v1/user/")
                        .content(objectMapper.writeValueAsString(userCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertEquals(error.constraint(), constraint);

    }

    @Test
    @DisplayName("Test if throw HttpMessageNotReadableException if request body is empty")
    void shouldThrowHttpMessageNotReadableException_WhenCreateIsCalledWithNoBody() throws Exception {

        var result = mockMvc.perform(post("/api/v1/user/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertTrue(error.message().contains("Required request body is missing"));
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

        UserGetDto expectedUpdatedUserGetDto = UserGetDto.builder()
                .firstName(userUpdateDto.firstName())
                .lastName(userUpdateDto.lastName())
                .email(userUpdateDto.email())
                .address(userUpdateDto.address())
                .phoneNumber(userUpdateDto.phoneNumber())
                .build();

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

    @ParameterizedTest(name = "Test {index}: updating user with {1}")
    @MethodSource("userUpdateDtoProvider")
    @DisplayName("Test if update user throws DataIntegrityViolationException")
    void updateUserThrowsDataIntegrityException(UserUpdateDto userUpdateDto, String fieldBeingTested, String constraint) throws Exception {

        persistUser();

        persistUserToUpdate();

       var result = mockMvc.perform(put("/api/v1/user/update/{id}", userId)
                        .content(objectMapper.writeValueAsString(userUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertEquals(error.constraint(), constraint);
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

    @Test
    @DisplayName("Test if get user by id is in cache when called a second time")
    void testIfRequestIsCachedWhenGettingUserByIdASecondTime() throws Exception {
        persistUser();

        userService.getById(userId);
        userService.getById(userId);

        Long dbAccessCount = userRepository.count();
        assertEquals(1, dbAccessCount);
    }

    @Test
    @DisplayName("Test if getting a user by id gets retrieved from cache after the first time it's called")
    void testIfGetUserByIdIsRetrievedFromCacheAfterTheFirstTimeItsCalled() throws Exception {

        persistUser();

        UserGetDto result = userService.getById(userId);
        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);

        int queryTimes = 50;
        for (int i = 1; i < queryTimes; i++) {
            result = userService.getById(userId);
            assertNotNull(result);
        }

        verify(userRepository, times(1)).findById(userId);
    }
}