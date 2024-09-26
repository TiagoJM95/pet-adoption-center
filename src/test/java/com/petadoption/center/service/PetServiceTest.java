package com.petadoption.center.service;

import com.petadoption.center.converter.PetConverter;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.exception.breed.BreedMismatchException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetDescriptionException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Pet;
import com.petadoption.center.repository.PetRepository;
import com.petadoption.center.service.interfaces.BreedServiceI;
import com.petadoption.center.service.interfaces.ColorServiceI;
import com.petadoption.center.service.interfaces.OrganizationServiceI;
import com.petadoption.center.service.interfaces.SpeciesServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.petadoption.center.testUtils.TestDtoFactory.createPetCreateDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createAttributes;
import static com.petadoption.center.testUtils.TestEntityFactory.createPet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
    private PetCreateDto petCreateDto;
    private String petId;
    private String invalidId;
    private PageRequest pageRequest;

    @BeforeEach
    void setup() {
        pet = createPet();
        petId = pet.getId();
        petCreateDto = createPetCreateDto();
        invalidId = "invalidId";
        pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
    }

    @Test
    @DisplayName("Test if getPetById() finds the correct pet and returns it's dto, with a valid Id")
    void testGetPetByIdFindsCorrectPetAndReturnsDtoWithValidId() throws PetNotFoundException {
        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));

        PetGetDto petGetDto = petService.getPetById(petId);

        assertEquals(pet.getId(), petGetDto.id());
        assertEquals(pet.getName(), petGetDto.name());
        assertEquals(pet.getSpecies().getId(), petGetDto.speciesDto().id());
        assertEquals(pet.getPrimaryBreed().getId(), petGetDto.primaryBreedDto().id());
        assertEquals(pet.getSecondaryBreed().getId(), petGetDto.secondaryBreedDto().id());
        assertEquals(pet.getPrimaryColor().getId(), petGetDto.primaryColorDto().id());
        assertEquals(pet.getSecondaryColor().getId(), petGetDto.secondaryColorDto().id());
        assertEquals(pet.getTertiaryColor().getId(), petGetDto.tertiaryColorDto().id());
        assertEquals(pet.getGender(), petGetDto.gender());
        assertEquals(pet.getCoat(), petGetDto.coat());
        assertEquals(pet.getSize(), petGetDto.size());
        assertEquals(pet.getAge(), petGetDto.age());
        assertEquals(pet.getDescription(), petGetDto.description());
        assertEquals(pet.getImageUrl(), petGetDto.imageUrl());
        assertEquals(pet.isAdopted(), petGetDto.isAdopted());
        assertEquals(pet.getAttributes(), petGetDto.attributes());
        assertEquals(pet.getDateAdded(), petGetDto.dateAdded());
        assertEquals(pet.getOrganization().getId(), petGetDto.organizationDto().id());

        verify(petRepository, times(1)).findById(petId);
    }

    @Test
    @DisplayName("Test if getPetById() throws PetNotFoundException with an invalid Id")
    void testGetPetByIdThrowsPetNotFoundExceptionWithInvalidId() {
        when(petRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> petService.getPetById(invalidId));

        verify(petRepository, times(1)).findById(invalidId);
    }

    @Test
    @DisplayName("Test addNewPet() with valid data returns PetGetDto")
    void testAddNewPetWithValidDataReturnsGetDto() throws BreedNotFoundException, BreedMismatchException, OrgNotFoundException, PetDescriptionException, ColorNotFoundException, SpeciesNotFoundException {

        doNothing().when(breedServiceI).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        when(petRepository.save(any(Pet.class))).thenReturn(pet);
        PetGetDto expected = PetConverter.toDto(pet);

        PetGetDto actual = petService.addNewPet(petCreateDto);

        assertEquals(expected, actual);
        verify(breedServiceI, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, times(1)).save(any(Pet.class));
    }
}
