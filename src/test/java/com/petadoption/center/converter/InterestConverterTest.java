package com.petadoption.center.converter;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.enums.Status;
import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.model.Interest;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.User;
import com.petadoption.center.testUtils.TestDtoFactory;
import com.petadoption.center.testUtils.TestEntityFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.createInterest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class InterestConverterTest {

    private static User user;
    private static Pet pet;
    private static UserGetDto userGetDto;
    private static PetGetDto petGetDto;
    private static OrganizationGetDto organizationGetDto;

    @BeforeAll
    static void setUp() {
        userGetDto = userGetDto();
        petGetDto = petGetDto();
        organizationGetDto = orgGetDto();
    }

    @Test
    @DisplayName("Test if passing null to any of the AdoptionFormConverter methods returns null")
    void testIfPassingNullToAnyAdoptionFormConverterMethodReturnsNull() {

        assertNull(InterestConverter.toDto(null));
    }

    @Test
    @DisplayName("Test converting from Interest model to InterestGetDto")
    void testConvertInterestModelToGetDto() {

        Interest interest = createInterest();

        InterestGetDto interestConverted = InterestConverter.toDto(interest);

        assertEquals(interest.getId(), interestConverted.id());
        assertEquals(userGetDto, interestConverted.userDto());
        assertEquals(petGetDto, interestConverted.petDto());
        assertEquals(organizationGetDto, interestConverted.organizationDto());
        assertEquals(Status.PENDING, interestConverted.status());
        assertEquals(LocalDateTime.of(2024, 1, 1, 1 ,1), interestConverted.timestamp());
    }
}
