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
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static com.petadoption.center.testUtils.ConstantsURL.*;
import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.*;
import static com.petadoption.center.util.Messages.*;
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

    private String invalidAdoptionFormId;

    private String UNIQUE_USER_PET_CONSTRAINT;

    @BeforeAll
    void setUp() throws Exception {
        UNIQUE_USER_PET_CONSTRAINT = "uniqueuserandpet";

        SpeciesGetDto speciesGetDto = persistSpecies(speciesCreateDto());
        BreedGetDto primaryBreedGetDto = persistBreed(primaryBreedCreateDto(speciesGetDto.id()));
        ColorGetDto primaryColorGetDto = persistColor(primaryColorCreateDto());
        OrganizationGetDto organizationGetDto = persistOrganization(organizationCreateDto());

        PetCreateDto petCreateDto = PetCreateDto.builder()
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

        UserGetDto userGetDto = persistUser(userCreateDto());
        PetGetDto petGetDto = persistPet(petCreateDto);

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

        invalidAdoptionFormId = "1111-1111";
    }

    @AfterEach
    void cleanTable(){
       adoptionFormRepository.deleteAll();
       clearRedisCache();
    }

    @Test
    @DisplayName("Test if get all AdoptionForms when repository is empty returns empty list")
    void testIfGetAllEmptyAdoptionFormsReturnsEmptyList() throws Exception {

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
    @DisplayName("Test if getting all AdoptionForms with 1 element in repository returns 1 element list")
    void testIfGetAllAdoptionFormWhenRepositorsHasOneElementReturnsListOfSizeOne() throws Exception {

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
    @DisplayName("Test if getting an adoption form with a valid id returns the correct entry")
    void testIfGetAdoptionFormWithValidIdReturnsEntry() throws Exception {

        AdoptionFormGetDto persistedAdoptionForm = persistAdoptionForm(adoptionFormCreateDto);

        MvcResult result = mockMvc.perform(get(ADOPTION_FORM_GET_BY_ID_URL, persistedAdoptionForm.id())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        AdoptionFormGetDto retrievedAdoptionForm = objectMapper.readValue(result.getResponse().getContentAsString(), AdoptionFormGetDto.class);

        assertThat(persistedAdoptionForm)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(retrievedAdoptionForm);
    }

    @Test
    @DisplayName("Test if getting an adoption form with an invalid id returns an AdoptionNotFoundException with message")
    void testIfGetAdoptionFormWithInvalidIdReturnsExceptionMessage() throws Exception {

        mockMvc.perform(get(ADOPTION_FORM_GET_BY_ID_URL, invalidAdoptionFormId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(format(ADOPTION_FORM_NOT_FOUND, invalidAdoptionFormId)));
    }

    @Test
    @DisplayName("Test if create adoption persists an AdoptionForm and returns the correct AdoptionFormGetDto")
    void testIfCreateAdoptionFormPersistsAndReturnsDto() throws Exception {

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
    @DisplayName("Test if create adoption with repeated User/Pet combination returns conflict with correct constraint and message")
    void testIfCreateAdoptionWithExistentUserAndPetReturnsConflict() throws Exception {

        AdoptionFormGetDto persistedAdoptionForm = persistAdoptionForm(adoptionFormCreateDto);

        assertNotNull(persistedAdoptionForm.createdAt());
        assertTrue(persistedAdoptionForm.id().matches("^[0-9a-fA-F-]{36}$"));

        mockMvc.perform(post(ADOPTION_FORM_GET_ALL_OR_CREATE_URL)
                        .content(objectMapper.writeValueAsString(adoptionFormCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(constraintMessageResolver.getMessage(UNIQUE_USER_PET_CONSTRAINT)))
                .andExpect(jsonPath("$.constraint").value(UNIQUE_USER_PET_CONSTRAINT));
    }

    @Test
    @DisplayName("Test if update an AdoptionForm correctly updates the fields with an AdoptionFormUpdateDto")
    void testIfUpdateAdoptionFormUpdatesFieldsWithSentDto() throws Exception {

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
    @DisplayName("Test if delete an AdoptionForm correctly removes the entry from the repository")
    void testIfDeleteAdoptionFormRemovesEntryFromRepository() throws Exception {

        AdoptionFormGetDto adoptionFormGetDto = persistAdoptionForm(adoptionFormCreateDto);
        String adoptionFormId = adoptionFormGetDto.id();

        mockMvc.perform(delete(ADOPTION_FORM_DELETE_URL, adoptionFormId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(ADOPTION_FORM_DELETE_MESSAGE, adoptionFormId)));

        assertEquals(Optional.empty(), adoptionFormRepository.findById(adoptionFormId));

    }

    @Test
    @DisplayName("Test if deleting an AdoptionForm with an invalid id returns an AdoptionNotFoundException with message")
    void testIfDeleteAdoptionFormWithInvalidIdReturnsExceptionMessage() throws Exception {

        mockMvc.perform(delete(ADOPTION_FORM_DELETE_URL, invalidAdoptionFormId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(format(ADOPTION_FORM_NOT_FOUND, invalidAdoptionFormId)));
    }
}
