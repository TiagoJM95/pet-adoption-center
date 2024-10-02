package com.petadoption.center.service;

import com.petadoption.center.converter.AdoptionFormConverter;
import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.exception.adoptionform.AdoptionFormNotFoundException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.model.Pet;
import com.petadoption.center.repository.AdoptionFormRepository;
import com.petadoption.center.service.interfaces.PetServiceI;
import com.petadoption.center.service.interfaces.UserServiceI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.createAdoptionForm;
import static com.petadoption.center.util.Messages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AdoptionFormServiceTest {

    @InjectMocks
    private AdoptionFormService adoptionFormService;

    @Mock
    private AdoptionFormRepository adoptionFormRepository;

    @Mock
    private UserServiceI userServiceI;

    @Mock
    private PetServiceI petServiceI;

    private static AdoptionForm testAdoptionForm;
    private static AdoptionFormGetDto adoptionFormGetDto;
    private static AdoptionFormCreateDto adoptionFormCreateDto;
    private static AdoptionFormUpdateDto adoptionFormUpdateDto;


    @BeforeAll
    static void setUp() {
        testAdoptionForm = createAdoptionForm();
        adoptionFormGetDto = adoptionFormGetDto();
        adoptionFormCreateDto = adoptionFormCreateDto();
        adoptionFormUpdateDto = adoptionFormUpdateDto();
    }

    @Test
    @DisplayName("Test it getAllAdoptionForms works correctly")
    void testGetAllAdoptionFormsReturnsListOfAdoptionForms() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "createdAt");

        Page<AdoptionForm> adoptionFormPage = new PageImpl<>(List.of(testAdoptionForm));

        when(adoptionFormRepository.findAll(pageRequest)).thenReturn(adoptionFormPage);

        List<AdoptionFormGetDto> result = adoptionFormService.getAllAdoptionForms(0, 10, "createdAt");

        assertEquals(1, result.size());
        assertEquals(testAdoptionForm.getId(), result.getFirst().id());
    }

    @Test
    @DisplayName("Test if get all adoption forms return an empty list when repository has no entries")
    void testGetAllAdoptionFormsReturnsEmpty(){

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<AdoptionForm> adoptionFormPage = new PageImpl<>(List.of());

        when(adoptionFormRepository.findAll(pageRequest)).thenReturn(adoptionFormPage);

        List<AdoptionFormGetDto> result = adoptionFormService.getAllAdoptionForms(0,10,"id");

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test if get all adoption forms with page size 2 returns 2 entries")
    void testGetAllAdoptionFormsWithPageSize2Returns2Entries(){

        AdoptionForm adoptionForm1 = new AdoptionForm();
        AdoptionForm adoptionForm2 = new AdoptionForm();
        AdoptionForm adoptionForm3 = new AdoptionForm();

        List<AdoptionForm> adoptionFormList = List.of(adoptionForm1, adoptionForm2, adoptionForm3);
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.ASC, "createdAt");
        Page<AdoptionForm> adoptionFormPage = new PageImpl<>(List.of(adoptionForm1, adoptionForm2), pageRequest, adoptionFormList.size());

        when(adoptionFormRepository.findAll(pageRequest)).thenReturn(adoptionFormPage);

        List<AdoptionFormGetDto> result = adoptionFormService.getAllAdoptionForms(0,2,"createdAt");

        assertEquals(2, result.size());
        assertFalse(result.size() > 2);
    }

    @Test
    @DisplayName("Test if get all adoption forms return with descending order")
    void getAllAdoptionFormsShouldReturnAdoptionFormsInDescendingOrder(){

        AdoptionForm adoptionForm1 = new AdoptionForm();
        adoptionForm1.setOtherNotes("1");
        AdoptionForm adoptionForm2 = new AdoptionForm();
        adoptionForm2.setOtherNotes("2");
        AdoptionForm adoptionForm3 = new AdoptionForm();
        adoptionForm3.setOtherNotes("3");

        List<AdoptionForm> adoptionFormList = List.of(adoptionForm1, adoptionForm2, adoptionForm3);
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "otherNotes"));
        Page<AdoptionForm> pagedUsers = new PageImpl<>(adoptionFormList.reversed(), pageRequest, adoptionFormList.size());

        when(adoptionFormRepository.findAll(any(PageRequest.class))).thenReturn(pagedUsers);

        List<AdoptionFormGetDto> result = adoptionFormService.getAllAdoptionForms(0,3,"otherNotes");

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(adoptionForm3.getOtherNotes(), result.get(0).otherNotes());
        assertEquals(adoptionForm2.getOtherNotes(), result.get(1).otherNotes());
        assertEquals(adoptionForm1.getOtherNotes(), result.get(2).otherNotes());
    }

    @Test
    @DisplayName("Test if get AdoptionForm by id works correctly")
    void testGetAdoptionFormById() throws AdoptionFormNotFoundException {

        when(adoptionFormRepository.findById(testAdoptionForm.getId())).thenReturn(Optional.of(testAdoptionForm));

        AdoptionFormGetDto result = adoptionFormService.getAdoptionFormById(testAdoptionForm.getId());

        assertEquals(testAdoptionForm.getId(), result.id());
    }

    @Test
    @DisplayName("Test if get AdoptionForm by non-existent id throws AdoptionFormNotFoundException")
    void testGetAdoptionFormByNonExistentIdThrowsException() {

        when(adoptionFormRepository.findById(testAdoptionForm.getId())).thenReturn(Optional.empty());

        assertThrows(AdoptionFormNotFoundException.class, () -> adoptionFormService.getAdoptionFormById(testAdoptionForm.getId()));
    }

    @Test
    @DisplayName("Test if create AdoptionForm works correctly and returns AdoptionFormGetDto")
    void testCreateAdoptionFormWorksAndReturnsAdoptionFormGetDto() throws UserNotFoundException, PetNotFoundException {

        when(adoptionFormRepository.save(any(AdoptionForm.class))).thenReturn(testAdoptionForm);

        AdoptionFormGetDto result = adoptionFormService.addNewAdoptionForm(adoptionFormCreateDto);

        assertEquals(testAdoptionForm.getOtherNotes(), result.otherNotes());
    }

    @Test
    @DisplayName("Test throw UserNotFoundException when creating AdoptionForm when user Id is invalid")
    void testThrowUserNotFoundWhenCreatingAdoptionFormWithInvalidUserId() throws UserNotFoundException {

        String exceptionMessage = adoptionFormCreateDto.userId();

        when(userServiceI.getUserById(anyString())).thenThrow(new UserNotFoundException(exceptionMessage));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> adoptionFormService.addNewAdoptionForm(adoptionFormCreateDto));

        assertEquals(exceptionMessage, exception.getMessage());
        verify(adoptionFormRepository, never()).save(any(AdoptionForm.class));
    }

    @Test
    @DisplayName("Test throw UserNotFoundException when creating AdoptionForm when user Id is invalid")
    void testThrowPetNotFoundWhenCreatingAdoptionFormWithInvalidUserId() throws PetNotFoundException {

        String exceptionMessage = PET_WITH_ID + adoptionFormCreateDto.petId() + NOT_FOUND;

        when(petServiceI.getPetById(anyString())).thenThrow(new PetNotFoundException(exceptionMessage));

        PetNotFoundException exception = assertThrows(PetNotFoundException.class, () -> adoptionFormService.addNewAdoptionForm(adoptionFormCreateDto));

        assertEquals(exceptionMessage, exception.getMessage());
        verify(adoptionFormRepository, never()).save(any(AdoptionForm.class));
    }

    @Test
    @DisplayName("Test if update AdoptionForm saves all fields and returns AdoptionFormGetDto")
    void testUpdateAdoptionFormShouldSaveAllFieldsAndReturnAdoptionFormGetDto() throws AdoptionFormNotFoundException {

        AdoptionForm updatedAdoptionForm = createAdoptionForm();
        updatedAdoptionForm.setPetVacationHome("Pet Hotel");

        when(adoptionFormRepository.findById(testAdoptionForm.getId())).thenReturn(Optional.of(testAdoptionForm));
        when(adoptionFormRepository.save(any(AdoptionForm.class))).thenReturn(updatedAdoptionForm);
        AdoptionFormGetDto result = adoptionFormService.updateAdoptionForm(testAdoptionForm.getId(), adoptionFormUpdateDto);

        assertNotNull(adoptionFormUpdateDto);
        assertEquals(adoptionFormUpdateDto.petVacationHome(), result.petVacationHome());
    }

    @Test
    @DisplayName("Test if update AdoptionForm throws AdoptionFormNotFound exception when there is no AdoptionForm")
    void testUpdateAdoptionFormThrowsAdoptionFormNotFoundException(){

        when(adoptionFormRepository.findById(testAdoptionForm.getId())).thenReturn(Optional.empty());

        assertThrows(AdoptionFormNotFoundException.class, () -> adoptionFormService.updateAdoptionForm(testAdoptionForm.getId(), adoptionFormUpdateDto));
    }

    @Test
    @DisplayName("Test if delete AdoptionForm works correctly and returns message")
    void testDeleteAdoptionFormAndReturnConfirmationMessage() throws AdoptionFormNotFoundException {

        when(adoptionFormRepository.findById(testAdoptionForm.getId())).thenReturn(Optional.of(testAdoptionForm));

        assertEquals(adoptionFormService.deleteAdoptionForm(testAdoptionForm.getId()), ADOPTION_FORM_WITH_ID + testAdoptionForm.getId() + DELETE_SUCCESS);
    }

    @Test
    @DisplayName("Test if delete AdoptionForm throws AdoptionFormNotFound exception when there is no AdoptionForm")
    void testDeleteAdoptionFormThrowsAdoptionFormNotFoundException(){

        when(adoptionFormRepository.findById(testAdoptionForm.getId())).thenReturn(Optional.empty());

        assertThrows(AdoptionFormNotFoundException.class, () -> adoptionFormService.deleteAdoptionForm(testAdoptionForm.getId()));
    }
}
