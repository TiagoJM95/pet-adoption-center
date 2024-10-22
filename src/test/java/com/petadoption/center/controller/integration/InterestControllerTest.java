package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.createAttributes;
import static com.petadoption.center.util.Messages.INTEREST_DELETE_MESSAGE;
import static com.petadoption.center.util.Messages.INTEREST_NOT_FOUND;
import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class InterestControllerTest extends AbstractIntegrationTest {

    private InterestGetDto interestGetDto;
    private InterestCreateDto interestCreateDto;
    private InterestUpdateDto interestUpdateDtoRejected;
    private InterestUpdateDto interestUpdateDtoFormRequested;
    private UserGetDto userGetDto;
    private PetGetDto petGetDto;
    private OrganizationGetDto organizationGetDto;
    private String userId;
    private String petId;
    private String orgId;
    private String speciesId;
    private String breedId;
    private String colorId;
    private String interestId;
    private String URL = "/api/v1";

    @BeforeEach
    void setUp() throws Exception {
        speciesId = helper.persistTestSpecies();
        breedId = helper.persistTestPrimaryBreed();
        colorId = helper.persistTestPrimaryColor();
        orgId = helper.persistTestOrg();

        addUser();
        addPet();

        interestCreateDto = new InterestCreateDto(
                userId,
                petId,
                orgId
        );

        interestUpdateDtoRejected = interestUpdateDtoToRejected();
        interestUpdateDtoFormRequested = interestUpdateDtoToFormRequested();
    }

    @AfterEach
    void cleanTable() {
        helper.cleanAll();
    }

    private void addUser() throws Exception {
        UserCreateDto userCreateDto = userCreateDto();

        MvcResult result = mockMvc.perform(post(URL + "/user/")
                        .content(objectMapper.writeValueAsString(userCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        userGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto.class);

        userId = userGetDto.id();
    }

    private void addPet() throws Exception {
        PetCreateDto petCreateDto = PetCreateDto.builder()
                .name("Bobi")
                .speciesId(speciesId)
                .primaryBreedId(breedId)
                .primaryColor(colorId)
                .gender("MALE")
                .coat("HAIRLESS")
                .size("SMALL")
                .age("BABY")
                .description("Description")
                .imageUrl("http://aeer.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationId(orgId)
                .build();

        MvcResult result = mockMvc.perform(post(URL + "/pet/addSingle")
                        .content(objectMapper.writeValueAsString(petCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        petGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), PetGetDto.class);

        petId = petGetDto.id();
    }

    private void createInterest() throws Exception {

        MvcResult result = mockMvc.perform(post(URL + "/interest/")
                        .content(objectMapper.writeValueAsString(interestCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        InterestGetDto interestGetDtoCreated = objectMapper.readValue(result.getResponse().getContentAsString(), InterestGetDto.class);

        interestId = interestGetDtoCreated.id();

        interestGetDto = new InterestGetDto(
                interestId,
                userGetDto,
                petGetDto,
                organizationGetDto,
                interestGetDtoCreated.status(),
                interestGetDtoCreated.timestamp(),
                interestGetDtoCreated.reviewTimestamp()
        );
    }

    private void updateInterestToRejected() throws Exception {

        createInterest();

        mockMvc.perform(put(URL + "/interest/update/{id}", interestId)
                        .content(objectMapper.writeValueAsString(interestUpdateDtoRejected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Test get current interest by organization id returns empty")
    void testGetCurrentInterestByOrganizationIdReturnsEmpty() throws Exception {

        mockMvc.perform(get(URL + "/interest/organization/{organizationId}/current", orgId)
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

        createInterest();

        mockMvc.perform(get(URL + "/interest/organization/{organizationId}/current", orgId)
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

        mockMvc.perform(get(URL + "/interest/organization/{organizationId}/history", orgId)
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

        updateInterestToRejected();

        mockMvc.perform(get(URL + "/interest/organization/{orgId}/history", orgId)
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

        mockMvc.perform(get(URL + "/interest/user/{userId}/current", userId)
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

        createInterest();

        mockMvc.perform(get(URL + "/interest/user/{userId}/current", userId)
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

        mockMvc.perform(get(URL + "/interest/user/{userId}/current", userId)
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

        updateInterestToRejected();

        mockMvc.perform(get(URL + "/interest/user/{userId}/history", userId)
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

        createInterest();

        mockMvc.perform(get(URL + "/interest/id/{id}", interestId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(interestGetDto.id())))
                .andReturn();
    }

    @Test
    @DisplayName("Test get current interest with invalid id throws InterestNotFoundException")
    void testGetInterestByIdReturnsInterestNotFoundException() throws Exception {

        String invalidId = "123123-123123";

        mockMvc.perform(get(URL + "/interest/id/{id}", invalidId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(format(INTEREST_NOT_FOUND, invalidId)))
                .andReturn();
    }

    @Test
    @DisplayName("Test create new interest works correctly")
    void testCreateInterest() throws Exception {

        MvcResult result = mockMvc.perform(post(URL + "/interest/")
                        .content(objectMapper.writeValueAsString(interestCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        InterestGetDto interestGetDtoCreated = objectMapper.readValue(result.getResponse().getContentAsString(), InterestGetDto.class);

        interestId = interestGetDtoCreated.id();

        interestGetDto = new InterestGetDto(
                interestId,
                userGetDto,
                petGetDto,
                organizationGetDto,
                interestGetDtoCreated.status(),
                interestGetDtoCreated.timestamp(),
                interestGetDtoCreated.reviewTimestamp()
        );
    }

    @Test
    @DisplayName("Test if update interest to form requested works correctly")
    void testUpdateInterestToFormRequested() throws Exception {

        createInterest();

        mockMvc.perform(put(URL + "/interest/update/{id}", interestId)
                        .content(objectMapper.writeValueAsString(interestUpdateDtoFormRequested))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Test if update interest to rejected works correctly")
    void testUpdateInterestToRejected() throws Exception {

        createInterest();

        mockMvc.perform(put(URL + "/interest/update/{id}", interestId)
                        .content(objectMapper.writeValueAsString(interestUpdateDtoRejected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Test if delete interest works correctly")
    void testDeleteInterest() throws Exception {

        createInterest();

        mockMvc.perform(delete(URL + "/interest/delete/{id}", interestGetDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(INTEREST_DELETE_MESSAGE, interestGetDto.id())));
    }

    @Test
    @DisplayName("Test if delete interest with invalid id throws InterestNotFoundException")
    void testDeleteInterestWithInvalidIdThrowsInterestNotFoundException() throws Exception {

        String invalidId = "123123-123123";

        mockMvc.perform(delete(URL + "/interest/delete/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(format(INTEREST_NOT_FOUND, invalidId)));
    }
}
