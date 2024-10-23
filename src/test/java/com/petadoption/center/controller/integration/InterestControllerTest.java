package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.enums.Status;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.petadoption.center.testUtils.ConstantsURL.*;
import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.createAttributes;
import static com.petadoption.center.util.Messages.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class InterestControllerTest extends AbstractIntegrationTest {

    private InterestCreateDto interestCreateDto;
    private InterestUpdateDto interestUpdateDtoRejected;
    private InterestUpdateDto interestUpdateDtoFormRequested;
    private InterestGetDto expectedInterestGetDto;

    private String UNIQUE_USER_PET_ORG_CONSTRAINT;

    private String invalidInterestId;
    private String invalidOrgId;
    private String invalidUserId;
    private String orgId;
    private String userId;

    @BeforeAll
    void setUp() throws Exception {
        UNIQUE_USER_PET_ORG_CONSTRAINT = "uniqueuserandpetandorganization";

        SpeciesGetDto speciesGetDto = persistSpecies(speciesCreateDto());
        BreedGetDto primaryBreedGetDto = persistBreed(primaryBreedCreateDto(speciesGetDto.id()));
        ColorGetDto primaryColorGetDto = persistColor(primaryColorCreateDto());

        OrganizationGetDto organizationGetDto = persistOrganization(organizationCreateDto());
        orgId = organizationGetDto.id();

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
        userId = userGetDto.id();

        PetGetDto petGetDto = persistPet(petCreateDto);

        interestCreateDto = new InterestCreateDto(
                userGetDto.id(),
                petGetDto.id(),
                organizationGetDto.id()
        );

        interestUpdateDtoRejected = interestUpdateDtoToRejected();
        interestUpdateDtoFormRequested = interestUpdateDtoToFormRequested();

        expectedInterestGetDto = InterestGetDto.builder()
                .userDto(userGetDto)
                .petDto(petGetDto)
                .organizationDto(organizationGetDto)
                .status(Status.PENDING)
                .createdAt(LocalDateTime.of(2024, 1, 1, 1 ,1))
                .build();

        invalidInterestId = "1111-1111";
        invalidOrgId = "2222-2222";
        invalidUserId = "3333-3333";
    }

    @AfterEach
    void reset(){
        interestRepository.deleteAll();
        clearRedisCache();
    }

    private void persistAndUpdateInterestToRejected() throws Exception {

        InterestGetDto persistedInterest = persistInterest(interestCreateDto);

        mockMvc.perform(put(INTEREST_UPDATE_URL, persistedInterest.id())
                        .content(objectMapper.writeValueAsString(interestUpdateDtoRejected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    private Stream<Arguments> provideCreateDTOsWithInvalidProps() {
        return Stream.of(
                Arguments.of(interestCreateDto.toBuilder().userId(null).build(), "userId"),
                Arguments.of(interestCreateDto.toBuilder().petId(null).build(), "petId"),
                Arguments.of(interestCreateDto.toBuilder().organizationId(null).build(), "organizationId")
        );
    }

    @Test
    @DisplayName("Test get current interest by organization id returns empty")
    void testGetCurrentInterestByOrganizationIdReturnsEmpty() throws Exception {

        mockMvc.perform(get(INTEREST_GET_CURRENT_BY_ORG_ID_URL, orgId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)))
                .andReturn();
    }

    @Test
    @DisplayName("Test get current interest by organization id returns interest list with size 1")
    void testGetCurrentInterestByOrganizationIdReturnsInterestSizeOne() throws Exception {

        persistInterest(interestCreateDto);

        mockMvc.perform(get(INTEREST_GET_CURRENT_BY_ORG_ID_URL, orgId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andReturn();
    }

    @Test
    @DisplayName("Test get interest history by organization id returns empty")
    void testGetInterestHistoryByOrganizationIdReturnsEmpty() throws Exception {

        mockMvc.perform(get(INTEREST_GET_HISTORY_BY_ORG_ID_URL, orgId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)))
                .andReturn();
    }

    @Test
    @DisplayName("Test get interest history by organization id returns interest list with size 1")
    void testGetInterestHistoryByOrganizationIdReturnsInterestSizeOne() throws Exception {

        persistAndUpdateInterestToRejected();

        mockMvc.perform(get(INTEREST_GET_HISTORY_BY_ORG_ID_URL, orgId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andReturn();
    }

    @Test
    @DisplayName("Test get current interest by user id returns empty")
    void testGetCurrentInterestByUserIdReturnsEmpty() throws Exception {

        mockMvc.perform(get(INTEREST_GET_CURRENT_BY_USER_ID_URL, userId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)))
                .andReturn();
    }

    @Test
    @DisplayName("Test get current interest by user id returns interest list with size 1")
    void testGetCurrentInterestByUserIdReturnsInterestSizeOne() throws Exception {

        persistInterest(interestCreateDto);

        mockMvc.perform(get(INTEREST_GET_CURRENT_BY_USER_ID_URL, userId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andReturn();
    }

    @Test
    @DisplayName("Test get interest history by user id returns empty")
    void testGetInterestHistoryByUserIdReturnsEmpty() throws Exception {

        mockMvc.perform(get(INTEREST_GET_HISTORY_BY_USER_ID_URL, userId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)))
                .andReturn();
    }


    @Test
    @DisplayName("Test get interest history by user id returns interest list with size 1")
    void testGetInterestHistoryByUserIdReturnsInterestSizeOne() throws Exception {

        persistAndUpdateInterestToRejected();

        mockMvc.perform(get(INTEREST_GET_HISTORY_BY_USER_ID_URL, userId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andReturn();
    }

    @Test
    @DisplayName("Test get current interest by id returns interest")
    void testGetInterestByIdReturnsInterest() throws Exception {

        InterestGetDto persistedInterest = persistInterest(interestCreateDto);

        mockMvc.perform(get(INTEREST_GET_BY_ID_URL, persistedInterest.id())
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(persistedInterest.id())))
                .andReturn();
    }

    @Test
    @DisplayName("Test get current interest with invalid id throws InterestNotFoundException")
    void testGetInterestByIdReturnsInterestNotFoundException() throws Exception {

        mockMvc.perform(get(INTEREST_GET_BY_ID_URL, invalidInterestId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(format(INTEREST_NOT_FOUND, invalidInterestId)))
                .andReturn();
    }

    @Test
    @DisplayName("Test create new interest works correctly")
    void testCreateInterest() throws Exception {

        InterestGetDto persistedInterest = persistInterest(interestCreateDto);

        assertThat(persistedInterest)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(expectedInterestGetDto);

        assertNotNull(persistedInterest.createdAt());
        assertTrue(persistedInterest.id().matches("^[0-9a-fA-F-]{36}$"));
    }

    @Test
    @DisplayName("Test if create interest with repeated User/Pet/Org combination returns conflict with correct constraint and message")
    void testIfCreateInterestWithExistentUserAndPetAndOrgReturnsConflict() throws Exception {

        InterestGetDto persistedInterest = persistInterest(interestCreateDto);

        assertNotNull(persistedInterest.createdAt());
        assertTrue(persistedInterest.id().matches("^[0-9a-fA-F-]{36}$"));

        mockMvc.perform(post(INTEREST_CREATE_URL)
                        .content(objectMapper.writeValueAsString(interestCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(constraintMessageResolver.getMessage(UNIQUE_USER_PET_ORG_CONSTRAINT)))
                .andExpect(jsonPath("$.constraint").value(UNIQUE_USER_PET_ORG_CONSTRAINT));
    }

    @ParameterizedTest(name = "Test {index}: Test with invalid {1}")
    @MethodSource("provideCreateDTOsWithInvalidProps")
    @DisplayName("Test if create an Interest with invalid field throws exception and validation message")
    void testIfCreatingInterestFormWithInvalidFieldThrowsException(InterestCreateDto interestCreateDto, String invalidField) throws Exception{

        mockMvc.perform(post(INTEREST_CREATE_URL)
                        .content(objectMapper.writeValueAsString(interestCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationIssue." + invalidField, is(BLANK_FIELD)));
    }

    @Test
    @DisplayName("Test if update interest to form requested works correctly")
    void testUpdateInterestToFormRequested() throws Exception {

        InterestGetDto persistedInterest = persistInterest(interestCreateDto);

        mockMvc.perform(put(INTEREST_UPDATE_URL, persistedInterest.id())
                        .content(objectMapper.writeValueAsString(interestUpdateDtoFormRequested))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Test if update interest to rejected works correctly")
    void testUpdateInterestToRejected() throws Exception {

        InterestGetDto persistedInterest = persistInterest(interestCreateDto);

        mockMvc.perform(put(INTEREST_UPDATE_URL, persistedInterest.id())
                        .content(objectMapper.writeValueAsString(interestUpdateDtoRejected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Test if delete interest works correctly")
    void testDeleteInterest() throws Exception {

        InterestGetDto persistedInterest = persistInterest(interestCreateDto);

        mockMvc.perform(delete(INTEREST_DELETE_URL, persistedInterest.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(INTEREST_DELETE_MESSAGE, persistedInterest.id())));
    }

    @Test
    @DisplayName("Test if delete interest with invalid id throws InterestNotFoundException")
    void testDeleteInterestWithInvalidIdThrowsInterestNotFoundException() throws Exception {

        mockMvc.perform(delete(INTEREST_DELETE_URL, invalidInterestId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(format(INTEREST_NOT_FOUND, invalidInterestId)));
    }
}
