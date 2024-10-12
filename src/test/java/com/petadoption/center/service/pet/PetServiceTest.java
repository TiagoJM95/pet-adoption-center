package com.petadoption.center.service.pet;

import com.petadoption.center.converter.PetConverter;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;
import com.petadoption.center.exception.not_found.PetNotFoundException;
import com.petadoption.center.model.Pet;
import com.petadoption.center.repository.PetRepository;
import com.petadoption.center.service.PetService;
import com.petadoption.center.service.interfaces.BreedServiceI;
import com.petadoption.center.service.interfaces.ColorServiceI;
import com.petadoption.center.service.interfaces.OrganizationServiceI;
import com.petadoption.center.service.interfaces.SpeciesServiceI;
import com.petadoption.center.specifications.PetSearchCriteria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.petadoption.center.testUtils.TestDtoFactory.petGetDto;
import static com.petadoption.center.testUtils.TestDtoFactory.petUpdateDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createPet;
import static com.petadoption.center.testUtils.TestEntityFactory.petSearchCriteria;
import static com.petadoption.center.util.Messages.*;
import static java.lang.String.format;
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

    private static Pet pet;
    private static Pet updatedPet;
    private static PetGetDto petGetDto;
    private static PetUpdateDto petUpdateDto;
    private static String petId;
    private static String invalidId;
    private static PetSearchCriteria criteria;
    private static Pageable pageable;

    @BeforeAll
    static void setup() {
        pet = createPet();
        updatedPet = createPet();
        updatedPet.setSize(Sizes.LARGE);
        updatedPet.setAge(Ages.SENIOR);
        updatedPet.setDescription("Max is an updated dog");
        updatedPet.setImageUrl("https://www.updatedimages.com");
        petId = pet.getId();
        petGetDto = petGetDto();
        petUpdateDto = petUpdateDto();
        invalidId = "invalidId";
        criteria = petSearchCriteria();
        pageable = PageRequest.of(0, 10, Sort.by("createdAt"));
    }

    @AfterEach
    void after() {
        pet = createPet();
    }


    // getByID()


    @Test
    @DisplayName("Returns PetGetDto when getPetById() is called with an valid ID")
    void shouldReturnPetGetDtoWhenPetExistsById() {
        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));

        PetGetDto actual = petService.getById(petId);

        assertEquals(petGetDto, actual);
        verify(petRepository, times(1)).findById(petId);
    }

    @Test
    @DisplayName("Throws PetNotFoundException when getPetById() is called with an invalid ID")
    void shouldThrowPetNotFoundExceptionWhenPetDoesNotExistById() {
        when(petRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class,
                () -> petService.getById(invalidId));

        verify(petRepository, times(1)).findById(invalidId);
    }


    // update()


    @Test
    @DisplayName("Returns a PetGetDto when updatePet() is called with a valid ID and dto")
    void shouldReturnPetGetDtoWhenPetIsUpdatedSuccessfully() {

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        when(petRepository.save(any(Pet.class))).thenReturn(updatedPet);
        PetGetDto expected = PetConverter.toDto(updatedPet);

        PetGetDto actual = petService.update(petId, petUpdateDto);

        assertEquals(expected, actual);
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws PetNotFoundException when updatePet() is called with an invalid ID")
    void shouldThrowPetNotFoundExceptionWhenIdIsInvalid() {

        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        PetNotFoundException ex = assertThrows(PetNotFoundException.class,
                () -> petService.update(petId, petUpdateDto));

        assertEquals(format(PET_NOT_FOUND, petId), ex.getMessage());
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Throws OrganizationNotFoundException when updatePet() is called with an invalid orgID in the DTO")
    void shouldThrowOrgNotFoundExceptionWhenOrgIdIsInvalid() {

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        when(organizationService.getById(petUpdateDto.organizationId()))
                .thenThrow(new OrganizationNotFoundException(format(ORG_NOT_FOUND, petUpdateDto.organizationId())));

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class,
                () -> petService.update(petId, petUpdateDto));

        assertEquals(format(ORG_NOT_FOUND, petUpdateDto.organizationId()), ex.getMessage());
        verify(petRepository, never()).save(any(Pet.class));
    }


    // delete()


    @Test
    @DisplayName("Returns a String when Pet is deleted successfully, providing a valid ID")
    void shouldReturnStringWhenPetIsDeletedSuccessfully() {

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        String expectedString = format(PET_DELETE_MESSAGE, petId);

        String actual = petService.delete(petId);

        assertEquals(expectedString, actual);
        verify(petRepository, times(1)).deleteById(petId);
    }

    @Test
    @DisplayName("Throws PetNotFoundException when deletePet() is called with an invalid ID")
    void shouldThrowPetNotFoundExceptionWhenDeleteIsCalledWithInvalidId() {

        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        PetNotFoundException ex = assertThrows(PetNotFoundException.class,
                () -> petService.delete(petId));

        assertEquals(format(PET_NOT_FOUND, petId), ex.getMessage());
        verify(petRepository, never()).deleteById(petId);
    }


    // search()


    @Test
    @DisplayName("Returns a List of PetGetDto when data is valid")
    void shouldReturnPetGetDtoListWhenDataIsValid() {

        List<Pet> mockPets = List.of(pet);
        List<PetGetDto> expectedDtos = List.of(petGetDto);
        when(petRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(mockPets));

        List<PetGetDto> result = petService.searchPets(criteria, pageable);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDtos.getFirst(), result.getFirst());
        verify(petRepository, times(1)).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    @DisplayName("Returns a List of PetGetDto even if PetSearchCriteria is null")
    void shouldReturnPetGetDtoListWhenPetSearchCriteriaIsNull() {

        List<Pet> mockPets = List.of(pet);
        List<PetGetDto> expectedDtos = List.of(petGetDto);

        when(petRepository.findAll(Specification.where(null), pageable))
                .thenReturn(new PageImpl<>(mockPets));

        List<PetGetDto> result = petService.searchPets(null, pageable);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDtos.getFirst(), result.getFirst());
        verify(petRepository, times(1)).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    @DisplayName("Returns empty list if data is valid but no filter matches")
    void shouldReturnEmptyListValidDataNoMatches() {

        when(petRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        List<PetGetDto> result = petService.searchPets(criteria, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(petRepository, times(1)).findAll(any(Specification.class), any(PageRequest.class));
    }
}