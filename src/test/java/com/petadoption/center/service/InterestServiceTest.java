package com.petadoption.center.service;

import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.enums.Status;
import com.petadoption.center.exception.not_found.*;
import com.petadoption.center.model.Interest;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.User;
import com.petadoption.center.repository.InterestRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.petadoption.center.enums.Status.*;
import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestDtoFactory.interestUpdateDtoToFormRequested;
import static com.petadoption.center.testUtils.TestEntityFactory.createInterest;
import static com.petadoption.center.util.Messages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class InterestServiceTest {

    @InjectMocks
    private InterestService interestService;

    @Mock
    private InterestRepository interestRepository;

    @Mock
    private UserService userService;

    @Mock
    private PetService petService;

    @Mock
    private OrganizationService organizationService;

    @Mock
    private AdoptionFormService adoptionFormService;

    private static Pageable pageable;
    private static String userId;
    private static Organization organization;
    private static String orgId;
    private static User user;
    private static String interestId;
    private static Interest testInterest;
    private static InterestGetDto interestGetDto;
    private static InterestGetDto interestGetDtoUpdated;
    private static InterestCreateDto interestCreateDto;
    private static InterestUpdateDto interestUpdateDtoToFormRequested;

    @BeforeAll
    static void setUp() {
        testInterest = createInterest();
        interestGetDto = interestGetDto();
        interestGetDtoUpdated = interestGetDtoUpdated();
        interestCreateDto = interestCreateDto();
        interestUpdateDtoToFormRequested = interestUpdateDtoToFormRequested();
        int page = 0;
        int size = 2;
        String sort = "created_at";
        pageable = PageRequest.of(page, size, Sort.by(sort));
    }


    @Test
    @DisplayName("Test if getting current interests by org Id with no entries returns empty list")
    void testIfGettingAllCurrentInterestsByOrgIdWorksCorrectlyWhenEmpty() {

        Page<Interest> interestPage = new PageImpl<>(List.of());
        List<Status> statusList = List.of(PENDING, FORM_REQUESTED, FORM_FILLED);

        when(interestRepository.findByOrganizationAndStatusIn(organization, statusList, pageable)).thenReturn(interestPage);

        List<InterestGetDto> result = interestService.getCurrentByOrganizationId(pageable, orgId);

        assertEquals(0, result.size());

    }

    @Test
    @DisplayName("Test if getting current interests by org Id works correctly and returns list of Interests")
    void testIfGettingAllCurrentInterestsByOrgIdWorksCorrectly() {

        Page<Interest> interestPage = new PageImpl<>(List.of(testInterest));
        List<Status> statusList = List.of(PENDING, FORM_REQUESTED, FORM_FILLED);

        when(interestRepository.findByOrganizationAndStatusIn(organization, statusList, pageable)).thenReturn(interestPage);

        List<InterestGetDto> result = interestService.getCurrentByOrganizationId(pageable, orgId);

        assertEquals(1, result.size());
        assertEquals(testInterest.getId(), result.getFirst().id());
    }

    @Test
    @DisplayName("Test if get current interests by organization id with page size 2 returns 2 entries")
    void testGetCurrentInterestByOrgIdWithPageSize2Returns2Entries(){

        Interest interest1 = new Interest();
        Interest interest2 = new Interest();
        Interest interest3 = new Interest();

        List<Interest> interestList = List.of(interest1, interest2, interest3);
        Page<Interest> interestPage = new PageImpl<>(List.of(interest1, interest2), pageable, interestList.size());
        List<Status> statusList = List.of(PENDING, FORM_REQUESTED, FORM_FILLED);

        when(interestRepository.findByOrganizationAndStatusIn(organization, statusList, pageable)).thenReturn(interestPage);

        List<InterestGetDto> result = interestService.getCurrentByOrganizationId(pageable, orgId);

        assertEquals(2, result.size());
        assertFalse(result.size() > 2);
    }

    @Test
    @DisplayName("Test if getting interest history by org Id with no entries returns empty list")
    void testIfGettingInterestHistoryByOrgIdWorksCorrectlyWhenEmpty() {

        Page<Interest> interestPage = new PageImpl<>(List.of());
        List<Status> statusList = List.of(ACCEPTED, REJECTED);

        when(interestRepository.findByOrganizationAndStatusIn(organization, statusList, pageable)).thenReturn(interestPage);

        List<InterestGetDto> result = interestService.getHistoryByOrganizationId(pageable, orgId);

        assertEquals(0, result.size());

    }

    @Test
    @DisplayName("Test if getting interest history by org Id works correctly and returns list of Interests")
    void testIfGettingInterestHistoryByOrgIdWorksCorrectly() {

        Page<Interest> interestPage = new PageImpl<>(List.of(testInterest));
        List<Status> statusList = List.of(ACCEPTED, REJECTED);

        when(interestRepository.findByOrganizationAndStatusIn(organization, statusList, pageable)).thenReturn(interestPage);

        List<InterestGetDto> result = interestService.getHistoryByOrganizationId(pageable, orgId);

        assertEquals(1, result.size());
        assertEquals(testInterest.getId(), result.getFirst().id());
    }

    @Test
    @DisplayName("Test if get interest history by organization id with page size 2 returns 2 entries")
    void testGetInterestHistoryByOrgIdWithPageSize2Returns2Entries(){

        Interest interest1 = new Interest();
        Interest interest2 = new Interest();
        Interest interest3 = new Interest();

        List<Interest> interestList = List.of(interest1, interest2, interest3);
        Page<Interest> interestPage = new PageImpl<>(List.of(interest1, interest2), pageable, interestList.size());
        List<Status> statusList = List.of(ACCEPTED, REJECTED);

        when(interestRepository.findByOrganizationAndStatusIn(organization, statusList, pageable)).thenReturn(interestPage);

        List<InterestGetDto> result = interestService.getHistoryByOrganizationId(pageable, orgId);

        assertEquals(2, result.size());
        assertFalse(result.size() > 2);
    }

    @Test
    @DisplayName("Test if getting current interests by user Id with no entries returns empty list")
    void testIfGettingAllCurrentInterestsByUserIdWorksCorrectlyWhenEmpty() {

        Page<Interest> interestPage = new PageImpl<>(List.of());
        List<Status> statusList = List.of(PENDING, FORM_REQUESTED, FORM_FILLED);

        when(interestRepository.findByUserAndStatusIn(user, statusList, pageable)).thenReturn(interestPage);

        List<InterestGetDto> result = interestService.getCurrentByUserId(pageable, userId);

        assertEquals(0, result.size());

    }

    @Test
    @DisplayName("Test if getting current interests by user Id works correctly and returns list of Interests")
    void testIfGettingAllCurrentInterestsByUserIdWorksCorrectly() {

        Page<Interest> interestPage = new PageImpl<>(List.of(testInterest));
        List<Status> statusList = List.of(PENDING, FORM_REQUESTED, FORM_FILLED);

        when(interestRepository.findByUserAndStatusIn(user, statusList, pageable)).thenReturn(interestPage);

        List<InterestGetDto> result = interestService.getCurrentByUserId(pageable, userId);

        assertEquals(1, result.size());
        assertEquals(testInterest.getId(), result.getFirst().id());
    }

    @Test
    @DisplayName("Test if get current interests by user id with page size 2 returns 2 entries")
    void testGetCurrentInterestByUserIdWithPageSize2Returns2Entries(){

        Interest interest1 = new Interest();
        Interest interest2 = new Interest();
        Interest interest3 = new Interest();

        List<Interest> interestList = List.of(interest1, interest2, interest3);
        Page<Interest> interestPage = new PageImpl<>(List.of(interest1, interest2), pageable, interestList.size());
        List<Status> statusList = List.of(PENDING, FORM_REQUESTED, FORM_FILLED);

        when(interestRepository.findByUserAndStatusIn(user, statusList, pageable)).thenReturn(interestPage);

        List<InterestGetDto> result = interestService.getCurrentByUserId(pageable, userId);

        assertEquals(2, result.size());
        assertFalse(result.size() > 2);
    }

    @Test
    @DisplayName("Test if getting interest history by user Id with no entries returns empty list")
    void testIfGettingInterestHistoryByUserIdWorksCorrectlyWhenEmpty() {

        Page<Interest> interestPage = new PageImpl<>(List.of());
        List<Status> statusList = List.of(ACCEPTED, REJECTED);

        when(interestRepository.findByUserAndStatusIn(user, statusList, pageable)).thenReturn(interestPage);

        List<InterestGetDto> result = interestService.getHistoryByUserId(pageable, userId);

        assertEquals(0, result.size());

    }

    @Test
    @DisplayName("Test if getting interest history by user Id works correctly and returns list of Interests")
    void testIfGettingInterestHistoryByUserIdWorksCorrectly() {

        Page<Interest> interestPage = new PageImpl<>(List.of(testInterest));
        List<Status> statusList = List.of(ACCEPTED, REJECTED);

        when(interestRepository.findByUserAndStatusIn(user, statusList, pageable)).thenReturn(interestPage);

        List<InterestGetDto> result = interestService.getHistoryByUserId(pageable, userId);

        assertEquals(1, result.size());
        assertEquals(testInterest.getId(), result.getFirst().id());
    }

    @Test
    @DisplayName("Test if get interest history by user id with page size 2 returns 2 entries")
    void testGetInterestHistoryByUserIdWithPageSize2Returns2Entries(){

        Interest interest1 = new Interest();
        Interest interest2 = new Interest();
        Interest interest3 = new Interest();

        List<Interest> interestList = List.of(interest1, interest2, interest3);
        Page<Interest> interestPage = new PageImpl<>(List.of(interest1, interest2), pageable, interestList.size());
        List<Status> statusList = List.of(ACCEPTED, REJECTED);

        when(interestRepository.findByUserAndStatusIn(user, statusList, pageable)).thenReturn(interestPage);

        List<InterestGetDto> result = interestService.getHistoryByUserId(pageable, userId);

        assertEquals(2, result.size());
        assertFalse(result.size() > 2);
    }

    @Test
    @DisplayName("Test if get current Interests by non-existent organization id throws OrganizationNotFoundException")
    void testGetCurrentInterestByInvalidOrgIdThrowsException() {

        when(organizationService.getById(orgId)).thenThrow(OrganizationNotFoundException.class);

        assertThrows(OrganizationNotFoundException.class, () -> interestService.getCurrentByOrganizationId(pageable, orgId));
    }

    @Test
    @DisplayName("Test if get Interest history by non-existent organization id throws OrganizationNotFoundException")
    void testGetInterestHistoryByInvalidOrgIdThrowsException() {

        when(organizationService.getById(orgId)).thenThrow(OrganizationNotFoundException.class);

        assertThrows(OrganizationNotFoundException.class, () -> interestService.getHistoryByOrganizationId(pageable, orgId));
    }

    @Test
    @DisplayName("Test if get current Interests by non-existent user id throws UserNotFoundException")
    void testGetCurrentInterestByInvalidUserIdThrowsException() {

        when(userService.getById(userId)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> interestService.getCurrentByUserId(pageable, userId));
    }

    @Test
    @DisplayName("Test if get Interest history by non-existent user id throws UserNotFoundException")
    void testGetInterestHistoryByInvalidUserIdThrowsException() {

        when(userService.getById(userId)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> interestService.getHistoryByUserId(pageable, userId));
    }

    @Test
    @DisplayName("Test if getting an interest by id works correctly and returns an InterestGetDto")
    void testGetInterestById() {
        when(interestRepository.findById(testInterest.getId())).thenReturn(Optional.of(testInterest));

        InterestGetDto result = interestService.getById(testInterest.getId());

        assertEquals(testInterest.getId(), result.id());
    }

    @Test
    @DisplayName("Test if getting an interest by invalid id returns an InterestNotFoundException")
    void testGetInterestByIdThrowsInterestNotFoundException() {

        when(interestRepository.findById(testInterest.getId())).thenReturn(Optional.empty());

        assertThrows(InterestNotFoundException.class, () -> interestService.getById(testInterest.getId()));
    }

    @Test
    @DisplayName("Test if create an Interest works correctly and returns the InterestGetDto")
    void testCreateInterestWorksAndReturnsAdoptionFormGetDto() {

        when(interestRepository.save(any(Interest.class))).thenReturn(testInterest);

        InterestGetDto result = interestService.create(interestCreateDto);

        assertEquals(interestGetDto, result);
    }

    @Test
    @DisplayName("Test if create an Interest with non-existent user id throws UserNotFoundException")
    void testCreateInterestByInvalidUserIdThrowsException() {

        when(userService.getById(anyString())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> interestService.create(interestCreateDto));
        verify(interestRepository, never()).save(any(Interest.class));
    }

    @Test
    @DisplayName("Test if create an Interest with non-existent pet id throws PetNotFoundException")
    void testCreateInterestByInvalidPetIdThrowsException() {

        when(petService.getById(anyString())).thenThrow(PetNotFoundException.class);

        assertThrows(PetNotFoundException.class, () -> interestService.create(interestCreateDto));
        verify(interestRepository, never()).save(any(Interest.class));
    }

    @Test
    @DisplayName("Test if create an Interest with non-existent organization id throws OrganizationNotFoundException")
    void testCreateInterestByInvalidOrgIdThrowsException() {

        when(organizationService.getById(anyString())).thenThrow(OrganizationNotFoundException.class);

        assertThrows(OrganizationNotFoundException.class, () -> interestService.create(interestCreateDto));
        verify(interestRepository, never()).save(any(Interest.class));
    }

    @Test
    @DisplayName("Test if update Interest saves all fields and returns AdoptionFormGetDto")
    void testUpdateInterestFormShouldSaveAllFieldsAndReturnGetDto() {

        Interest updatedInterest = createInterest();
        updatedInterest.setStatus(FORM_REQUESTED);

        when(interestRepository.findById(testInterest.getId())).thenReturn(Optional.of(testInterest));
        when(interestRepository.save(any(Interest.class))).thenReturn(updatedInterest);
        InterestGetDto result = interestService.update(testInterest.getId(), interestUpdateDtoToFormRequested);

        assertNotNull(interestUpdateDtoToFormRequested);
        assertEquals(interestUpdateDtoToFormRequested.status(), result.status().toString());
    }

    @Test
    @DisplayName("Test if update an Interest with non-existent id throws InterestNotFoundException")
    void testCreateInterestByInvalidInterestIdThrowsException() {

        when(interestRepository.findById(interestId)).thenThrow(InterestNotFoundException.class);

        assertThrows(InterestNotFoundException.class, () -> interestService.update(interestId, interestUpdateDtoToFormRequested));
        verify(interestRepository, never()).save(any(Interest.class));
    }

    @Test
    @DisplayName("Test if delete Interest works correctly and returns message")
    void testDeleteInterestAndReturnConfirmationMessage() {

        when(interestRepository.findById(testInterest.getId())).thenReturn(Optional.of(testInterest));

        assertEquals(interestService.delete(testInterest.getId()), INTEREST_WITH_ID + testInterest.getId() + DELETE_SUCCESS);
    }

    @Test
    @DisplayName("Test if delete Interest throws InterestNotFoundException exception when there is no Interest")
    void testDeleteInterestFormThrowsNotFoundException(){

        when(interestRepository.findById(interestId)).thenReturn(Optional.empty());

        assertThrows(InterestNotFoundException.class, () -> interestService.delete(interestId));
    }
}
