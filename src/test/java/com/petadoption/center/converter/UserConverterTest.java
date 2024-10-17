package com.petadoption.center.converter;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static com.petadoption.center.testUtils.TestDtoFactory.userCreateDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserConverterTest {


    @Test
    @DisplayName("Test UserCreateDto to User model is working correctly")
    void fromUserCreateDtoToModel() {

        User user = UserConverter.toModel(userCreateDto());

        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("user@email.com", user.getEmail());
        assertEquals("287654321", user.getNif());
        assertEquals(LocalDate.of(1990, 1, 1), user.getDateOfBirth());
        assertEquals("Rua de Santo Antonio, 123", user.getAddress().getStreet());
        assertEquals("Gondomar", user.getAddress().getCity());
        assertEquals("Porto", user.getAddress().getState());
        assertEquals("4444-444", user.getAddress().getPostalCode());
        assertEquals("917654321", user.getPhoneNumber());

    }


    @Test
    @DisplayName("Test User model to UserGetDto is working correctly")
    void fromModelToUserGetDto() {

        UserGetDto userGetDto = UserConverter.toDto(createUser());

        assertEquals("999999-99999999-9999", userGetDto.id());
        assertEquals("John", userGetDto.firstName());
        assertEquals("Doe", userGetDto.lastName());
        assertEquals("user@email.com", userGetDto.email());
        assertEquals("287654321", userGetDto.nif());
        assertEquals(LocalDate.of(1990, 1, 1), userGetDto.dateOfBirth());
        assertEquals("Rua de Santo Antonio, 123", userGetDto.address().getStreet());
        assertEquals("Gondomar", userGetDto.address().getCity());
        assertEquals("Porto", userGetDto.address().getState());
        assertEquals("4444-444", userGetDto.address().getPostalCode());
        assertEquals("917654321", userGetDto.phoneNumber());

    }


    @Test
    @DisplayName("Test if fromCreateDtoToModel return null if received null dto")
    void testIfFromCreateDtoToModelReturnNullIfReceivedNullDto() {
        assertNull(UserConverter.toModel((UserCreateDto) null));
    }

    @Test
    @DisplayName("Test if fromGetDtoToModel return null if received null dto")
    void testIfFromGetDtoToModelReturnNullIfReceivedNullDto() {
        assertNull(UserConverter.toModel((UserGetDto) null));
    }


    @Test
    @DisplayName("Test if fromModelToUserGetDto return null if received null model")
    void testIfFromModelToUserGetDtoReturnNullIfReceivedNullModel() {
        assertNull(UserConverter.toDto(null));
    }


}
