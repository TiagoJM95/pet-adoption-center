package com.petadoption.center.converter;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
public class UserConverterTest {


    private Address createAddress = new Address("Rua das Andorinhas, 123",
                                        "Vila Nova de Gaia",
                                        "Porto",
                                        "4410-000");

    @Test
    @DisplayName("Test UserCreateDto to User model is working correctly")
    void fromUserCreateDtoToModel() {

        UserCreateDto userCreateDto = new UserCreateDto(
                "Fabio",
                "Guedes",
                "teste@email.com",
                "123456789",
                LocalDate.of(1990, 10, 25),
                createAddress,
                "912345678"
        );

        User user = UserConverter.toModel(userCreateDto);

        assertEquals("Fabio", user.getFirstName());
        assertEquals("Guedes", user.getLastName());
        assertEquals("teste@email.com", user.getEmail());
        assertEquals(LocalDate.of(1990, 10, 25), user.getDateOfBirth());
        assertEquals("Rua das Andorinhas, 123", user.getAddress().getStreet());
        assertEquals("Vila Nova de Gaia", user.getAddress().getCity());
        assertEquals("Porto", user.getAddress().getState());
        assertEquals("4410-000", user.getAddress().getPostalCode());
        assertEquals("912345678", user.getPhoneNumber());

    }


    @Test
    @DisplayName("Test User model to UserGetDto is working correctly")
    void fromModelToUserGetDto() {

    Address address = new Address(
        "Rua das Andorinhas, 123",
        "Vila Nova de Gaia",
        "Porto",
        "4410-000"
    );

    User user = User.builder()
            .id("12321-2313-123213")
            .firstName("Fabio")
            .lastName("Guedes")
            .email("teste@email.com")
            .dateOfBirth(LocalDate.of(1990, 10, 25))
            .address(address)
            .phoneNumber("911111111")
            .favoritePets(null)
            .adoptedPets(null)
            .build();

        UserGetDto userGetDto = UserConverter.toDto(user);

        assertEquals("12321-2313-123213", userGetDto.id());
        assertEquals("Fabio", userGetDto.firstName());
        assertEquals("Guedes", userGetDto.lastName());
        assertEquals("teste@email.com", userGetDto.email());
        assertEquals(LocalDate.of(1990, 10, 25), userGetDto.dateOfBirth());
        assertEquals("Rua das Andorinhas, 123", userGetDto.address().getStreet());
        assertEquals("Vila Nova de Gaia", userGetDto.address().getCity());
        assertEquals("Porto", userGetDto.address().getState());
        assertEquals("4410-000", userGetDto.address().getPostalCode());
        assertEquals("911111111", userGetDto.phoneNumber());

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
