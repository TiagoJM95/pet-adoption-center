package com.petadoption.center.service;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.repository.UserRepository;
import com.petadoption.center.service.implementation.UserServiceImpl;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {


    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User TestUser;
    private UserCreateDto userCreateDto;
    private UserUpdateDto userUpdateDto;

    @BeforeEach
    void setUp() {
        TestUser = new User();
        TestUser.setId(1L);
        TestUser.setFirstName("Manuel");
        TestUser.setLastName("Silva");
        TestUser.setEmail("email@email.com");
        TestUser.setDateOfBirth(LocalDate.of(1990, 10, 25));
        TestUser.setPhoneCountryCode("+351");
        TestUser.setPhoneNumber(911234567);
        TestUser.setAddress(new Address("Rua dos animais, 123", "Gondomar", "Porto", "4400-000"));

        userCreateDto = new UserCreateDto("Fabio", "Guedes", "email1@email.com", LocalDate.of(1990, 10, 25), "Rua das andorinhas, 321", "Vila Nova de Gaia", "Porto", "4410-001", "+351", 912354678);
        userUpdateDto = new UserUpdateDto("Tiago", "Moreira", "tm@email.com", "Rua dos bandidos, 123", "Rio Tinto", "Porto", "4100-001", "+351", 934587967);
    }

    @Test
    @DisplayName("Test if getAllUsers works correctly")
    void getAllUsersShouldReturnListOfUsers() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<User> pagedUsers = new PageImpl<>(List.of(TestUser));

        when(userRepository.findAll(pageRequest)).thenReturn(pagedUsers);

        List<UserGetDto> result = userService.getAllUsers(0, 10, "id");

        assertEquals(1, result.size());
        assertEquals(TestUser.getEmail(), result.getFirst().email());
    }


    //    @Test
//    @DisplayName("Test if validator works correctly")
//    void testIfAcceptNullFirstName() {
//        UserCreateDto userCreateDto = new UserCreateDto(
//                null,
//                "Guedes",
//                "teste@email.com",
//                LocalDate.of(1990, 10, 25),
//                "Rua das Andorinhas, 123",
//                "Vila Nova de Gaia",
//                "Porto",
//                "4410-000",
//                "+351",
//                912345678);
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
//        Set<ConstraintViolation<UserCreateDto>> violations = validator.validate(userCreateDto);
//
//        assertFalse(violations.isEmpty());
//
//        boolean hasFirstNameViolation = violations.stream()
//                .anyMatch(violation -> violation.getPropertyPath().toString().equals("firstName"));
//        assertTrue(hasFirstNameViolation, "There should be a violation for the 'firstName' field.");
//    }
}
