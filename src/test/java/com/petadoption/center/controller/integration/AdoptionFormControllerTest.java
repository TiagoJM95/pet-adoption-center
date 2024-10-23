package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.user.UserGetDto;

import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;

import java.util.Optional;

import static com.petadoption.center.testUtils.ConstantsURL.*;
import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.*;
import static com.petadoption.center.util.Messages.ADOPTION_FORM_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdoptionFormControllerTest extends AbstractIntegrationTest {

    private static AdoptionFormCreateDto adoptionFormCreateDto;
    private static AdoptionFormUpdateDto adoptionFormUpdateDto;
    private static AdoptionFormGetDto expectedAdoptionFormGetDto;

    private SpeciesGetDto speciesGetDto;
    private BreedGetDto primaryBreedGetDto;
    private ColorGetDto primaryColorGetDto;
    private OrganizationGetDto organizationGetDto;
    private PetCreateDto petCreateDto;
    private UserGetDto userGetDto;
    private PetGetDto petGetDto;

    @BeforeAll
    void setUp() throws Exception {
        speciesGetDto = persistSpecies(speciesCreateDto());
        primaryBreedGetDto = persistBreed(primaryBreedCreateDto(speciesGetDto.id()));
        primaryColorGetDto = persistColor(primaryColorCreateDto());
        organizationGetDto = persistOrganization(organizationCreateDto());

        petCreateDto = PetCreateDto.builder()
                .name("Max")
                .speciesId(speciesGetDto.id())
                .primaryBreedId(primaryBreedGetDto.id())
                .primaryColor(primaryColorGetDto.id())
                .gender("Male")
                .coat("Medium")
                .age("Adult")
                .size("Large")
                .description("Max is friendly dog!")
                .imageUrl("https://dog.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationId(organizationGetDto.id())
                .build();

        userGetDto = persistUser(userCreateDto());
        petGetDto = persistPet(petCreateDto);

        adoptionFormCreateDto = AdoptionFormCreateDto.builder()
                .userId(userGetDto.id())
                .petId(petGetDto.id())
                .userFamily(createFamily())
                .petVacationHome("Pet Hotel")
                .isResponsibleForPet(true)
                .otherNotes("Notes")
                .petAddress(createAddress())
                .build();

        adoptionFormUpdateDto = AdoptionFormUpdateDto.builder()
                .userFamily(createFamily())
                .petVacationHome("Neighbour")
                .isResponsibleForPet(true)
                .otherNotes("Notes")
                .petAddress(createAddress())
                .build();

        expectedAdoptionFormGetDto = AdoptionFormGetDto.builder()
                .user(userGetDto)
                .pet(petGetDto)
                .userFamily(createFamily())
                .petVacationHome("Pet Hotel")
                .isResponsibleForPet(true)
                .otherNotes("Notes")
                .petAddress(createAddress())
                .build();
    }

    @AfterEach
    void cleanTable(){
        helper.cleanAll();
        clearRedisCache();
    }

    @Test
    @DisplayName("Test get all adoption forms when empty returns empty")
    void testGetAllEmptyAdoptionFormsReturnsEmpty() throws Exception {

        mockMvc.perform(get(ADOPTION_FORM_GET_ALL_OR_CREATE_URL)
                .param("page", "0")
                .param("size", "5")
                .param("sort", "id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)))
                .andReturn();

    }

    @Test
    @DisplayName("Test get all adoption with 1 element returns 1 element")
    void testGetAllReturnsOne() throws Exception {

        AdoptionFormGetDto persistedAdoptionForm = persistAdoptionForm(adoptionFormCreateDto);

        mockMvc.perform(get(ADOPTION_FORM_GET_ALL_OR_CREATE_URL)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(persistedAdoptionForm.id())))
                .andExpect(jsonPath("$[0].user.id", is(persistedAdoptionForm.user().id())))
                .andExpect(jsonPath("$[0].pet.id", is(persistedAdoptionForm.pet().id())))
                .andReturn();
    }

    @Test
    @DisplayName("Test if create adoption form works correctly")
    void testCreateAdoptionForm() throws Exception {

        AdoptionFormGetDto persistedAdoptionForm = persistAdoptionForm(adoptionFormCreateDto);

        assertThat(persistedAdoptionForm)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(expectedAdoptionFormGetDto);

        assertNotNull(persistedAdoptionForm.createdAt());
        assertTrue(persistedAdoptionForm.id().matches("^[0-9a-fA-F-]{36}$"));
    }

    @Test
    @DisplayName("Test if update an adoption form works correctly")
    void testUpdate() throws Exception {

        AdoptionFormGetDto adoptionFormGetDto = persistAdoptionForm(adoptionFormCreateDto);
        String adoptionFormId = adoptionFormGetDto.id();

        mockMvc.perform(put(ADOPTION_FORM_UPDATE_URL, adoptionFormId)
                        .content(objectMapper.writeValueAsString(adoptionFormUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petVacationHome", is(adoptionFormUpdateDto.petVacationHome())))
                .andExpect(jsonPath("$.isResponsibleForPet", is(adoptionFormUpdateDto.isResponsibleForPet())))
                .andExpect(jsonPath("$.otherNotes", is(adoptionFormUpdateDto.otherNotes())));
    }

    @Test
    @DisplayName("Test if delete an adoption form works correctly")
    void testDelete() throws Exception {

        AdoptionFormGetDto adoptionFormGetDto = persistAdoptionForm(adoptionFormCreateDto);
        String adoptionFormId = adoptionFormGetDto.id();

        mockMvc.perform(delete(ADOPTION_FORM_DELETE_URL, adoptionFormId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(ADOPTION_FORM_DELETE_MESSAGE, adoptionFormId)));

        assertEquals(Optional.empty(), adoptionFormRepository.findById(adoptionFormId));

    }
}
