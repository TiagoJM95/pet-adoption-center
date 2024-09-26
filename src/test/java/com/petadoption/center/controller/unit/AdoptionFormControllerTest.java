package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.AdoptionFormController;
import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.exception.adoptionform.AdoptionFormNotFoundException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import com.petadoption.center.service.interfaces.AdoptionFormServiceI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.USER_WITH_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
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
    void testGetAllAdoptionForms() {

        adoptionFormGetDto = new AdoptionFormGetDto(
                "1111-1111-2222",
                userGetDto,
                petGetDto,
                family,
                "Neighbour",
                true,
                "Other Notes",
                address
        );

        int page = 0;
        int size = 5;
        String sortBy = "id";

        List<AdoptionFormGetDto> listOfAdoptionForms = List.of(adoptionFormGetDto);

        when(adoptionFormServiceI.getAllAdoptionForms(page, size, sortBy)).thenReturn(listOfAdoptionForms);

        ResponseEntity<List<AdoptionFormGetDto>> response = adoptionFormController.getAllAdoptionForms(page, size, sortBy);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listOfAdoptionForms, response.getBody());

        verify(adoptionFormServiceI).getAllAdoptionForms(page, size, sortBy);
    }

    @Test
    @DisplayName("Test if get adoption form by id works correctly")
    void testGetAdoptionFormById() throws AdoptionFormNotFoundException {

        String id = "1111-1111-2222";

        when(adoptionFormServiceI.getAdoptionFormById(id)).thenReturn(adoptionFormGetDto);

        ResponseEntity<AdoptionFormGetDto> response = adoptionFormController.getAdoptionFormById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adoptionFormGetDto, response.getBody());

        verify(adoptionFormServiceI).getAdoptionFormById(id);
    }

    @Test
    @DisplayName("Test if add new adoption form works correctly")
    void testAddAdoptionForm() throws UserNotFoundException, PetNotFoundException {

       when(adoptionFormServiceI.addNewAdoptionForm(adoptionFormCreateDto)).thenReturn(adoptionFormGetDto);

       ResponseEntity<AdoptionFormGetDto> response = adoptionFormController.addNewAdoptionForm(adoptionFormCreateDto);

       assertEquals(HttpStatus.CREATED, response.getStatusCode());
       assertEquals(adoptionFormGetDto, response.getBody());

       verify(adoptionFormServiceI).addNewAdoptionForm(adoptionFormCreateDto);
    }

    @Test
    @DisplayName("Test if update adoption form works correctly")
    void testUpdateAdoptionForm() throws AdoptionFormNotFoundException {

        String id = "1111-1111-2222";

        when(adoptionFormServiceI.updateAdoptionForm(id, adoptionFormUpdateDto)).thenReturn(adoptionFormGetDto);

        ResponseEntity<AdoptionFormGetDto> response = adoptionFormController.updateAdoptionForm(id, adoptionFormUpdateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adoptionFormGetDto, response.getBody());

        verify(adoptionFormServiceI).updateAdoptionForm(id, adoptionFormUpdateDto);
    }

    @Test
    @DisplayName("Test if delete adoption form works correctly")
    void testDeleteAdoptionForm() throws AdoptionFormNotFoundException {

        String id = "1111-1111-2222";

        when(adoptionFormServiceI.deleteAdoptionForm(id)).thenReturn(USER_WITH_ID + id + DELETE_SUCCESS);

        ResponseEntity<String> response = adoptionFormController.deleteAdoptionForm(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(USER_WITH_ID + id + DELETE_SUCCESS, response.getBody());

        verify(adoptionFormServiceI).deleteAdoptionForm(id);
    }
}
