package com.petadoption.center.service.pet;

import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.exception.BreedMismatchException;
import com.petadoption.center.exception.not_found.BreedNotFoundException;
import com.petadoption.center.exception.not_found.ColorNotFoundException;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;
import com.petadoption.center.exception.not_found.SpeciesNotFoundException;
import com.petadoption.center.model.Pet;
import com.petadoption.center.repository.PetRepository;
import com.petadoption.center.service.PetService;
import com.petadoption.center.service.interfaces.BreedServiceI;
import com.petadoption.center.service.interfaces.ColorServiceI;
import com.petadoption.center.service.interfaces.OrganizationServiceI;
import com.petadoption.center.service.interfaces.SpeciesServiceI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.*;
import static com.petadoption.center.util.Messages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PetServiceCreationTest {

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

    private static Pet pet;
    private static PetGetDto petGetDto;
    private static PetCreateDto petCreateDto;

    @BeforeAll
    static void setup() {
        pet = createPet();
        petGetDto = petGetDto();
        petCreateDto = petCreateDto("Max");
    }

    static Stream<List<PetCreateDto>> petCreateDtoListProvider() {
        PetCreateDto problematicDto = PetCreateDto.builder()
                .name("Max")
                .speciesId("111AAA-1111AAAA-11AA")
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


    // Create()


    @Test
    @DisplayName("Saves Pet in DB and returns PetGetDto when create() is called with valid data")
    void shouldReturnPetGetDtoWhenPetIsSavedInDb() {

        doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        PetGetDto actual = petService.create(petCreateDto);

        assertEquals(petGetDto, actual);
        verify(breedService, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws BreedMismatchException when create() is called with breed not belonging to species")
    void shouldThrowBreedMismatchExceptionDuringCreationWhenBreedAndSpeciesMismatch() {

        doThrow(new BreedMismatchException(BREED_SPECIES_MISMATCH))
                .when(breedService).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));

        BreedMismatchException ex = assertThrows(BreedMismatchException.class, () -> petService.create(petCreateDto));

        assertEquals(BREED_SPECIES_MISMATCH, ex.getMessage());
        verify(breedService, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws SpeciesNotFoundException when create() is called with invalid species")
    void shouldThrowSpeciesNotFoundExceptionDuringCreationWhenSpeciesIsNotFound() {

        doThrow(new SpeciesNotFoundException(SPECIES_WITH_ID + petCreateDto.speciesId() + NOT_FOUND))
                .when(breedService).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));

        SpeciesNotFoundException ex = assertThrows(SpeciesNotFoundException.class, () -> petService.create(petCreateDto));

        assertEquals(SPECIES_WITH_ID + petCreateDto.speciesId() + NOT_FOUND, ex.getMessage());
        verify(breedService, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws BreedNotFoundException when create() is called with invalid breed")
    void shouldThrowBreedNotFoundExceptionDuringCreationWhenBreedIsNotFound() {

        doThrow(new BreedNotFoundException(BREED_WITH_ID + petCreateDto.primaryBreedId() + NOT_FOUND))
                .when(breedService).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));

        BreedNotFoundException ex = assertThrows(BreedNotFoundException.class, () -> petService.create(petCreateDto));

        assertEquals(BREED_WITH_ID + petCreateDto.primaryBreedId() + NOT_FOUND, ex.getMessage());
        verify(breedService, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws ColorNotFoundException when create() is called with invalid color")
    void shouldThrowColorNotFoundExceptionDuringCreationWhenColorIsNotFound() {

        doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        when(colorService.getById(anyString()))
                .thenThrow(new ColorNotFoundException(COLOR_WITH_ID + petCreateDto.primaryColor() + NOT_FOUND));

        ColorNotFoundException ex = assertThrows(ColorNotFoundException.class, () -> petService.create(petCreateDto));

        assertEquals(COLOR_WITH_ID + petCreateDto.primaryColor() + NOT_FOUND, ex.getMessage());
        verify(breedService, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws OrganizationNotFoundException when create() is called with invalid org")
    void shouldThrowOrgNotFoundExceptionDuringCreationWhenOrganizationIsNotFound() {

        doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        when(organizationService.getById(anyString()))
                .thenThrow(new OrganizationNotFoundException(ORG_WITH_ID + petCreateDto.organizationId() + NOT_FOUND));

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class, () -> petService.create(petCreateDto));

        assertEquals(ORG_WITH_ID + petCreateDto.organizationId() + NOT_FOUND, ex.getMessage());
        verify(breedService, times(1)).verifyIfBreedsAndSpeciesMatch(petCreateDto);
        verify(petRepository, never()).save(any(Pet.class));
    }


    // createFromList()


    @Test
    @DisplayName("Returns a String when createFromList() is called with a list of valid Dto")
    void shouldReturnAStringWhenAddingAValidListOfNewPets() {

        List<PetCreateDto> pets = List.of(petCreateDto, petCreateDto("Bobby"), petCreateDto("Spike"));

        doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(any());
        when(petRepository.save(any(Pet.class))).thenReturn(any(Pet.class));

        String actual = petService.createFromList(pets);

        assertEquals(PET_LIST_ADDED_SUCCESS, actual);
        verify(breedService, times(pets.size())).verifyIfBreedsAndSpeciesMatch(any());
        verify(petRepository, times(pets.size())).save(any(Pet.class));
    }

    @ParameterizedTest
    @DisplayName("Throws BreedMismatchException when createFromList() is called with breed not belonging to species in any position of the List")
    @MethodSource("petCreateDtoListProvider")
    void shouldThrowBreedMismatchExceptionDuringCreationWhenBreedAndSpeciesMismatchInList(List<PetCreateDto> pets) {

        PetCreateDto problematicDto = pets.stream()
                .filter(p -> p.name().equals("Max"))
                .findFirst()
                .orElseThrow();

        lenient().doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));
        doThrow(new BreedMismatchException(BREED_SPECIES_MISMATCH)).when(breedService).verifyIfBreedsAndSpeciesMatch(problematicDto);

        BreedMismatchException ex = assertThrows(BreedMismatchException.class, () -> petService.createFromList(pets));

        assertEquals(BREED_SPECIES_MISMATCH, ex.getMessage());
        verify(breedService, times(pets.indexOf(problematicDto)+1)).verifyIfBreedsAndSpeciesMatch(any());
        verify(petRepository, times(pets.indexOf(problematicDto))).save(any(Pet.class));
    }

    @ParameterizedTest
    @DisplayName("Throws SpeciesNotFoundException when createFromList() is called with an invalid species in any position of the List")
    @MethodSource("petCreateDtoListProvider")
    void shouldThrowSpeciesNotFoundExceptionDuringCreationWhenSpeciesNotFound(List<PetCreateDto> pets) {

        PetCreateDto problematicDto = pets.stream()
                .filter(p -> p.name().equals("Max"))
                .findFirst()
                .orElseThrow();

        lenient().doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));
        doThrow(new SpeciesNotFoundException(SPECIES_WITH_ID + problematicDto.speciesId() + NOT_FOUND))
                .when(breedService).verifyIfBreedsAndSpeciesMatch(problematicDto);

        SpeciesNotFoundException ex = assertThrows(SpeciesNotFoundException.class, () -> petService.createFromList(pets));

        assertEquals(SPECIES_WITH_ID + problematicDto.speciesId() + NOT_FOUND, ex.getMessage());
        verify(breedService, times(pets.indexOf(problematicDto)+1)).verifyIfBreedsAndSpeciesMatch(any());
        verify(petRepository, times(pets.indexOf(problematicDto))).save(any(Pet.class));
    }

    @ParameterizedTest
    @DisplayName("Throws BreedNotFoundException when createFromList() is called with an invalid breed in any position of the List")
    @MethodSource("petCreateDtoListProvider")
    void shouldThrowBreedNotFoundExceptionDuringCreationWhenBreedNotFound(List<PetCreateDto> pets) {

        PetCreateDto problematicDto = pets.stream()
                .filter(p -> p.name().equals("Max"))
                .findFirst()
                .orElseThrow();

        lenient().doNothing().when(breedService).verifyIfBreedsAndSpeciesMatch(any(PetCreateDto.class));
        doThrow(new BreedNotFoundException(BREED_WITH_ID + problematicDto.primaryBreedId() + NOT_FOUND))
                .when(breedService).verifyIfBreedsAndSpeciesMatch(problematicDto);

        BreedNotFoundException ex = assertThrows(BreedNotFoundException.class, () -> petService.createFromList(pets));

        assertEquals(BREED_WITH_ID + problematicDto.primaryBreedId() + NOT_FOUND, ex.getMessage());
        verify(breedService, times(pets.indexOf(problematicDto)+1)).verifyIfBreedsAndSpeciesMatch(any());
        verify(petRepository, times(pets.indexOf(problematicDto))).save(any(Pet.class));
    }

    @ParameterizedTest
    @DisplayName("Throws ColorNotFoundException when createFromList() is called with an invalid color in any position of the List")
    @MethodSource("petCreateDtoListProvider")
    void shouldThrowColorNotFoundExceptionDuringCreationWhenColorNotFound(List<PetCreateDto> pets) {

        PetCreateDto problematicDto = pets.stream()
                .filter(p -> p.name().equals("Max"))
                .findFirst()
                .orElseThrow();

        when(colorService.getById(anyString())).thenAnswer(invocation -> {
            String colorId = invocation.getArgument(0);
            if (colorId.equals(problematicDto.primaryColor())) {
                throw new ColorNotFoundException(COLOR_WITH_ID + colorId + NOT_FOUND);
            }
            return any(ColorGetDto.class);
        });

        ColorNotFoundException ex = assertThrows(ColorNotFoundException.class, () -> petService.createFromList(pets));

        assertEquals(COLOR_WITH_ID + problematicDto.primaryColor() + NOT_FOUND, ex.getMessage());
        verify(petRepository, times(pets.indexOf(problematicDto))).save(any(Pet.class));
    }

    @ParameterizedTest
    @DisplayName("Throws OrganizationNotFoundException when createFromList() is called with an invalid org in any position of the List")
    @MethodSource("petCreateDtoListProvider")
    void shouldThrowOrgNotFoundExceptionDuringCreationWhenOrganizationNotFound(List<PetCreateDto> pets) {

        PetCreateDto problematicDto = pets.stream()
                .filter(p -> p.name().equals("Max"))
                .findFirst()
                .orElseThrow();

        when(organizationService.getById(anyString())).thenAnswer(invocation -> {
            String orgId = invocation.getArgument(0);
            if (orgId.equals(problematicDto.organizationId())) {
                throw new OrganizationNotFoundException(ORG_WITH_ID + orgId + NOT_FOUND);
            }
            return any(OrganizationGetDto.class);
        });

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class, () -> petService.createFromList(pets));

        assertEquals(ORG_WITH_ID + problematicDto.organizationId() + NOT_FOUND, ex.getMessage());
        verify(petRepository, times(pets.indexOf(problematicDto))).save(any(Pet.class));
    }
}
