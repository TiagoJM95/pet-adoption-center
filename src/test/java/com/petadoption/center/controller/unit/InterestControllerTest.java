package com.petadoption.center.controller.unit;

import com.petadoption.center.controller.InterestController;
import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.exception.not_found.InterestNotFoundException;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;
import com.petadoption.center.exception.not_found.PetNotFoundException;
import com.petadoption.center.exception.not_found.UserNotFoundException;
import com.petadoption.center.service.interfaces.InterestServiceI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class InterestControllerTest {

    @Mock
    InterestServiceI interestService;

    @InjectMocks
    InterestController interestController;

    private static Pageable pageable;
    private static String orgId;
    private static InterestGetDto interestGetDto;
    private static InterestGetDto interestGetDtoUpdated;
    private static InterestCreateDto interestCreateDto;
    private static InterestUpdateDto interestUpdateDto;

    @BeforeAll
    static void setUp() {
       interestGetDto = interestGetDto();
       interestGetDtoUpdated = interestGetDtoUpdated();
       interestCreateDto = interestCreateDto();
       interestUpdateDto = interestUpdateDto();
    }

    @Test
    @DisplayName("Test get current interests filtering by organization")
    void testGetCurrentInterestsByOrganization() {

        List<InterestGetDto> interestGetDtoList = List.of(interestGetDto);
        when(interestService.getCurrentByOrganizationId(pageable, orgId)).thenReturn(interestGetDtoList);

        ResponseEntity<List<InterestGetDto>> response = interestController.getCurrentByOrganizationId(pageable, orgId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(interestGetDtoList, response.getBody());

        verify(interestService).getCurrentByOrganizationId(pageable, orgId);
    }

    @Test
    @DisplayName("Test trow OrganizationNotFoundException when getting a current interest with invalid organization id")
    void testThrowOrganizationNotFoundWhenGettingCurrentInterestWithInvalidOrganizationId() {

        when(interestService.getCurrentByOrganizationId(pageable, orgId)).thenThrow(new OrganizationNotFoundException("Invalid Id"));

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class, () -> interestController.getCurrentByOrganizationId(pageable, orgId));

        assertEquals("Invalid Id", ex.getMessage());
        verify(interestService, times(1)).getCurrentByOrganizationId(pageable, orgId);
    }

    @Test
    @DisplayName("Test get interest history filtering by organization")
    void testGetInterestHistoryByOrganization() {

        List<InterestGetDto> interestGetDtoList = List.of(interestGetDto);
        when(interestService.getHistoryByOrganizationId(pageable, orgId)).thenReturn(interestGetDtoList);

        ResponseEntity<List<InterestGetDto>> response = interestController.getHistoryByOrganizationId(pageable, orgId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(interestGetDtoList, response.getBody());

        verify(interestService).getHistoryByOrganizationId(pageable, orgId);
    }

    @Test
    @DisplayName("Test trow OrganizationNotFoundException when getting interest history with invalid organization id")
    void testThrowOrganizationNotFoundWhenGettingInterestHistoryWithInvalidOrganizationId() {

        when(interestService.getHistoryByOrganizationId(pageable, orgId)).thenThrow(new OrganizationNotFoundException("Invalid Id"));

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class, () -> interestController.getHistoryByOrganizationId(pageable, orgId));

        assertEquals("Invalid Id", ex.getMessage());
        verify(interestService, times(1)).getHistoryByOrganizationId(pageable, orgId);
    }

    @Test
    @DisplayName("Test get current interests filtering by user")
    void testGetCurrentInterestsByUser() {

        List<InterestGetDto> interestGetDtoList = List.of(interestGetDto);
        when(interestService.getCurrentByUserId(pageable, orgId)).thenReturn(interestGetDtoList);

        ResponseEntity<List<InterestGetDto>> response = interestController.getCurrentByUserId(pageable, orgId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(interestGetDtoList, response.getBody());

        verify(interestService).getCurrentByUserId(pageable, orgId);
    }

    @Test
    @DisplayName("Test trow OrganizationNotFoundException when getting a current interest with invalid user id")
    void testThrowOrganizationNotFoundWhenGettingCurrentInterestWithInvalidUserId() {

        when(interestService.getCurrentByUserId(pageable, orgId)).thenThrow(new OrganizationNotFoundException("Invalid Id"));

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class, () -> interestController.getCurrentByUserId(pageable, orgId));

        assertEquals("Invalid Id", ex.getMessage());
        verify(interestService, times(1)).getCurrentByUserId(pageable, orgId);
    }

    @Test
    @DisplayName("Test get interest history filtering by user")
    void testGetInterestHistoryByUser() {

        List<InterestGetDto> interestGetDtoList = List.of(interestGetDto);
        when(interestService.getHistoryByUserId(pageable, orgId)).thenReturn(interestGetDtoList);

        ResponseEntity<List<InterestGetDto>> response = interestController.getHistoryByUserId(pageable, orgId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(interestGetDtoList, response.getBody());

        verify(interestService).getHistoryByUserId(pageable, orgId);
    }

    @Test
    @DisplayName("Test trow OrganizationNotFoundException when getting interest history with invalid user id")
    void testThrowOrganizationNotFoundWhenGettingInterestHistoryWithInvalidUserId() {

        when(interestService.getHistoryByUserId(pageable, orgId)).thenThrow(new OrganizationNotFoundException("Invalid Id"));

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class, () -> interestController.getHistoryByUserId(pageable, orgId));

        assertEquals("Invalid Id", ex.getMessage());
        verify(interestService, times(1)).getHistoryByUserId(pageable, orgId);
    }

    @Test
    @DisplayName("Test get interest by Id")
    void testGetInterestById() {

        when(interestService.getById(orgId)).thenReturn(interestGetDto);

        ResponseEntity<InterestGetDto> response = interestController.getById(orgId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(interestGetDto, response.getBody());

        verify(interestService).getById(orgId);
    }

    @Test
    @DisplayName("Test throw InterestNotFound when getting interest by Id")
    void testThrowInterestNotFoundWhenGettingInterestById() {

        when(interestService.getById(orgId)).thenThrow(new InterestNotFoundException("Invalid Id"));

        InterestNotFoundException ex = assertThrows(InterestNotFoundException.class, () -> interestController.getById(orgId));

        assertEquals("Invalid Id", ex.getMessage());
        verify(interestService, times(1)).getById(orgId);
    }

    @Test
    @DisplayName("Test create Interest works correctly")
    void testCreateInterestWorksCorrectly() {

        when(interestService.create(any(InterestCreateDto.class))).thenReturn(interestGetDto);

        ResponseEntity<InterestGetDto> actual = interestController.create(interestCreateDto);

        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(interestGetDto, actual.getBody());
        verify(interestService, times(1)).create(any(InterestCreateDto.class));
    }

    @Test
    @DisplayName("Test create Interest throws UserNotFoundException when User Id is invalid")
    void testCreateInterestThrowsUserNotFound() {

        when(interestService.create(any(InterestCreateDto.class))).thenThrow(new UserNotFoundException("User not found"));

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> interestController.create(interestCreateDto));

        assertEquals("User not found", ex.getMessage());
        verify(interestService, times(1)).create(any(InterestCreateDto.class));
    }

    @Test
    @DisplayName("Test create Interest throws PetNotFoundException when Pet Id is invalid")
    void testCreateInterestThrowsPetNotFound() {

        when(interestService.create(any(InterestCreateDto.class))).thenThrow(new PetNotFoundException("Pet not found"));

        PetNotFoundException ex = assertThrows(PetNotFoundException.class, () -> interestController.create(interestCreateDto));

        assertEquals("Pet not found", ex.getMessage());
        verify(interestService, times(1)).create(any(InterestCreateDto.class));
    }

    @Test
    @DisplayName("Test create Interest throws OrganizationNotFoundException when Organization Id is invalid")
    void testCreateInterestThrowsOrganizationNotFound() {

        when(interestService.create(any(InterestCreateDto.class))).thenThrow(new OrganizationNotFoundException("Org not found"));

        OrganizationNotFoundException ex = assertThrows(OrganizationNotFoundException.class, () -> interestController.create(interestCreateDto));

        assertEquals("Org not found", ex.getMessage());
        verify(interestService, times(1)).create(any(InterestCreateDto.class));
    }

    @Test
    @DisplayName("Test if update an Interest works correctly and returns an InterestGetDto")
    void testUpdateInterestWorksCorrectly() {

        when(interestService.update(anyString(), any(InterestUpdateDto.class))).thenReturn(interestGetDtoUpdated);

        ResponseEntity<InterestGetDto> actual = interestController.update(interestGetDto.id(), interestUpdateDto);

        assertEquals(interestGetDtoUpdated, actual.getBody());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        verify(interestService, times(1)).update(anyString(), any(InterestUpdateDto.class));
    }

    @Test
    @DisplayName("Test if update an Interest throws InterestNotFoundException")
    void testUpdateInterestThrowsInterestNotFoundException() {

        when(interestService.update(orgId, interestUpdateDto)).thenThrow(new InterestNotFoundException("Invalid Id"));

        InterestNotFoundException ex = assertThrows(InterestNotFoundException.class, () -> interestController.update(orgId, interestUpdateDto));

        assertEquals("Invalid Id", ex.getMessage());
        verify(interestService, times(1)).update(orgId, interestUpdateDto);
    }

    @Test
    @DisplayName("Test if delete an Interest works correctly and returns an InterestGetDto")
    void testDeleteInterestWorksCorrectly() {

        when(interestService.delete(orgId)).thenReturn("Interest with id 1111 deleted");

        ResponseEntity<String> response = interestController.delete(orgId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Interest with id 1111 deleted", response.getBody());

        verify(interestService, times(1)).delete(orgId);
    }

    @Test
    @DisplayName("Test if delete an Interest throws InterestNotFoundException")
    void testDeleteInterestThrowsInterestNotFoundException() {

        when(interestService.delete(orgId)).thenThrow(new InterestNotFoundException("Invalid Id"));

        InterestNotFoundException ex = assertThrows(InterestNotFoundException.class, () -> interestController.delete(orgId));

        assertEquals("Invalid Id", ex.getMessage());
        verify(interestService, times(1)).delete(orgId  );
    }
}
