package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.AdoptionFormController;
import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.exception.not_found.AdoptionFormNotFoundException;
import com.petadoption.center.exception.not_found.PetNotFoundException;
import com.petadoption.center.exception.not_found.UserNotFoundException;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import com.petadoption.center.service.interfaces.AdoptionFormServiceI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static com.petadoption.center.util.Messages.ADOPTION_FORM_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AdoptionFormControllerTest {

    @Mock
    AdoptionFormServiceI adoptionFormServiceI;

    @InjectMocks
    AdoptionFormController adoptionFormController;

    private AdoptionFormCreateDto adoptionFormCreateDto;
    private AdoptionFormGetDto adoptionFormGetDto;
    private AdoptionFormUpdateDto adoptionFormUpdateDto;
    private Family family;
    private Address address;
    private UserGetDto userGetDto;
    private PetGetDto petGetDto;

    @Test
    @DisplayName("Test if get all adoption forms works correctly")
    void testGetAll() {

        adoptionFormGetDto = new AdoptionFormGetDto(
                "1111-1111-2222",
                userGetDto,
                petGetDto,
                family,
                "Neighbour",
                true,
                "Other Notes",
                address,
                LocalDateTime.now()
        );

        int page = 0;
        int size = 10;
        String sort = "created_at";
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        List<AdoptionFormGetDto> listOfAdoptionForms = List.of(adoptionFormGetDto);

        when(adoptionFormServiceI.getAll(pageable)).thenReturn(listOfAdoptionForms);

        ResponseEntity<List<AdoptionFormGetDto>> response = adoptionFormController.getAll(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listOfAdoptionForms, response.getBody());

        verify(adoptionFormServiceI).getAll(pageable);
    }

    @Test
    @DisplayName("Test if get adoption form by id works correctly")
    void testGetById() throws AdoptionFormNotFoundException {

        String id = "1111-1111-2222";

        when(adoptionFormServiceI.getById(id)).thenReturn(adoptionFormGetDto);

        ResponseEntity<AdoptionFormGetDto> response = adoptionFormController.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adoptionFormGetDto, response.getBody());

        verify(adoptionFormServiceI).getById(id);
    }

    @Test
    @DisplayName("Test if add new adoption form works correctly")
    void testAddAdoptionForm() throws UserNotFoundException, PetNotFoundException {

       when(adoptionFormServiceI.create(adoptionFormCreateDto)).thenReturn(adoptionFormGetDto);

       ResponseEntity<AdoptionFormGetDto> response = adoptionFormController.create(adoptionFormCreateDto);

       assertEquals(HttpStatus.CREATED, response.getStatusCode());
       assertEquals(adoptionFormGetDto, response.getBody());

       verify(adoptionFormServiceI).create(adoptionFormCreateDto);
    }

    @Test
    @DisplayName("Test if update adoption form works correctly")
    void testUpdate() throws AdoptionFormNotFoundException {

        String id = "1111-1111-2222";

        when(adoptionFormServiceI.update(id, adoptionFormUpdateDto)).thenReturn(adoptionFormGetDto);

        ResponseEntity<AdoptionFormGetDto> response = adoptionFormController.update(id, adoptionFormUpdateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adoptionFormGetDto, response.getBody());

        verify(adoptionFormServiceI).update(id, adoptionFormUpdateDto);
    }

    @Test
    @DisplayName("Test if delete adoption form works correctly")
    void testDelete() throws AdoptionFormNotFoundException {

        String id = "1111-1111-2222";

        when(adoptionFormServiceI.delete(id)).thenReturn(format(ADOPTION_FORM_DELETE_MESSAGE, id));

        ResponseEntity<String> response = adoptionFormController.delete(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(format(ADOPTION_FORM_DELETE_MESSAGE, id), response.getBody());

        verify(adoptionFormServiceI).delete(id);
    }
}