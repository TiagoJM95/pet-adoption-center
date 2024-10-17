package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.OrganizationController;
import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;
import com.petadoption.center.service.interfaces.OrganizationServiceI;
import org.junit.jupiter.api.BeforeEach;
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

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.createAddress;
import static com.petadoption.center.testUtils.TestEntityFactory.createSocialMedia;
import static com.petadoption.center.util.Messages.ORG_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class OrganizationControllerTest {

    @Mock
    private OrganizationServiceI organizationServiceI;

    @InjectMocks
    private OrganizationController organizationController;

    private OrganizationGetDto organizationGetDto;
    private OrganizationCreateDto organizationCreateDto;
    private OrganizationUpdateDto organizationUpdateDto;
    private OrganizationGetDto updatedOrganizationGetDto;

    @BeforeEach
    void setUp() {
        organizationGetDto = orgGetDto();
        organizationCreateDto = organizationCreateDto();
        organizationUpdateDto = orgUpdateDto();
        updatedOrganizationGetDto = OrganizationGetDto.builder()
                .id("777777-77777777-7777")
                .name("Pet Adoption Center")
                .email("email@email.com")
                .nipc("123456789")
                .phoneNumber("123456789")
                .address(createAddress())
                .websiteUrl("https://www.org.com")
                .socialMedia(createSocialMedia())
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
    }

    @Test
    @DisplayName("Test if get all organizations works correctly")
    void getAllOrganizations() {

        int page = 0;
        int size = 10;
        String sort = "created_at";
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        List<OrganizationGetDto> expectedOrganizations = List.of(organizationGetDto);

        when(organizationServiceI.getAll(pageable)).thenReturn(expectedOrganizations);

        ResponseEntity<List<OrganizationGetDto>> response = organizationController.getAll(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOrganizations, response.getBody());
        verify(organizationServiceI, times(1)).getAll(pageable);
    }

    @Test
    @DisplayName("Test if get all organization return a empty list if no organizations in database")
    void getAllOrganizationsEmptyList() {

        int page = 0;
        int size = 10;
        String sort = "created_at";
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        when(organizationServiceI.getAll(pageable)).thenReturn(List.of());

        ResponseEntity<List<OrganizationGetDto>> response = organizationController.getAll(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(), response.getBody());
        verify(organizationServiceI, times(1)).getAll(pageable);
    }


    @Test
    @DisplayName("Test if get organization by id works correctly")
    void getOrganizationById(){

        when(organizationServiceI.getById(organizationGetDto.id())).thenReturn(organizationGetDto);

        ResponseEntity<OrganizationGetDto> response = organizationController.getById(organizationGetDto.id());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(organizationGetDto, response.getBody());
        verify(organizationServiceI, times(1)).getById(organizationGetDto.id());
    }

    @Test
    @DisplayName("Test if OrganizationNotFoundException is thrown when organization is not found")
    void getOrganizationByIdNotFound(){

        when(organizationServiceI.getById(organizationGetDto.id())).thenThrow(new OrganizationNotFoundException("Org not found"));

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class, () -> organizationController.getById(organizationGetDto.id()));

        assertEquals("Org not found", ex.getMessage());
        verify(organizationServiceI, times(1)).getById(organizationGetDto.id());
    }

    @Test
    @DisplayName("Test if add new organization works correctly")
    void createOrganizationAndReturnGetDto() {

        when(organizationServiceI.create(organizationCreateDto)).thenReturn(organizationGetDto);

        ResponseEntity<OrganizationGetDto> response = organizationController.create(organizationCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(organizationGetDto, response.getBody());
        verify(organizationServiceI, times(1)).create(organizationCreateDto);
    }


    @Test
    @DisplayName("Test if update organization works correctly")
    void updateOrganizationAndReturnGetDto() {

        when(organizationServiceI.update(organizationGetDto.id(), organizationUpdateDto)).thenReturn(updatedOrganizationGetDto);

        ResponseEntity<OrganizationGetDto> response = organizationController.update(organizationGetDto.id(), organizationUpdateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedOrganizationGetDto.email(), organizationUpdateDto.email());
        verify(organizationServiceI, times(1)).update(organizationGetDto.id(), organizationUpdateDto);
    }

    @Test
    @DisplayName("Test if OrganizationNotFoundException is thrown when try to update a organization that does not exist")
    void updateOrganizationNotFound() {

        when(organizationServiceI.update(organizationGetDto.id(), organizationUpdateDto)).thenThrow(new OrganizationNotFoundException("Org not found"));

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class, () -> organizationController.update(organizationGetDto.id(), organizationUpdateDto));

        assertEquals("Org not found", ex.getMessage());
        verify(organizationServiceI, times(1)).update(organizationGetDto.id(), organizationUpdateDto);
    }


    @Test
    @DisplayName("Test if delete organization works correctly")
    void deleteOrganizationAndReturnString() {

        when(organizationServiceI.delete(organizationGetDto.id())).thenReturn(format(ORG_DELETE_MESSAGE, organizationGetDto.id()));

        ResponseEntity<String> response = organizationController.delete(organizationGetDto.id());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(format(ORG_DELETE_MESSAGE, organizationGetDto.id()), response.getBody());
        verify(organizationServiceI, times(1)).delete(organizationGetDto.id());
    }

    @Test
    @DisplayName("Test if OrganizationNotFoundException is thrown when try to delete a organization that does not exist")
    void deleteOrganizationNotFound() {

        when(organizationServiceI.delete(organizationGetDto.id())).thenThrow(new OrganizationNotFoundException("Org not found"));

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class, () -> organizationController.delete(organizationGetDto.id()));

        assertEquals("Org not found", ex.getMessage());
        verify(organizationServiceI, times(1)).delete(organizationGetDto.id());
    }
}
