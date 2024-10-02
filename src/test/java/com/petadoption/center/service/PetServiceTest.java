package com.petadoption.center.service;

import com.petadoption.center.converter.PetConverter;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Sizes;
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
import com.petadoption.center.specifications.PetSearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.petadoption.center.testUtils.TestDtoFactory.petCreateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.petUpdateDto;
import static com.petadoption.center.testUtils.TestEntityFactory.*;
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
    private SpeciesServiceI speciesService;
    @Mock
    private BreedServiceI breedService;
    @Mock
    private ColorServiceI colorService;
    @Mock
    private OrganizationServiceI organizationService;

    private Pet pet;
    private Pet updatedPet;
    private PetCreateDto petCreateDto;
    private PetUpdateDto petUpdateDto;
    private String petId;
    private String invalidId;
    private PetSearchCriteria criteria;
    private int page;
    private int size;
    private String sortBy;

    @BeforeEach
    void setup() {
        pet = createPet();
        petId = pet.getId();
        updatedPet = pet;
        updatedPet.setSize(Sizes.LARGE);
        updatedPet.setAge(Ages.SENIOR);
        updatedPet.setDescription("Max is an updated dog");
        updatedPet.setImageUrl("https://www.updatedimages.com");
        petCreateDto = petCreateDto("Max");
        petUpdateDto = petUpdateDto();
        invalidId = "invalidId";
        criteria = petSearchCriteria();
        page = 0;
        size = 10;
        sortBy = "name";
    }

    static Stream<List<PetCreateDto>> petCreateDtoListProvider() {
        PetCreateDto problematicDto = PetCreateDto.builder()
                .name("Max")
                .petSpeciesId("111AAA-1111AAAA-11AA")
                .primaryBreedId("222AAA-2222AAAA-22AA")
                .secondaryBreedId("333AAA-3333AAAA-33AA")
                .primaryColor("444AAA-4444AAA-44AA")
                .secondaryColor("555AAA-5555AAAA-55AA")
                .tertiaryColor("666AAA-6666AAAA-66AA")
                .gender("Male")
                .coat("Short")
                .size("Medium")
                .age("Adult")
                .description("Max is a very friendly dog")
                .imageUrl("https://www.dogimages.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationId("777AAA-7777AAAA-77AA")
                .build();
               return Stream.of(
                List.of(petCreateDto("Jim"), petCreateDto("Bobby"), petCreateDto("Spike"), problematicDto),
                List.of(petCreateDto("Jim"), problematicDto, petCreateDto("Bobby"), petCreateDto("Spike")),
                List.of(problematicDto, petCreateDto("Jim"), petCreateDto("Bobby"), petCreateDto("Spike"))
        );
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

        doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        when(petRepository.save(any(Pet.class))).thenReturn(pet);
        PetGetDto expected = PetConverter.toDto(pet);

        PetGetDto actual = petService.addNewPet(petCreateDto);

        assertEquals(expected, actual);
        verify(breedService, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws BreedMismatchException when addNewPet() is called with breed not belonging to species")
    void shouldThrowBreedMismatchExceptionWhenBreedAndSpeciesMismatch() throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException {

        doThrow(new BreedMismatchException("Breed mismatch")).when(breedService).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));

        BreedMismatchException ex = assertThrows(BreedMismatchException.class, () -> petService.addNewPet(petCreateDto));


        assertEquals("Breed mismatch", ex.getMessage());
        verify(breedService, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws SpeciesNotFoundException when addNewPet() is called with invalid species")
    void shouldThrowSpeciesNotFoundExceptionWhenSpeciesIsNotFound() throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException {

        doThrow(new SpeciesNotFoundException("Species not found")).when(breedService).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));

        SpeciesNotFoundException ex = assertThrows(SpeciesNotFoundException.class, () -> petService.addNewPet(petCreateDto));

        assertEquals("Species not found", ex.getMessage());
        verify(breedService, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws BreedNotFoundException when addNewPet() is called with invalid breed")
    void shouldThrowBreedNotFoundExceptionWhenBreedIsNotFound() throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException {

        doThrow(new BreedNotFoundException("Breed not found")).when(breedService).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));

        BreedNotFoundException ex = assertThrows(BreedNotFoundException.class, () -> petService.addNewPet(petCreateDto));

        assertEquals("Breed not found", ex.getMessage());
        verify(breedService, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws ColorNotFoundException when addNewPet() is called with invalid color")
    void shouldThrowColorNotFoundExceptionWhenColorIsNotFound() throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException, ColorNotFoundException {

        doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        when(colorService.getColorById(anyString())).thenThrow(new ColorNotFoundException("InvalidColor"));

        ColorNotFoundException ex = assertThrows(ColorNotFoundException.class, () -> petService.addNewPet(petCreateDto));

        assertEquals("InvalidColor", ex.getMessage());
        verify(breedService, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws OrgNotFoundException when addNewPet() is called with invalid org")
    void shouldThrowOrgNotFoundExceptionWhenOrgIsNotFound() throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException, OrgNotFoundException {

        doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        when(organizationService.getOrganizationById(anyString())).thenThrow(new OrgNotFoundException("InvalidOrganization"));

        OrgNotFoundException ex = assertThrows(OrgNotFoundException.class, () -> petService.addNewPet(petCreateDto));

        assertEquals("InvalidOrganization", ex.getMessage());
        verify(breedService, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
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
    @DisplayName("Nothing is thrown when adding a valid list of new pets")
    void shouldRunWithNoThrowsWhenAddingAValidListOfNewPets() throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException, OrgNotFoundException, PetDescriptionException, ColorNotFoundException {

        List<PetCreateDto> pets = List.of(petCreateDto, petCreateDto("Bobby"), petCreateDto("Spike"));

        doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(any());
        when(petRepository.save(any(Pet.class))).thenReturn(any(Pet.class));

        petService.addListOfNewPets(pets);

        verify(breedService, times(pets.size())).verifyIfBreedsAndSpeciesMatch(any());
        verify(petRepository, times(pets.size())).save(any(Pet.class));
    }

    @ParameterizedTest
    @DisplayName("Throws BreedMismatchException when addListOfNewPets() is called with breed not belonging to species in any position of the List")
    @MethodSource("petCreateDtoListProvider")
    void shouldThrowBreedMismatchExceptionWhenBreedAndSpeciesMismatchInList(List<PetCreateDto> pets) throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException {

        PetCreateDto problematicDto = pets.stream()
                .filter(p -> p.name().equals("Max"))
                .findFirst()
                .orElseThrow();

        lenient().doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));
        doThrow(new BreedMismatchException(BREED_SPECIES_MISMATCH)).when(breedService).verifyIfBreedsAndSpeciesMatch(problematicDto);

        BreedMismatchException ex = assertThrows(BreedMismatchException.class, () -> petService.addListOfNewPets(pets));

        assertEquals(BREED_SPECIES_MISMATCH, ex.getMessage());
        verify(breedService, times(pets.indexOf(problematicDto)+1)).verifyIfBreedsAndSpeciesMatch(any());
        verify(petRepository, times(pets.indexOf(problematicDto))).save(any(Pet.class));
    }

    @ParameterizedTest
    @DisplayName("Throws SpeciesNotFoundException when addListOfNewPets() is called with an invalid species in any position of the List")
    @MethodSource("petCreateDtoListProvider")
    void shouldThrowSpeciesNotFoundExceptionWhenSpeciesNotFound(List<PetCreateDto> pets) throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException {

        PetCreateDto problematicDto = pets.stream()
                .filter(p -> p.name().equals("Max"))
                .findFirst()
                .orElseThrow();

        lenient().doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));
        doThrow(new SpeciesNotFoundException("Species not found")).when(breedService).verifyIfBreedsAndSpeciesMatch(problematicDto);

        SpeciesNotFoundException ex = assertThrows(SpeciesNotFoundException.class, () -> petService.addListOfNewPets(pets));

        assertEquals("Species not found", ex.getMessage());
        verify(breedService, times(pets.indexOf(problematicDto)+1)).verifyIfBreedsAndSpeciesMatch(any());
        verify(petRepository, times(pets.indexOf(problematicDto))).save(any(Pet.class));
    }

    @ParameterizedTest
    @DisplayName("Throws BreedNotFoundException when addListOfNewPets() is called with an invalid breed in any position of the List")
    @MethodSource("petCreateDtoListProvider")
    void shouldThrowBreedNotFoundExceptionWhenBreedNotFound(List<PetCreateDto> pets) throws BreedNotFoundException, BreedMismatchException, SpeciesNotFoundException {

        PetCreateDto problematicDto = pets.stream()
                .filter(p -> p.name().equals("Max"))
                .findFirst()
                .orElseThrow();

        lenient().doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));
        doThrow(new BreedNotFoundException("Breed not found")).when(breedService).verifyIfBreedsAndSpeciesMatch(problematicDto);

        BreedNotFoundException ex = assertThrows(BreedNotFoundException.class, () -> petService.addListOfNewPets(pets));

        assertEquals("Breed not found", ex.getMessage());
        verify(breedService, times(pets.indexOf(problematicDto)+1)).verifyIfBreedsAndSpeciesMatch(any());
        verify(petRepository, times(pets.indexOf(problematicDto))).save(any(Pet.class));
    }

    @ParameterizedTest
    @DisplayName("Throws ColorNotFoundException when addListOfNewPets() is called with an invalid color in any position of the List")
    @MethodSource("petCreateDtoListProvider")
    void shouldThrowColorNotFoundExceptionWhenColorNotFound(List<PetCreateDto> pets) throws ColorNotFoundException {

        PetCreateDto problematicDto = pets.stream()
                .filter(p -> p.name().equals("Max"))
                .findFirst()
                .orElseThrow();

        when(colorService.getColorById(anyString())).thenAnswer(invocation -> {
            String colorId = invocation.getArgument(0);
            if (colorId.equals(problematicDto.primaryColor())) {
                throw new ColorNotFoundException("InvalidColor");
            }
            return any(ColorGetDto.class);
        });

        ColorNotFoundException ex = assertThrows(ColorNotFoundException.class, () -> petService.addListOfNewPets(pets));

        assertEquals("InvalidColor", ex.getMessage());
        verify(petRepository, times(pets.indexOf(problematicDto))).save(any(Pet.class));
    }

    @ParameterizedTest
    @DisplayName("Throws OrgNotFoundException when addListOfNewPets() is called with an invalid org in any position of the List")
    @MethodSource("petCreateDtoListProvider")
    void shouldThrowOrgNotFoundExceptionWhenOrgNotFound(List<PetCreateDto> pets) throws OrgNotFoundException {

        PetCreateDto problematicDto = pets.stream()
                .filter(p -> p.name().equals("Max"))
                .findFirst()
                .orElseThrow();

        when(organizationService.getOrganizationById(anyString())).thenAnswer(invocation -> {
            String orgId = invocation.getArgument(0);
            if (orgId.equals(problematicDto.organizationId())) {
                throw new OrgNotFoundException("InvalidOrg");
            }
            return any(OrgGetDto.class);
        });

        OrgNotFoundException ex = assertThrows(OrgNotFoundException.class, () -> petService.addListOfNewPets(pets));

        assertEquals("InvalidOrg", ex.getMessage());
        verify(petRepository, times(pets.indexOf(problematicDto))).save(any(Pet.class));
    }

    @Test
    @DisplayName("Returns a PetGetDto when updatePet() is called with a valid ID and dto")
    void shouldReturnPetGetDtoWhenPetIsUpdatedSuccessfully() throws OrgNotFoundException, PetDescriptionException, PetNotFoundException {

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        when(petRepository.save(any(Pet.class))).thenReturn(updatedPet);
        PetGetDto expected = PetConverter.toDto(updatedPet);

        PetGetDto actual = petService.updatePet(petId, petUpdateDto);

        assertEquals(expected, actual);
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws PetNotFoundException when updatePet() is called with an invalid ID")
    void shouldThrowPetNotFoundExceptionWhenIdIsInvalid() {

        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        PetNotFoundException ex = assertThrows(PetNotFoundException.class, () -> petService.updatePet(petId, petUpdateDto));

        assertEquals(PET_WITH_ID + petId + NOT_FOUND, ex.getMessage());
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws OrgNotFoundException when updatePet() is called with an invalid orgID in the DTO")
    void shouldThrowOrgNotFoundExceptionWhenOrgIdIsInvalid() throws OrgNotFoundException {

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        when(organizationService.getOrganizationById(anyString())).thenThrow(new OrgNotFoundException("Org not found"));

        OrgNotFoundException ex = assertThrows(OrgNotFoundException.class, () -> petService.updatePet(petId, petUpdateDto));

        assertEquals("Org not found", ex.getMessage());
        verify(petRepository, never()).save(any(Pet.class));
    }

    @ParameterizedTest
    @DisplayName("Throws PetDescriptionException when updatePet() is called with an invalid Size or Age")
    @CsvSource({
            "Invalid, Adult, Invalid size description: Invalid",
            "Medium, Invalid, Invalid age description: Invalid"
    })
    void shouldThrowPetDescriptionExceptionWhenSizeOrAgeIsInvalid(String size, String age, String exMessage) {

        PetUpdateDto invalidDto = PetUpdateDto.builder()
                .size(size)
                .age(age)
                .description("Max is an updated dog")
                .imageUrl("https://www.updatedimages.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationId("777777-77777777-7777")
                .build();

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));

        PetDescriptionException ex = assertThrows(PetDescriptionException.class, () -> petService.updatePet(petId, invalidDto));

        assertEquals(exMessage, ex.getMessage());
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Returns a String when Pet is deleted successfully, providing a valid ID")
    void shouldReturnStringWhenPetIsDeletedSuccessfully() throws PetNotFoundException {

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        String expectedString = PET_WITH_ID + petId + DELETE_SUCCESS;

        String actual = petService.deletePet(petId);

        assertEquals(expectedString, actual);
        verify(petRepository, times(1)).deleteById(petId);
    }

    @Test
    @DisplayName("Throws PetNotFoundException when deletePet() is called with an invalid ID")
    void shouldThrowPetNotFoundExceptionWhenDeletePetIsCalledWithInvalidId() {

        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        PetNotFoundException ex = assertThrows(PetNotFoundException.class, () -> petService.deletePet(petId));

        assertEquals(PET_WITH_ID + petId + NOT_FOUND, ex.getMessage());
        verify(petRepository, never()).deleteById(petId);
    }

    @Test
    @DisplayName("Returns a List of PetGetDto when data is valid")
    void shouldReturnPetGetDtoListWhenDataIsValid() throws PetDescriptionException {

        List<Pet> mockPets = List.of(pet);
        when(petRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(mockPets));

        List<PetGetDto> result = petService.searchPets(criteria, page, size, sortBy);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(pet, mockPets.getFirst());
        verify(petRepository, times(1)).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    @DisplayName("Returns empty list if data is valid but no filter matches")
    void shouldReturnEmptyListValidDataNoMatches() throws PetDescriptionException {

        when(petRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        List<PetGetDto> result = petService.searchPets(criteria, page, size, sortBy);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(petRepository, times(1)).findAll(any(Specification.class), any(PageRequest.class));
    }

    @ParameterizedTest
    @DisplayName("Throws PetDescriptionException when searchPets() is called with invalid enum fields")
    @CsvSource({
            "Invalid, Short, Medium, Adult, Invalid gender description: Invalid",
            "Male, Invalid, Medium, Adult, Invalid coat description: Invalid",
            "Male, Short, Invalid, Adult, Invalid size description: Invalid",
            "Male, Short, Medium, Invalid, Invalid age description: Invalid"
    })
    void shouldThrowPetDescriptionExceptionWhenEnumsInvalid(String gender, String coat, String sizes, String age, String expectedMessage) {

        PetSearchCriteria invalidCriteria = PetSearchCriteria.builder()
                .nameLike(null)
                .species(null)
                .breed(null)
                .color(null)
                .gender(gender)
                .coat(coat)
                .size(sizes)
                .age(age)
                .isAdopted(null)
                .isSterilized(null)
                .isVaccinated(null)
                .isChipped(null)
                .isSpecialNeeds(null)
                .isHouseTrained(null)
                .goodWithKids(null)
                .goodWithDogs(null)
                .goodWithCats(null)
                .isPureBreed(null)
                .state(null)
                .city(null)
                .build();

        PetDescriptionException ex = assertThrows(PetDescriptionException.class, () -> petService.searchPets(invalidCriteria, page, size, sortBy));

        assertTrue(ex.getMessage().contains(expectedMessage));
        verify(petRepository, never()).findAll(any(Specification.class), any(PageRequest.class));
    }
}