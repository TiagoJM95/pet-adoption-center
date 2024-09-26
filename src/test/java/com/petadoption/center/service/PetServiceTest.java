package com.petadoption.center.service;

import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.model.Pet;
import com.petadoption.center.repository.PetRepository;
import com.petadoption.center.service.interfaces.BreedServiceI;
import com.petadoption.center.service.interfaces.ColorServiceI;
import com.petadoption.center.service.interfaces.OrganizationServiceI;
import com.petadoption.center.service.interfaces.SpeciesServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.petadoption.center.testUtils.TestEntityFactory.createPet;
import static com.petadoption.center.testUtils.TestEntityFactory.createPetWithoutId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class PetServiceTest {

    @InjectMocks
    private PetService petService;
    @Mock
    private PetRepository petRepository;
    @Mock
    private SpeciesServiceI speciesServiceI;
    @Mock
    private BreedServiceI breedServiceI;
    @Mock
    private ColorServiceI colorServiceI;
    @Mock
    private OrganizationServiceI organizationServiceI;

    private Pet pet;
    private String petId;

    @BeforeEach
    void setup() {
        pet = createPet();
        petId = pet.getId();
    }

    @Test
    @DisplayName("Test findById")
    void testFindById() throws PetNotFoundException {
        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));

        PetGetDto petGetDto = petService.getPetById(petId);

        assertEquals(pet.getId(), petGetDto.id());
    }
}
