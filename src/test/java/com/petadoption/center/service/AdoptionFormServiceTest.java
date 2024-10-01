package com.petadoption.center.service;

import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.exception.adoptionform.AdoptionFormNotFoundException;
import com.petadoption.center.model.AdoptionForm;
import com.petadoption.center.repository.AdoptionFormRepository;
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

import static com.petadoption.center.testUtils.TestEntityFactory.createAdoptionForm;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AdoptionFormServiceTest {

    @InjectMocks
    private AdoptionFormService adoptionFormService;

    @Mock
    private AdoptionFormRepository adoptionFormRepository;

    private static AdoptionForm adoptionForm;

    @BeforeAll
    static void setUp() {
        adoptionForm = createAdoptionForm();
    }

    @Test
    @DisplayName("Test it getAllAdoptionForms works correctly")
    void testGetAllAdoptionFormsReturnsListOfAdoptionForms() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "createdAt");

        Page<AdoptionForm> adoptionFormPage = new PageImpl<>(List.of(adoptionForm));

        when(adoptionFormRepository.findAll(pageRequest)).thenReturn(adoptionFormPage);

        List<AdoptionFormGetDto> result = adoptionFormService.getAllAdoptionForms(0, 10, "createdAt");

        assertEquals(1, result.size());
        assertEquals(adoptionForm.getId(), result.getFirst().id());
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

        when(adoptionFormRepository.findById(adoptionForm.getId())).thenReturn(Optional.of(adoptionForm));

        AdoptionFormGetDto result = adoptionFormService.getAdoptionFormById(adoptionForm.getId());

        assertEquals(adoptionForm.getId(), result.id());
    }

    @Test
    @DisplayName("Test if get AdoptionForm by non-existent id throws AdoptionFormNotFoundException")
    void testGetAdoptionFormByNonExistentIdThrowsException() {

        when(adoptionFormRepository.findById(adoptionForm.getId())).thenReturn(Optional.empty());

        assertThrows(AdoptionFormNotFoundException.class, () -> adoptionFormService.getAdoptionFormById(adoptionForm.getId()));
    }
}
