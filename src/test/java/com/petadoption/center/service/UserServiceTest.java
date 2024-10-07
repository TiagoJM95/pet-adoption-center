package com.petadoption.center.service;

import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.dto.user.UserUpdateDto;
import com.petadoption.center.exception.not_found.UserNotFoundException;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.petadoption.center.testUtils.TestDtoFactory.userCreateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.userUpdateDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createUser;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.USER_WITH_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User testUser;
    private User updatedUser;
    private UserCreateDto userCreateDto;
    private UserUpdateDto userUpdateDto;
    private Pageable pageable;


    @BeforeEach
    void setUp() {

        testUser = createUser();
        userCreateDto = userCreateDto();
        userUpdateDto = userUpdateDto();

        Address updateAddress = new Address("Rua das Gaivotas, 456",
                "Vila Nova de Gaia",
                "4410-000",
                "Porto");

        updatedUser = createUser();
        updatedUser.setId("2132-1234-1234");
        updatedUser.setFirstName("Tiago");
        updatedUser.setLastName("Moreira");
        updatedUser.setEmail("tm@email.com");
        updatedUser.setDateOfBirth(LocalDate.of(1990, 10, 25));
        updatedUser.setPhoneNumber("934587967");
        updatedUser.setAddress(updateAddress);

        int page = 0;
        int size = 10;
        String sort = "created_at";
        pageable = PageRequest.of(page, size, Sort.by(sort));
    }

    @Test
    @DisplayName("Test if get all users works correctly")
    void getAllUsersShouldReturnListOf() {

        Page<User> pagedUsers = new PageImpl<>(List.of(testUser));

        when(userRepository.findAll(pageable)).thenReturn(pagedUsers);

        List<UserGetDto> result = userService.getAll(pageable);

        assertEquals(1, result.size());
        assertEquals(testUser.getEmail(), result.getFirst().email());
    }

    @Test
    @DisplayName("Test if get all users return empty list if no users")
    void getAllShouldReturnEmpty(){

        Page<User> pagedUsers = new PageImpl<>(List.of());

        when(userRepository.findAll(pageable)).thenReturn(pagedUsers);

        List<UserGetDto> result = userService.getAll(pageable);

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test if get all users return number of elements of page size")
    void getAllShouldReturnNumberOfElementsOfPageSize(){

        User userToAdd = new User();
        List<User> allUsers = List.of(testUser, updatedUser, userToAdd);
        Page<User> pagedUsers = new PageImpl<>(List.of(testUser, updatedUser), pageable, allUsers.size());

        when(userRepository.findAll(pageable)).thenReturn(pagedUsers);

        List<UserGetDto> result = userService.getAll(pageable);

        assertEquals(2, result.size());
        assertFalse(result.size() > 2);
    }

    @Test
    @DisplayName("Test if get all users return with Descending Order")
    void getAllUsersShouldReturnInDescendingOrder(){

        User userToAdd = new User();
        userToAdd.setFirstName("Fabio");
        List<User> allUsers = List.of(updatedUser, testUser, userToAdd);
        Page<User> pagedUsers = new PageImpl<>(allUsers, pageable, allUsers.size());

        when(userRepository.findAll(any(PageRequest.class))).thenReturn(pagedUsers);

        List<UserGetDto> result = userService.getAll(pageable);

        assertNotNull(result);
        assertEquals(3,result.size());
        assertEquals(result.get(0).firstName(), updatedUser.getFirstName());
        assertEquals(result.get(1).firstName(), testUser.getFirstName());
        assertEquals(result.get(2).firstName(), userToAdd.getFirstName());
    }

    @Test
    @DisplayName("Test if get all users return with Ascending Order")
    void getAllUsersShouldReturnInAscendingOrder(){

        User userToAdd = new User();
        userToAdd.setFirstName("Fabio");
        List<User> allUsers = List.of(userToAdd, testUser, updatedUser);
        Page<User> pagedUsers = new PageImpl<>(allUsers, pageable, allUsers.size());

        when(userRepository.findAll(any(PageRequest.class))).thenReturn(pagedUsers);

        List<UserGetDto> result = userService.getAll(pageable);

        assertNotNull(result);
        assertEquals(3,result.size());
        assertEquals(result.get(0).firstName(), userToAdd.getFirstName());
        assertEquals(result.get(1).firstName(), testUser.getFirstName());
        assertEquals(result.get(2).firstName(), updatedUser.getFirstName());
    }


    @Test
    @DisplayName("Test if get user by id works correctly")
    void getUserByIdShouldReturn() throws UserNotFoundException {

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

            UserGetDto result = userService.getById(testUser.getId());
            assertEquals(testUser.getEmail(), result.email());
    }

    @Test
    @DisplayName("Test if get user by id throws UserNotFoundException")
    void getUserByIdShouldThrowNotFoundException() {

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getById(testUser.getId()));
    }

    @Test
    @DisplayName("Test if add new user saves and returns UserGetDto")
    void createGetDto() {

        when(userRepository.save(any(User.class))).thenReturn(testUser);
        UserGetDto result = userService.create(userCreateDto);

        assertNotNull(userCreateDto);
        assertEquals(userCreateDto.email(), result.email());
   }


    @Test
    @DisplayName("Test if update user saves all fields and returns UserGetDto")
    void updateUserShouldSaveAllFieldsAndReturnGetDto() throws UserNotFoundException {

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        UserGetDto result = userService.update(testUser.getId(), userUpdateDto);

        assertNotNull(userUpdateDto);
        assertEquals(userUpdateDto.email(), result.email());
    }

    @Test
    @DisplayName("Test if update user throws exception user not found")
    void updateUserShouldThrowExceptionNotFound(){

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.update(testUser.getId(), userUpdateDto));
    }


    @Test
    @DisplayName("Test if delete user erase user and return message")
    void deleteShouldEraseAndDisplayMessage() throws UserNotFoundException {

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        assertEquals(userService.delete(testUser.getId()),USER_WITH_ID + testUser.getId() + DELETE_SUCCESS);
    }

    @Test
    @DisplayName("Test if delete user throw exception user not found")
    void deleteUserShouldThrowExceptionNotFound(){

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.delete(testUser.getId()));
    }





    // FOR VALIDATION TESTS

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
