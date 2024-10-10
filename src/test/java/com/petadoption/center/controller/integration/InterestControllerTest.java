package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.interest.InterestUpdateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.testUtils.TestPersistenceHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.petadoption.center.testUtils.TestDtoFactory.interestUpdateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.userCreateDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createAttributes;
import static com.petadoption.center.util.Messages.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class InterestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestPersistenceHelper testPersistenceHelper;

    private InterestGetDto interestGetDto;
    private InterestCreateDto interestCreateDto;
    private InterestUpdateDto interestUpdateDto;
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
        speciesId = testPersistenceHelper.persistTestSpecies();
        breedId = testPersistenceHelper.persistTestPrimaryBreed();
        colorId = testPersistenceHelper.persistTestPrimaryColor();
        orgId = testPersistenceHelper.persistTestOrg();

        addUser();
        addPet();

        interestCreateDto = new InterestCreateDto(
                userId,
                petId,
                orgId
        );

        interestUpdateDto = interestUpdateDto();
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
                .petSpeciesId(speciesId)
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
    @DirtiesContext
    void testGetCurrentInterestByOrganizationIdReturnsInterestSizeOne() throws Exception {

        testCreateInterest();

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
    @DirtiesContext
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

    //TODO é preciso corrigir o update para mudar o status
    /*@Test
    @DisplayName("Test get interest history by organization id returns interest list with size 1")
    @DirtiesContext
    void testGetInterestHistoryByOrganizationIdReturnsInterestSizeOne() throws Exception {

        testCreateInterest();


        mockMvc.perform(get(URL + "/interest/organization/{organizationId}/history", orgId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andReturn();
    }*/

    @Test
    @DisplayName("Test get current interest by user id returns empty")
    @DirtiesContext
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
    @DirtiesContext
    void testGetCurrentInterestByUserIdReturnsInterestSizeOne() throws Exception {

        testCreateInterest();

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
    @DirtiesContext
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

    //TODO corrigir update
    /*@Test
    @DisplayName("Test get interest history by user id returns interest list with size 1")
    @DirtiesContext
    void testGetInterestHistoryByUserIdReturnsInterestSizeOne() throws Exception {

        testCreateInterest();

        mockMvc.perform(get(URL + "/interest/user/{userId}/current", userId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andReturn();
    }*/

    @Test
    @DisplayName("Test get current interest by id returns interest")
    @DirtiesContext
    void testGetInterestByIdReturnsInterest() throws Exception {

        testCreateInterest();

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
    @DirtiesContext
    void testGetInterestByIdReturnsInterestNotFoundException() throws Exception {

        String invalidId = "123123-123123";

        mockMvc.perform(get(URL + "/interest/id/{id}", invalidId)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INTEREST_WITH_ID + invalidId + NOT_FOUND))
                .andReturn();
    }

    @Test
    @DisplayName("Test create new interest works correctly")
    @DirtiesContext
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


    //TODO corrigir o stack overflow
    /*@Test
    @DisplayName("Test if update interest works correctly")
    @DirtiesContext
    void testUpdateInterest() throws Exception {

        testCreateInterest();

        MvcResult result = mockMvc.perform(put(URL + "/interest/update/{id}", interestId)
                        .content(objectMapper.writeValueAsString(interestUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }*/

    @Test
    @DisplayName("Test if delete interest works correctly")
    @DirtiesContext
    void testDeleteInterest() throws Exception {

        testCreateInterest();

        mockMvc.perform(delete(URL + "/interest/delete/{id}", interestGetDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(INTEREST_WITH_ID + interestGetDto.id() + DELETE_SUCCESS));
    }

    @Test
    @DisplayName("Test if delete interest with invalid id throws InterestNotFoundException")
    @DirtiesContext
    void testDeleteInterestWithInvalidIdThrowsInterestNotFoundException() throws Exception {

        String invalidId = "123123-123123";

        mockMvc.perform(delete(URL + "/interest/delete/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INTEREST_WITH_ID + invalidId + NOT_FOUND));
    }
}
