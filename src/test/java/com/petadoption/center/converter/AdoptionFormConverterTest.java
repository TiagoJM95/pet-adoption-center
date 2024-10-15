package com.petadoption.center.converter;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.User;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static com.petadoption.center.testUtils.TestDtoFactory.petGetDto;
import static com.petadoption.center.testUtils.TestDtoFactory.userGetDto;
import static com.petadoption.center.testUtils.TestEntityFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AdoptionFormConverterTest {

    private static Family family;
    private static Address address;
    private static UserGetDto userGetDto;
    private static PetGetDto petGetDto;
    private static User user;
    private static Pet pet;

    @BeforeAll
    static void setUp() {
        family = createFamily();
        address = createAddress();
        userGetDto = userGetDto();
        petGetDto = petGetDto();
        user = createUser();
        pet = createPet();
    }

    @Test
    @DisplayName("Test if passing null to any of the AdoptionFormConverter methods returns null")
    void testIfPassingNullToAnyAdoptionFormConverterMethodReturnsNull() {

        assertNull(AdoptionFormConverter.toModel((AdoptionFormCreateDto) null));
        assertNull(AdoptionFormConverter.toModel((AdoptionFormGetDto) null));
        assertNull(AdoptionFormConverter.toDto(null));
    }

    @Test
    @DisplayName("Test convert from AdoptionFormCreateDto to AdoptionForm Model")
    void testAdoptionFormCreateDtoToModel() {

        AdoptionFormCreateDto adoptionFormCreateDto = new AdoptionFormCreateDto(
                "1111-2222",
                "3333-4444",
                family,
                "Neighbour",
                true,
                "Notes",
                address
        );

        AdoptionForm adoptionFormConverted = AdoptionFormConverter.toModel(adoptionFormCreateDto);

        assertEquals(adoptionFormCreateDto.userFamily().getLikesPets(), adoptionFormConverted.getUserFamily().getLikesPets());
        assertEquals(adoptionFormCreateDto.userFamily().getHasOtherPets(), adoptionFormConverted.getUserFamily().getHasOtherPets());
        assertEquals(adoptionFormCreateDto.userFamily().getNumberOfPets(), adoptionFormConverted.getUserFamily().getNumberOfPets());
        assertEquals(adoptionFormCreateDto.userFamily().getFamilyPets(), adoptionFormConverted.getUserFamily().getFamilyPets());
        assertEquals(adoptionFormCreateDto.petVacationHome(), adoptionFormConverted.getPetVacationHome());
        assertEquals(adoptionFormCreateDto.isResponsibleForPet(), adoptionFormConverted.getIsResponsibleForPet());
        assertEquals(adoptionFormCreateDto.otherNotes(), adoptionFormConverted.getOtherNotes());
        assertEquals(adoptionFormCreateDto.petAddress().getStreet(), adoptionFormConverted.getPetAddress().getStreet());
        assertEquals(adoptionFormCreateDto.petAddress().getCity(), adoptionFormConverted.getPetAddress().getCity());
        assertEquals(adoptionFormCreateDto.petAddress().getState(), adoptionFormConverted.getPetAddress().getState());
        assertEquals(adoptionFormCreateDto.petAddress().getPostalCode(), adoptionFormConverted.getPetAddress().getPostalCode());
    }

    @Test
    @DisplayName("Test convert from AdoptionFormGetDto to AdoptionForm Model")
    void testAdoptionFormGetDtoToModel() {

        AdoptionFormGetDto adoptionFormGetDto = new AdoptionFormGetDto(
                "1111-2222",
                userGetDto,
                petGetDto,
                family,
                "Neighbour",
                true,
                "Notes",
                address,
                LocalDateTime.now()
        );

        AdoptionForm adoptionFormConverted = AdoptionFormConverter.toModel(adoptionFormGetDto);

        assertEquals(adoptionFormGetDto.userFamily().getLikesPets(), adoptionFormConverted.getUserFamily().getLikesPets());
        assertEquals(adoptionFormGetDto.userFamily().getHasOtherPets(), adoptionFormConverted.getUserFamily().getHasOtherPets());
        assertEquals(adoptionFormGetDto.userFamily().getNumberOfPets(), adoptionFormConverted.getUserFamily().getNumberOfPets());
        assertEquals(adoptionFormGetDto.userFamily().getFamilyPets(), adoptionFormConverted.getUserFamily().getFamilyPets());
        assertEquals(adoptionFormGetDto.petVacationHome(), adoptionFormConverted.getPetVacationHome());
        assertEquals(adoptionFormGetDto.isResponsibleForPet(), adoptionFormConverted.getIsResponsibleForPet());
        assertEquals(adoptionFormGetDto.otherNotes(), adoptionFormConverted.getOtherNotes());
        assertEquals(adoptionFormGetDto.petAddress().getStreet(), adoptionFormConverted.getPetAddress().getStreet());
        assertEquals(adoptionFormGetDto.petAddress().getCity(), adoptionFormConverted.getPetAddress().getCity());
        assertEquals(adoptionFormGetDto.petAddress().getState(), adoptionFormConverted.getPetAddress().getState());
        assertEquals(adoptionFormGetDto.petAddress().getPostalCode(), adoptionFormConverted.getPetAddress().getPostalCode());
    }

    @Test
    @DisplayName("Test convert from AdoptionForm model to AdoptionFormGetDto")
    void testAdoptionFormModelToGetDto() {

        AdoptionForm adoptionForm = new AdoptionForm(
                "1111-2222",
                user,
                pet,
                family,
                "Neighbour",
                true,
                "Notes",
                address,
                LocalDateTime.of(2024, 1, 1, 1 , 1)
        );

        AdoptionFormGetDto adoptionFormConverted = AdoptionFormConverter.toDto(adoptionForm);

        assertEquals(adoptionForm.getUserFamily().getLikesPets(), adoptionFormConverted.userFamily().getLikesPets());
        assertEquals(adoptionForm.getUserFamily().getHasOtherPets(), adoptionFormConverted.userFamily().getHasOtherPets());
        assertEquals(adoptionForm.getUserFamily().getNumberOfPets(), adoptionFormConverted.userFamily().getNumberOfPets());
        assertEquals(adoptionForm.getUserFamily().getFamilyPets(), adoptionFormConverted.userFamily().getFamilyPets());
        assertEquals(adoptionForm.getPetVacationHome(), adoptionFormConverted.petVacationHome());
        assertEquals(adoptionForm.getIsResponsibleForPet(), adoptionFormConverted.isResponsibleForPet());
        assertEquals(adoptionForm.getOtherNotes(), adoptionFormConverted.otherNotes());
        assertEquals(adoptionForm.getPetAddress().getStreet(), adoptionFormConverted.petAddress().getStreet());
        assertEquals(adoptionForm.getPetAddress().getCity(), adoptionFormConverted.petAddress().getCity());
        assertEquals(adoptionForm.getPetAddress().getState(), adoptionFormConverted.petAddress().getState());
        assertEquals(adoptionForm.getPetAddress().getPostalCode(), adoptionFormConverted.petAddress().getPostalCode());
    }
}
