package com.petadoption.center.converter;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.*;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static com.petadoption.center.enums.Ages.BABY;
import static com.petadoption.center.enums.Sizes.LARGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class AdoptionFormConverterTest {

    private static Family family;
    private static Address address;

    @BeforeAll
    static void setUp() {
        family = new Family(
                4,
                true,
                true,
                2,
                List.of("DOG", "PARROT")
        );

        address = new Address(
                "Rua das Andorinhas, 123",
                "Vila Nova de Gaia",
                "Porto",
                "4410-000");

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

        AdoptionForm adoptionForm = AdoptionFormConverter.toModel(adoptionFormCreateDto);

        assertEquals(4, adoptionForm.getUserFamily().getFamilyCount());
        assertEquals(true, adoptionForm.getUserFamily().getLikesPets());
        assertEquals(true, adoptionForm.getUserFamily().getHasOtherPets());
        assertEquals(2, adoptionForm.getUserFamily().getNumberOfPets());
        assertEquals(List.of("DOG", "PARROT"), adoptionForm.getUserFamily().getFamilyPets());
        assertEquals("Neighbour", adoptionForm.getPetVacationHome());
        assertEquals(true, adoptionForm.getIsResponsibleForPet());
        assertEquals("Notes", adoptionForm.getOtherNotes());
        assertEquals("Rua das Andorinhas, 123", adoptionForm.getPetAddress().getStreet());
        assertEquals("Vila Nova de Gaia", adoptionForm.getPetAddress().getCity());
        assertEquals("Porto", adoptionForm.getPetAddress().getState());
        assertEquals("4410-000", adoptionForm.getPetAddress().getPostalCode());
    }

    /*@Test
    @DisplayName("Test convert from AdoptionFormGetDto to AdoptionForm Model")
    void testAdoptionFormGetDtoToModel() {

        User user = User.builder()
                .id("1234-5678")
                .firstName("Manuel")
                .lastName("Agrião")
                .email("email@email.com")
                .nif("999999990")
                .phoneNumber("918948958")
                .dateOfBirth(LocalDate.of(1990, 10, 25))
                .address(address)
                .build();

        Species species = new Species("123123-12312312-3123", "Dog");
        Breed breed = new Breed("1231-1231-1231", "Shepperd", species);
        Color color = new Color("1234", "Black");

        PetGetDto pet = PetGetDto.builder()
                .id("1234-1234")
                .speciesDto()
                .primaryBreed(breed)
                .primaryColor(color)
                .size(LARGE)
                .age(BABY)
                .build();

        AdoptionFormGetDto adoptionFormGetDto = new AdoptionFormGetDto(
                "1111-2222",
                user,
                pet,
                family,
                "Neighbour",
                true,
                "Notes",
                address
        );
    }*/

}
