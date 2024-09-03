package com.petadoption.center.converter;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Address;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserConverterTest {



    @Test
    @DisplayName("Test if from UserCreateDto to User model is working correctly")
    void fromUserCreateDtoToModel() {

        UserCreateDto userCreateDto = new UserCreateDto(
                "Fabio",
                "Guedes",
                "teste@email.com",
                LocalDate.of(1990, 10, 25),
                "Rua das Andorinhas, 123",
                "Vila Nova de Gaia",
                "Porto",
                "4410-000",
                "+351",
                912345678);

        User user = UserConverter.fromUserCreateDtoToModel(userCreateDto);

        assertEquals("Fabio", user.getFirstName());
        assertEquals("Guedes", user.getLastName());
        assertEquals("teste@email.com", user.getEmail());
        assertEquals(LocalDate.of(1990, 10, 25), user.getDateOfBirth());
        assertEquals("Rua das Andorinhas, 123", user.getAddress().getStreet());
        assertEquals("Vila Nova de Gaia", user.getAddress().getCity());
        assertEquals("Porto", user.getAddress().getState());
        assertEquals("4410-000", user.getAddress().getPostalCode());
        assertEquals("+351", user.getPhoneCountryCode());
        assertEquals(912345678, user.getPhoneNumber());

    }


    @Test
    @DisplayName("Test if from User model to UserGetDto is working correctly")
    void fromModelToUserGetDto() {

    Address address = new Address(
        "Rua das Andorinhas, 123",
        "Vila Nova de Gaia",
        "Porto",
        "4410-000"
    );

        User user = new User(
                1L,
                "Fabio",
                "Guedes",
                "teste@email.com",
                LocalDate.of(1990, 10, 25),
                address,
                "+351",
                911111111,
                null,
                null,
                null);

        UserGetDto userGetDto = UserConverter.fromModelToUserGetDto(user);

        assertEquals(1L, userGetDto.id());
        assertEquals("Fabio", userGetDto.firstName());
        assertEquals("Guedes", userGetDto.lastName());
        assertEquals("teste@email.com", userGetDto.email());
        assertEquals(LocalDate.of(1990, 10, 25), userGetDto.dateOfBirth());
        assertEquals("Rua das Andorinhas, 123", userGetDto.address().getStreet());
        assertEquals("Vila Nova de Gaia", userGetDto.address().getCity());
        assertEquals("Porto", userGetDto.address().getState());
        assertEquals("4410-000", userGetDto.address().getPostalCode());
        assertEquals("+351", userGetDto.phoneCountryCode());
        assertEquals(911111111, userGetDto.phoneNumber());
        assertNull(userGetDto.favoritePets());
        assertNull(userGetDto.adoptedPets());
        assertNull(userGetDto.adoptionForms());
    }

    @Test
    void testIfAcceptNullFirstName() {
        UserCreateDto userCreateDto = new UserCreateDto(
                null,
                "Guedes",
                "teste@email.com",
                LocalDate.of(1990, 10, 25),
                "Rua das Andorinhas, 123",
                "Vila Nova de Gaia",
                "Porto",
                "4410-000",
                "+351",
                912345678);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<UserCreateDto>> violations = validator.validate(userCreateDto);

        assertFalse(violations.isEmpty());

        boolean hasFirstNameViolation = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("firstName"));
        assertTrue(hasFirstNameViolation, "There should be a violation for the 'firstName' field.");
    }



}
