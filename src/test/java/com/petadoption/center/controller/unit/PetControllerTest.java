package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.PetController;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.exception.breed.BreedMismatchException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrganizationNotFoundException;
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
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.PET_WITH_ID;
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
    void shouldReturnPetGetDtoListIfAllDataIsValid() {

        List<PetGetDto> expected = List.of(petGetDto);

        when(petService.searchPets(any(PetSearchCriteria.class), anyInt(), anyInt(), anyString())).thenReturn(expected);

        ResponseEntity<List<PetGetDto>> actual = petController.searchPets(petSearchCriteria(), 0, 0, "id");

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(petService, times(1)).searchPets(any(PetSearchCriteria.class), anyInt(), anyInt(), anyString());
    }

    @Test
    @DisplayName("Returns a PetGetDto when addNewPet() is called with a valid PetCreateDto")
    void shouldReturnPetGetDtoWhenPetCreateDtoIsValid() throws OrganizationNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        when(petService.addNewPet(any(PetCreateDto.class))).thenReturn(petGetDto);

        ResponseEntity<PetGetDto> actual = petController.addNewPet(petCreateDto);

        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(petGetDto, actual.getBody());
        verify(petService, times(1)).addNewPet(any(PetCreateDto.class));
    }

    @Test
    @DisplayName("Throws OrganizationNotFoundException when addNewPet() is called with an invalid organization")
    void shouldThrowOrgNotFoundExceptionWhenOrganizationIsNotFound() throws OrganizationNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        when(petService.addNewPet(any(PetCreateDto.class))).thenThrow(new OrganizationNotFoundException("Org not Found"));

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class, () -> petController.addNewPet(petCreateDto));

        assertEquals("Org not Found", ex.getMessage());
        verify(petService, times(1)).addNewPet(any(PetCreateDto.class));
    }

    @Test
    @DisplayName("Throws BreedNotFoundException when addNewPet() is called with an invalid breed")
    void shouldThrowBreedNotFoundExceptionWhenBreedIsNotFound() throws OrganizationNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        when(petService.addNewPet(any(PetCreateDto.class))).thenThrow(new BreedNotFoundException("Breed not Found"));

        BreedNotFoundException ex = assertThrows(BreedNotFoundException.class, () -> petController.addNewPet(petCreateDto));

        assertEquals("Breed not Found", ex.getMessage());
        verify(petService, times(1)).addNewPet(any(PetCreateDto.class));
    }

    @Test
    @DisplayName("Throws ColorNotFoundException when addNewPet() is called with an invalid color")
    void shouldThrowColorNotFoundExceptionWhenColorIsNotFound() throws OrganizationNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        when(petService.addNewPet(any(PetCreateDto.class))).thenThrow(new ColorNotFoundException("Color not Found"));

        ColorNotFoundException ex = assertThrows(ColorNotFoundException.class, () -> petController.addNewPet(petCreateDto));

        assertEquals("Color not Found", ex.getMessage());
        verify(petService, times(1)).addNewPet(any(PetCreateDto.class));
    }

    @Test
    @DisplayName("Throws SpeciesNotFoundException when addNewPet() is called with an invalid species")
    void shouldThrowSpeciesNotFoundExceptionWhenSpeciesIsNotFound() throws OrganizationNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        when(petService.addNewPet(any(PetCreateDto.class))).thenThrow(new SpeciesNotFoundException("Species not Found"));

        SpeciesNotFoundException ex = assertThrows(SpeciesNotFoundException.class, () -> petController.addNewPet(petCreateDto));

        assertEquals("Species not Found", ex.getMessage());
        verify(petService, times(1)).addNewPet(any(PetCreateDto.class));
    }

    @Test
    @DisplayName("Throws BreedMismatchException when addNewPet() is called with an invalid breed/species")
    void shouldThrowBreedMismatchExceptionWhenBreedAndSpeciesDoNotMatch() throws OrganizationNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        when(petService.addNewPet(any(PetCreateDto.class))).thenThrow(new BreedMismatchException("Breed no match"));

        BreedMismatchException ex = assertThrows(BreedMismatchException.class, () -> petController.addNewPet(petCreateDto));

        assertEquals("Breed no match", ex.getMessage());
        verify(petService, times(1)).addNewPet(any(PetCreateDto.class));
    }

    @Test
    @DisplayName("Returns the string Added when addListOfNewPets() is called with a list of valid PetCreateDto")
    void shouldReturnStringWhenAllPetCreateDtoInListAreValid() throws OrganizationNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        List<PetCreateDto> createDtos = List.of(petCreateDto);

        doNothing().when(petService).addListOfNewPets(anyList());

        ResponseEntity<String> actual = petController.addListOfNewPets(createDtos);

        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals("Added", actual.getBody());
        verify(petService, times(1)).addListOfNewPets(anyList());
    }

    @Test
    @DisplayName("Throws OrganizationNotFoundException when addListOfNewPets() is called with a list of PetCreateDto, with at least one element with an invalid organization ")
    void shouldThrowOrgNotFoundExceptionWhenOrgIsNotFoundInAListElement() throws OrganizationNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        doThrow(new OrganizationNotFoundException("Org not found")).when(petService).addListOfNewPets(anyList());

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class, () -> petController.addListOfNewPets(List.of(petCreateDto)));

        assertEquals("Org not found", ex.getMessage());
        verify(petService, times(1)).addListOfNewPets(anyList());
    }

    @Test
    @DisplayName("Throws BreedNotFoundException when addListOfNewPets() is called with a list of PetCreateDto, with at least one element with an invalid breed ")
    void shouldThrowBreedNotFoundExceptionWhenBreedIsNotFoundInAListElement() throws OrganizationNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        doThrow(new BreedNotFoundException("Breed not found")).when(petService).addListOfNewPets(anyList());

        BreedNotFoundException ex = assertThrows(BreedNotFoundException.class, () -> petController.addListOfNewPets(List.of(petCreateDto)));

        assertEquals("Breed not found", ex.getMessage());
        verify(petService, times(1)).addListOfNewPets(anyList());
    }

    @Test
    @DisplayName("Throws ColorNotFoundException when addListOfNewPets() is called with a list of PetCreateDto, with at least one element with an invalid color ")
    void shouldThrowColorNotFoundExceptionWhenColorIsNotFoundInAListElement() throws OrganizationNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        doThrow(new ColorNotFoundException("Color not found")).when(petService).addListOfNewPets(anyList());

        ColorNotFoundException ex = assertThrows(ColorNotFoundException.class, () -> petController.addListOfNewPets(List.of(petCreateDto)));

        assertEquals("Color not found", ex.getMessage());
        verify(petService, times(1)).addListOfNewPets(anyList());
    }

    @Test
    @DisplayName("Throws SpeciesNotFoundException when addListOfNewPets() is called with a list of PetCreateDto, with at least one element with an invalid species ")
    void shouldThrowSpeciesNotFoundExceptionWhenSpeciesIsNotFoundInAListElement() throws OrganizationNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        doThrow(new SpeciesNotFoundException("Species not found")).when(petService).addListOfNewPets(anyList());

        SpeciesNotFoundException ex = assertThrows(SpeciesNotFoundException.class, () -> petController.addListOfNewPets(List.of(petCreateDto)));

        assertEquals("Species not found", ex.getMessage());
        verify(petService, times(1)).addListOfNewPets(anyList());
    }

    @Test
    @DisplayName("Throws BreedMismatchException when addListOfNewPets() is called with a list of PetCreateDto, with at least one element with an invalid breed/species ")
    void shouldThrowBreedMismatchExceptionWhenBreedAndSpeciesDoNotMatchInAListElement() throws OrganizationNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, SpeciesNotFoundException {

        doThrow(new BreedMismatchException("Breed mismatch")).when(petService).addListOfNewPets(anyList());

        BreedMismatchException ex = assertThrows(BreedMismatchException.class, () -> petController.addListOfNewPets(List.of(petCreateDto)));

        assertEquals("Breed mismatch", ex.getMessage());
        verify(petService, times(1)).addListOfNewPets(anyList());
    }

    @Test
    @DisplayName("Returns a PetGetDto when updatePet() with a valid ID and PetUpdateDto")
    void shouldReturnPetGetDtoWithValidIdAndUpdateDto() throws OrganizationNotFoundException, PetNotFoundException {

        when(petService.updatePet(anyString(), any(PetUpdateDto.class))).thenReturn(petGetDto);

        ResponseEntity<PetGetDto> actual = petController.updatePet(petGetDto.id(), petUpdateDto);

        assertEquals(petGetDto, actual.getBody());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        verify(petService, times(1)).updatePet(anyString(), any(PetUpdateDto.class));
    }

    @Test
    @DisplayName("Throws OrganizationNotFoundException when updatePet() with an invalid OrgID in PetUpdateDto")
    void shouldThrowOrgNotFoundExceptionIfOrgIdNotFound() throws OrganizationNotFoundException, PetNotFoundException {

        when(petService.updatePet(anyString(), any(PetUpdateDto.class))).thenThrow(new OrganizationNotFoundException("Org not found"));

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class, () -> petController.updatePet(petGetDto.id(), petUpdateDto));

        assertEquals("Org not found", ex.getMessage());
        verify(petService, times(1)).updatePet(anyString(), any(PetUpdateDto.class));
    }

    @Test
    @DisplayName("Throws PetNotFoundException when updatePet() with an invalid ID")
    void shouldThrowPetNotFoundExceptionIfIdNotFound() throws OrganizationNotFoundException, PetNotFoundException {

        when(petService.updatePet(anyString(), any(PetUpdateDto.class))).thenThrow(new PetNotFoundException("Pet not found"));

        PetNotFoundException ex = assertThrows(PetNotFoundException.class, () -> petController.updatePet(petGetDto.id(), petUpdateDto));

        assertEquals("Pet not found", ex.getMessage());
        verify(petService, times(1)).updatePet(anyString(), any(PetUpdateDto.class));
    }

    @Test
    @DisplayName("Returns a String when deletePet() is called with valid ID")
    void shouldReturnStringWhenIdIsValid() throws PetNotFoundException {

        when(petService.deletePet(anyString())).thenReturn(PET_WITH_ID + petGetDto.id() + DELETE_SUCCESS);

        ResponseEntity<String> actual = petController.deletePet(petGetDto.id());

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(PET_WITH_ID + petGetDto.id() + DELETE_SUCCESS, actual.getBody());
        verify(petService, times(1)).deletePet(anyString());
    }

    @Test
    @DisplayName("Returns a String when deletePet() is called with valid ID")
    void shouldThrowPetNotFoundException() throws PetNotFoundException {

        when(petService.deletePet(anyString())).thenThrow(new PetNotFoundException("Not found"));

        PetNotFoundException ex = assertThrows(PetNotFoundException.class, () -> petController.deletePet(petGetDto.id()));

        assertEquals("Not found", ex.getMessage());
        verify(petService, times(1)).deletePet(anyString());
    }
}
