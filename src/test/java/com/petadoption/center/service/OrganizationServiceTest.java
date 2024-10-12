package com.petadoption.center.service;

import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;
import com.petadoption.center.model.Organization;
import com.petadoption.center.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.petadoption.center.testUtils.TestDtoFactory.orgUpdateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.organizationCreateDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createOrganization;
import static com.petadoption.center.util.Messages.ORG_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class OrganizationServiceTest {

    @InjectMocks
    private OrganizationService organizationService;

    @Mock
    private OrganizationRepository organizationRepository;

    private static Organization testOrganization;
    private static Organization org1;
    private static Organization org2;
    private static Organization org3;
    private static Organization updatedOrganization;
    private static OrganizationCreateDto organizationCreateDto;
    private static OrganizationUpdateDto organizationUpdateDto;

    static List<Organization> organizationList = List.of();

    private static Pageable pageable;

    @BeforeAll
    static void setUp() {
        testOrganization = createOrganization();
        updatedOrganization = createOrganization();
        updatedOrganization.setEmail("email@email.com");
        organizationCreateDto = organizationCreateDto();
        organizationUpdateDto = orgUpdateDto();

        org1 = createOrganization();
        org2 = createOrganization();
        org3 = createOrganization();
        organizationList = List.of(org1, org2, org3);

        int page = 0;
        int size = 10;
        String sort = "created_at";
        pageable = PageRequest.of(page, size, Sort.by(sort));
    }

    @Test
    @DisplayName("Test if get all organization works correctly")
    void getAllOrganizations() {

        Page<Organization> pagedOrganizations = new PageImpl<>(List.of(testOrganization, updatedOrganization), pageable, 2);

        when(organizationRepository.findAll(pageable)).thenReturn(pagedOrganizations);

        List<OrganizationGetDto> result = organizationService.getAll(pageable);

        assertEquals(2, result.size());
        assertEquals(testOrganization.getEmail(), result.get(0).email());
        assertEquals(updatedOrganization.getEmail(), result.get(1).email());
    }

    @Test
    @DisplayName("Test if get all organization with page size 2 returns 2 organizations")
    void getAllOrganizationsWithPageSize2() {

        Page<Organization> pagedOrganizations = new PageImpl<>(List.of(org1, org2), pageable, organizationList.size());

        when(organizationRepository.findAll(pageable)).thenReturn(pagedOrganizations);

        List<OrganizationGetDto> result = organizationService.getAll(pageable);

        assertEquals(2, result.size());
        assertFalse(result.size() > 2);
    }

    @Test
    @DisplayName("Test if get all organizations return in ascending order")
    void getAllOrganizationsReturnInAscendingOrder() {

        Page<Organization> pagedOrganizations = new PageImpl<>(organizationList, pageable, organizationList.size());

        when(organizationRepository.findAll(pageable)).thenReturn(pagedOrganizations);

        List<OrganizationGetDto> result = organizationService.getAll(pageable);

        assertEquals(result.get(0).email(), org1.getEmail());
        assertEquals(result.get(1).email(), org2.getEmail());
        assertEquals(result.get(2).email(), org3.getEmail());
    }

    @Test
    @DisplayName("Test if get all organizations return in descending order")
    void getAllOrganizationsReturnInDescendingOrder() {

        List<Organization> organizationList = List.of(org3, org2, org1);
        Page<Organization> pagedOrganizations = new PageImpl<>(organizationList, pageable, organizationList.size());

        when(organizationRepository.findAll(pageable)).thenReturn(pagedOrganizations);

        List<OrganizationGetDto> result = organizationService.getAll(pageable);

        assertEquals(result.get(0).email(), org3.getEmail());
        assertEquals(result.get(1).email(), org2.getEmail());
        assertEquals(result.get(2).email(), org1.getEmail());
    }

    @Test
    @DisplayName("Test if get all organization return empty list if no organizations in database")
    void getAllOrganizationsEmptyList() {

        Page<Organization> pagedOrganizations = new PageImpl<>(List.of());

        when(organizationRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of()));

        List<OrganizationGetDto> result = organizationService.getAll(pageable);

        assertEquals(0, result.size());

    }

    @Test
    @DisplayName("Test if get organization by id works correctly")
    void getOrganizationById() {

        when(organizationRepository.findById(testOrganization.getId())).thenReturn(Optional.of(testOrganization));

        OrganizationGetDto result = organizationService.getById(testOrganization.getId());

        assertEquals(testOrganization.getEmail(), result.email());
    }

    @Test
    @DisplayName("Test if get organization by id throws exception if organization not exists")
    void getOrganizationByIdThrowsException() {

        when(organizationRepository.findById(testOrganization.getId())).thenReturn(Optional.empty());

        assertThrows(OrganizationNotFoundException.class, () -> organizationService.getById(testOrganization.getId()));

    }

    @Test
    @DisplayName("Test if create organization works correctly")
    void createOrganizationAndReturnGetDto() {

        when(organizationRepository.save(any(Organization.class))).thenReturn(testOrganization);

        OrganizationGetDto result = organizationService.create(organizationCreateDto);

        assertNotNull(organizationCreateDto);
        assertNotNull(result);
        assertEquals(testOrganization.getEmail(), result.email());
    }

    @Test
    @DisplayName("Test if create organization throws exception if organization already exists")
    void createOrganizationThrowsDataIntegrityException() {

        when(organizationRepository.save(any(Organization.class))).thenThrow(new DataIntegrityViolationException("Unique constraint"));

        assertThrows(DataIntegrityViolationException.class, () -> organizationService.create(organizationCreateDto));

        verify(organizationRepository, times(1)).save(any(Organization.class));

    }


    @Test
    @DisplayName("Test if update organization works correctly")
    void updateOrganizationAndReturnGetDto() {

        when(organizationRepository.findById(testOrganization.getId())).thenReturn(Optional.of(testOrganization));

        when(organizationRepository.save(any(Organization.class))).thenReturn(updatedOrganization);

        OrganizationGetDto result = organizationService.update(testOrganization.getId(), organizationUpdateDto);



        assertNotNull(result);
        assertEquals(updatedOrganization.getEmail(), result.email());
    }

    @Test
    @DisplayName("Test if update organization throws exception if organization not exists")
    void updateOrganizationThrowsException() {

        when(organizationRepository.findById(testOrganization.getId())).thenReturn(Optional.empty());

        assertThrows(OrganizationNotFoundException.class, () -> organizationService.update(testOrganization.getId(), organizationUpdateDto));
    }

    @Test
    @DisplayName("Test if delete organization works correctly")
    void deleteOrganizationReturnMessage() {

        when(organizationRepository.findById(testOrganization.getId())).thenReturn(Optional.of(testOrganization));

        String result = organizationService.delete(testOrganization.getId());

        assertNotNull(result);
        assertEquals(format(ORG_DELETE_MESSAGE, testOrganization.getId()), result);
    }

    @Test
    @DisplayName("Test if delete organization throws exception if organization not exists")
    void deleteOrganizationThrowsException() {

        when(organizationRepository.findById(testOrganization.getId())).thenReturn(Optional.empty());

        assertThrows(OrganizationNotFoundException.class, () -> organizationService.delete(testOrganization.getId()));
    }
}
