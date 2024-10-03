package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.PetController;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.exception.breed.BreedMismatchException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetDescriptionException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.service.interfaces.PetServiceI;
import com.petadoption.center.specifications.PetSearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.petSearchCriteria;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PetControllerTest {

    @InjectMocks
    private PetController petController;
    @Mock
    private PetServiceI petService;

    private PetGetDto petGetDto;
    private PetCreateDto petCreateDto;
    private PetUpdateDto petUpdateDto;

    @BeforeEach
    void setup(){
        petGetDto = petGetDto();
        petCreateDto = petCreateDto("Max");
        petUpdateDto = petUpdateDto();
    }

    @Test
    @DisplayName("Returns a PetGetDto when getPetById() is called with a valid ID")
    void shouldReturnPetGetDtoWhenIdIsValid() throws PetNotFoundException {

        when(petService.getPetById(anyString())).thenReturn(petGetDto);

        ResponseEntity<PetGetDto> actual = petController.getPetById(anyString());

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(petGetDto, actual.getBody());
        verify(petService, times(1)).getPetById(anyString());
    }

    @Test
    @DisplayName("Throws PetNotFoundException when getPetById() is called with an invalid ID")
    void shouldThrowPetNotFoundExceptionWhenIdIsInvalid() throws PetNotFoundException {

        when(petService.getPetById(anyString())).thenThrow(new PetNotFoundException("Invalid Id"));

        PetNotFoundException ex = assertThrows(PetNotFoundException.class, () -> petController.getPetById(anyString()));

        assertEquals("Invalid Id", ex.getMessage());
        verify(petService, times(1)).getPetById(anyString());
    }

    @Test
    @DisplayName("Returns a list of PetGetDto when searchPets() is called with a valid PetSearchCriteria, page, size and sortBy parameters")
    void shouldReturnPetGetDtoListIfAllDataIsValid() throws PetDescriptionException {

        List<PetGetDto> expected = List.of(petGetDto);

        when(petService.searchPets(any(PetSearchCriteria.class), anyInt(), anyInt(), anyString())).thenReturn(expected);

        ResponseEntity<List<PetGetDto>> actual = petController.searchPets(petSearchCriteria(), 0, 0, "id");

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(petService, times(1)).searchPets(any(PetSearchCriteria.class), anyInt(), anyInt(), anyString());
    }

    @Test
    @DisplayName("Throws PetDescriptionException when searchPets() is called with an invalid PetSearchCriteria")
    void shouldThrowPetDescriptionExceptionWhenPetSearchCriteriaIsInvalid() throws PetDescriptionException {

        when(petService.searchPets(any(PetSearchCriteria.class), anyInt(), anyInt(), anyString())).thenThrow(new PetDescriptionException("Invalid Description"));

        PetDescriptionException ex = assertThrows(PetDescriptionException.class, () -> petService.searchPets(petSearchCriteria(), 0, 0, "id"));

        assertEquals("Invalid Description", ex.getMessage());
        verify(petService, times(1)).searchPets(any(PetSearchCriteria.class), anyInt(), anyInt(), anyString());
    }

    @Test
    @DisplayName("Returns a PetGetDto when addNewPet() is called with a valid PetCreateDto")
    void shouldReturnPetGetDtoWhenPetCreateDtoIsValid() throws OrgNotFoundException, PetDescriptionException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        when(petService.addNewPet(any(PetCreateDto.class))).thenReturn(petGetDto);

        ResponseEntity<PetGetDto> actual = petController.addNewPet(petCreateDto);

        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(petGetDto, actual.getBody());
        verify(petService, times(1)).addNewPet(any(PetCreateDto.class));
    }

    @Test
    @DisplayName("Throws OrgNotFoundException when addNewPet() is called with an invalid organization")
    void shouldThrowOrgNotFoundExceptionWhenOrganizationIsNotFound() throws OrgNotFoundException, PetDescriptionException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        when(petService.addNewPet(any(PetCreateDto.class))).thenThrow(new OrgNotFoundException("Org not Found"));

        OrgNotFoundException ex = assertThrows(OrgNotFoundException.class, () -> petController.addNewPet(petCreateDto));

        assertEquals("Org not Found", ex.getMessage());
        verify(petService, times(1)).addNewPet(any(PetCreateDto.class));
    }

    @Test
    @DisplayName("Throws BreedNotFoundException when addNewPet() is called with an invalid breed")
    void shouldThrowBreedNotFoundExceptionWhenBreedIsNotFound() throws OrgNotFoundException, PetDescriptionException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        when(petService.addNewPet(any(PetCreateDto.class))).thenThrow(new BreedNotFoundException("Breed not Found"));

        BreedNotFoundException ex = assertThrows(BreedNotFoundException.class, () -> petController.addNewPet(petCreateDto));

        assertEquals("Breed not Found", ex.getMessage());
        verify(petService, times(1)).addNewPet(any(PetCreateDto.class));
    }

    @Test
    @DisplayName("Throws ColorNotFoundException when addNewPet() is called with an invalid color")
    void shouldThrowColorNotFoundExceptionWhenColorIsNotFound() throws OrgNotFoundException, PetDescriptionException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        when(petService.addNewPet(any(PetCreateDto.class))).thenThrow(new ColorNotFoundException("Color not Found"));

        ColorNotFoundException ex = assertThrows(ColorNotFoundException.class, () -> petController.addNewPet(petCreateDto));

        assertEquals("Color not Found", ex.getMessage());
        verify(petService, times(1)).addNewPet(any(PetCreateDto.class));
    }

    @Test
    @DisplayName("Throws SpeciesNotFoundException when addNewPet() is called with an invalid species")
    void shouldThrowSpeciesNotFoundExceptionWhenSpeciesIsNotFound() throws OrgNotFoundException, PetDescriptionException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        when(petService.addNewPet(any(PetCreateDto.class))).thenThrow(new SpeciesNotFoundException("Species not Found"));

        SpeciesNotFoundException ex = assertThrows(SpeciesNotFoundException.class, () -> petController.addNewPet(petCreateDto));

        assertEquals("Species not Found", ex.getMessage());
        verify(petService, times(1)).addNewPet(any(PetCreateDto.class));
    }

    @Test
    @DisplayName("Throws BreedMismatchException when addNewPet() is called with an invalid breed/species")
    void shouldThrowBreedMismatchExceptionWhenBreedAndSpeciesDoNotMatch() throws OrgNotFoundException, PetDescriptionException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        when(petService.addNewPet(any(PetCreateDto.class))).thenThrow(new BreedMismatchException("Breed no match"));

        BreedMismatchException ex = assertThrows(BreedMismatchException.class, () -> petController.addNewPet(petCreateDto));

        assertEquals("Breed no match", ex.getMessage());
        verify(petService, times(1)).addNewPet(any(PetCreateDto.class));
    }

    @Test
    @DisplayName("Throws PetDescriptionException when addNewPet() is called with an invalid enum properties")
    void shouldThrowPetDescriptionExceptionWhenEnumsAreInvalid() throws OrgNotFoundException, PetDescriptionException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        when(petService.addNewPet(any(PetCreateDto.class))).thenThrow(new PetDescriptionException("Invalid description"));

        PetDescriptionException ex = assertThrows(PetDescriptionException.class, () -> petController.addNewPet(petCreateDto));

        assertEquals("Invalid description", ex.getMessage());
        verify(petService, times(1)).addNewPet(any(PetCreateDto.class));
    }

    @Test
    @DisplayName("Returns the string Added when addListOfNewPets() is called with a list of valid PetCreateDto")
    void shouldReturnStringWhenAllPetCreateDtoInListAreValid() throws OrgNotFoundException, PetDescriptionException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        List<PetCreateDto> createDtos = List.of(petCreateDto);

        doNothing().when(petService).addListOfNewPets(anyList());

        ResponseEntity<String> actual = petController.addListOfNewPets(createDtos);

        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals("Added", actual.getBody());
        verify(petService, times(1)).addListOfNewPets(anyList());
    }

    @Test
    @DisplayName("Throws OrgNotFoundException when addListOfNewPets() is called with a list of PetCreateDto, with at least one element with an invalid organization ")
    void shouldThrowOrgNotFoundExceptionWhenOrgIsNotFoundInAListElement() throws OrgNotFoundException, PetDescriptionException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        doThrow(new OrgNotFoundException("Org not found")).when(petService).addListOfNewPets(anyList());
    }

    @Test
    @DisplayName("Throws BreedNotFoundException when addListOfNewPets() is called with a list of PetCreateDto, with at least one element with an invalid breed ")
    void shouldThrowBreedNotFoundExceptionWhenBreedIsNotFoundInAListElement(){

    }

    @Test
    @DisplayName("Throws ColorNotFoundException when addListOfNewPets() is called with a list of PetCreateDto, with at least one element with an invalid color ")
    void shouldThrowColorNotFoundExceptionWhenColorIsNotFoundInAListElement(){

    }

    @Test
    @DisplayName("Throws SpeciesNotFoundException when addListOfNewPets() is called with a list of PetCreateDto, with at least one element with an invalid species ")
    void shouldThrowSpeciesNotFoundExceptionWhenSpeciesIsNotFoundInAListElement(){

    }

    @Test
    @DisplayName("Throws BreedMismatchException when addListOfNewPets() is called with a list of PetCreateDto, with at least one element with an invalid breed/species ")
    void shouldThrowBreedMismatchExceptionWhenBreedAndSpeciesDoNotMatchInAListElement(){

    }

    @Test
    @DisplayName("Throws PetDescriptionException when addListOfNewPets() is called with a list of PetCreateDto, with at least one element with an invalid enum field ")
    void shouldThrowPetDescriptionExceptionWhenEnumFieldInvalidInAListElement(){

    }
}
