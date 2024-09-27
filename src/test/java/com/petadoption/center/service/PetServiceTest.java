package com.petadoption.center.service;

import com.petadoption.center.converter.PetConverter;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.enums.Genders;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.petadoption.center.enums.Genders.getGenderByDescription;
import static com.petadoption.center.testUtils.TestDtoFactory.createPetCreateDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createAttributes;
import static com.petadoption.center.testUtils.TestEntityFactory.createPet;
import static com.petadoption.center.util.Messages.*;
import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("Returns PetGetDto when getPetById() is called with an valid ID")
    void shouldReturnPetGetDtoWhenPetExistsById() throws PetNotFoundException {
        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        PetGetDto expected = PetConverter.toDto(pet);

        PetGetDto actual = petService.getPetById(petId);

        assertEquals(expected, actual);
        verify(petRepository, times(1)).findById(petId);
    }

    @Test
    @DisplayName("Throws PetNotFoundException when getPetById() is called with an invalid ID")
    void shouldThrowPetNotFoundExceptionWhenPetDoesNotExistById() {
        when(petRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> petService.getPetById(invalidId));

        verify(petRepository, times(1)).findById(invalidId);
    }

    @Test
    @DisplayName("Saves Pet in DB and returns PetGetDto when addNewPet() is called with valid data")
    void shouldReturnPetGetDtoWhenPetIsSavedInDb() throws BreedNotFoundException, BreedMismatchException, OrgNotFoundException, PetDescriptionException, ColorNotFoundException, SpeciesNotFoundException {

        doNothing().when(breedServiceI).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        when(petRepository.save(any(Pet.class))).thenReturn(pet);
        PetGetDto expected = PetConverter.toDto(pet);

        PetGetDto actual = petService.addNewPet(petCreateDto);

        assertEquals(expected, actual);
        verify(breedServiceI, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws BreedMismatchException when addNewPet() is called with breed not belonging to species")
    void shouldThrowBreedMismatchExceptionWhenBreedAndSpeciesMismatch() throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException {

        doThrow(new BreedMismatchException("Breed mismatch")).when(breedServiceI).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));

        BreedMismatchException ex = assertThrows(BreedMismatchException.class, () -> petService.addNewPet(petCreateDto));


        assertEquals("Breed mismatch", ex.getMessage());
        verify(breedServiceI, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws SpeciesNotFoundException when addNewPet() is called with invalid species")
    void shouldThrowSpeciesNotFoundExceptionWhenSpeciesIsNotFound() throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException {

        doThrow(new SpeciesNotFoundException("Species not found")).when(breedServiceI).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));

        SpeciesNotFoundException ex = assertThrows(SpeciesNotFoundException.class, () -> petService.addNewPet(petCreateDto));

        assertEquals("Species not found", ex.getMessage());
        verify(breedServiceI, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws BreedNotFoundException when addNewPet() is called with invalid breed")
    void shouldThrowBreedNotFoundExceptionWhenBreedIsNotFound() throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException {

        doThrow(new BreedNotFoundException("Breed not found")).when(breedServiceI).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));

        BreedNotFoundException ex = assertThrows(BreedNotFoundException.class, () -> petService.addNewPet(petCreateDto));

        assertEquals("Breed not found", ex.getMessage());
        verify(breedServiceI, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws ColorNotFoundException when addNewPet() is called with invalid color")
    void shouldThrowColorNotFoundExceptionWhenColorIsNotFound() throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException, ColorNotFoundException {

        doNothing().when(breedServiceI).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        when(colorServiceI.getColorById(anyString())).thenThrow(new ColorNotFoundException("InvalidColor"));

        ColorNotFoundException ex = assertThrows(ColorNotFoundException.class, () -> petService.addNewPet(petCreateDto));

        assertEquals("InvalidColor", ex.getMessage());
        verify(breedServiceI, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws OrgNotFoundException when addNewPet() is called with invalid org")
    void shouldThrowOrgNotFoundExceptionWhenOrgIsNotFound() throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException, OrgNotFoundException {

        doNothing().when(breedServiceI).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        when(organizationServiceI.getOrganizationById(anyString())).thenThrow(new OrgNotFoundException("InvalidOrganization"));

        OrgNotFoundException ex = assertThrows(OrgNotFoundException.class, () -> petService.addNewPet(petCreateDto));

        assertEquals("InvalidOrganization", ex.getMessage());
        verify(breedServiceI, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @ParameterizedTest
    @DisplayName("Throws PetDescriptionException when addNewPet() is called with invalid enum fields")
    @CsvSource({
            "Invalid, Short, Medium, Adult, Invalid gender description: Invalid",
            "Male, Invalid, Medium, Adult, Invalid coat description: Invalid",
            "Male, Short, Invalid, Adult, Invalid size description: Invalid",
            "Male, Short, Medium, Invalid, Invalid age description: Invalid"
    })
    void shouldThrowPetDescriptionExceptionWhenInvalidFields(String gender, String coat, String size, String age, String expectedMessagePart) {
        PetCreateDto invalidDto = PetCreateDto.builder()
                .name("Max")
                .petSpeciesId("111111-11111111-1111")
                .primaryBreedId("222222-22222222-2222")
                .secondaryBreedId("333333-33333333-3333")
                .primaryColor("444444-44444444-4444")
                .secondaryColor("555555-55555555-5555")
                .tertiaryColor("666666-66666666-6666")
                .gender(gender)
                .coat(coat)
                .size(size)
                .age(age)
                .description("Max is a very friendly dog")
                .imageUrl("https://www.dogimages.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationId("777777-77777777-7777")
                .build();

        PetDescriptionException ex = assertThrows(PetDescriptionException.class, () -> {
            petService.addNewPet(invalidDto);
        });

        assertTrue(ex.getMessage().contains(expectedMessagePart));

        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("")
    void shouldReturnVoidWhenAddingAListOfNewPets(){

    }

}
