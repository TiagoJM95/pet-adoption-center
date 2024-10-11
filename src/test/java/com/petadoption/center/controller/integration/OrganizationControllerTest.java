package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import com.petadoption.center.repository.OrganizationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.util.stream.Stream;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.createSocialMedia;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.ORG_WITH_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrganizationRepository organizationRepository;

    private OrganizationGetDto organizationGetDto;
    private OrganizationCreateDto organizationCreateDto;
    private OrganizationUpdateDto organizationUpdateDto;


    @BeforeEach
    void setUp() {
        organizationCreateDto = organizationCreateDto();
        organizationUpdateDto = orgUpdateDto();
    }

    @AfterEach
    void tearDown() {
        organizationRepository.deleteAll();
    }

    static Stream<Arguments> orgCreateDtoProvider() {
        OrganizationCreateDto baseOrg = otherOrganizationCreateDto();

        return Stream.of(
                Arguments.of(baseOrg.toBuilder().email("org@email.com").build(), "repeated email"),
                Arguments.of(baseOrg.toBuilder().nif("123456789").build(), "repeated nif"),
                Arguments.of(baseOrg.toBuilder().phoneNumber("123456789").build(), "repeated phone number"),
                Arguments.of(baseOrg.toBuilder().address(createAddress()).build(), "repeated address"),
                Arguments.of(baseOrg.toBuilder().websiteUrl("https://www.org.com").build(), "repeated website url"),
                Arguments.of(baseOrg.toBuilder().socialMedia(createSocialMedia()).build(), "repeated social media")
        );
    }

    static Stream<Arguments> orgUpdateDtoProvider() {
        OrganizationUpdateDto baseOrg = OrganizationUpdateDto.builder()
                .name("Adopting Center")
                .email("adopting@email.com")
                .phoneNumber("987654123")
                .address(Address.builder()
                        .city("Lisboa")
                        .postalCode("1234-567")
                        .state("Lisboa")
                        .street("Rua dos Bobos, 321")
                        .build())
                .websiteUrl("https://www.adoptingcenter.com")
                .socialMedia(SocialMedia.builder()
                        .facebook("https://www.facebook.com/test")
                        .youtube("https://www.youtube.com/test")
                        .instagram("https://www.instagram.com/test")
                        .twitter("https://www.twitter.com/test")
                        .build())
                .build();

        return Stream.of(
                Arguments.of(baseOrg.toBuilder().email("org@email.com").build(), "repeated email"),
                Arguments.of(baseOrg.toBuilder().phoneNumber("123456789").build(), "repeated phone number"),
                Arguments.of(baseOrg.toBuilder().address(createAddress()).build(), "repeated address"),
                Arguments.of(baseOrg.toBuilder().websiteUrl("https://www.org.com").build(), "repeated website url"),
                Arguments.of(baseOrg.toBuilder().socialMedia(createSocialMedia()).build(), "repeated social media")
        );
    }

        private void persistOrganization() throws Exception {

        var result = mockMvc.perform(post("/api/v1/organization/")
                        .content(objectMapper.writeValueAsString(organizationCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        organizationGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), OrganizationGetDto.class);
    }

    private void persistOrganizationToUpdate() throws Exception {

        var result = mockMvc.perform(post("/api/v1/organization/")
                        .content(objectMapper.writeValueAsString(otherOrganizationCreateDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        organizationGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), OrganizationGetDto.class);
    }

    @Test
    @DisplayName("Test if create organization works correctly")
    void createOrganizationAndReturnGetDto() throws Exception {

       mockMvc.perform(post("/api/v1/organization/")
                .content(objectMapper.writeValueAsString(organizationCreateDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(organizationCreateDto.name())))
                .andExpect(jsonPath("$.email", is(organizationCreateDto.email())))
                .andReturn();

    }

    @ParameterizedTest(name = "Test {index}: Creating organization with {1}")
    @MethodSource("orgCreateDtoProvider")
    @DisplayName("Test if create organization send DataIntegrityViolationException")
    void createOrganizationThrowsDataIntegrityException(OrganizationCreateDto organizationCreateDto, String fieldBeingTested) throws Exception {

        persistOrganization();

        mockMvc.perform(post("/api/v1/organization/")
                        .content(objectMapper.writeValueAsString(organizationCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Please try again.")))
                .andReturn();

    }


    @Test
    @DisplayName("Test if get all organizations works correctly")
    void getAllOrganizations() throws Exception {

        persistOrganization();

        mockMvc.perform(get("/api/v1/organization/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(organizationGetDto.id())))
                .andExpect(jsonPath("$[0].name", is(organizationGetDto.name())))
                .andExpect(jsonPath("$[0].email", is(organizationGetDto.email())))
                .andExpect(jsonPath("$[0].nif", is(organizationGetDto.nif())))
                .andExpect(jsonPath("$[0].phoneNumber", is(organizationGetDto.phoneNumber())))
                .andExpect(jsonPath("$[0].websiteUrl", is(organizationGetDto.websiteUrl())));

    }

    @Test
    @DisplayName("Test if get organization by id works correctly")
    void getOrganizationById() throws Exception {

        persistOrganization();

        mockMvc.perform(get("/api/v1/organization/id/{id}", organizationGetDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(organizationGetDto.id())))
                .andExpect(jsonPath("$.name", is(organizationGetDto.name())))
                .andExpect(jsonPath("$.email", is(organizationGetDto.email())))
                .andExpect(jsonPath("$.nif", is(organizationGetDto.nif())))
                .andExpect(jsonPath("$.phoneNumber", is(organizationGetDto.phoneNumber())));
    }

    @Test
    @DisplayName("Test if get organization by id throws organization not found exception")
    void getOrganizationByIdThrowsOrganizationNotFoundException() throws Exception {

        mockMvc.perform(get("/api/v1/organization/id/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test if update organization works correctly")
    void updateOrganization() throws Exception {

        persistOrganization();

        mockMvc.perform(put("/api/v1/organization/update/{id}", organizationGetDto.id())
                        .content(objectMapper.writeValueAsString(organizationUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(organizationUpdateDto.email())));
    }

    @Test
    @DisplayName("Test if update organization throws organization not found exception")
    void updateOrganizationThrowsOrganizationNotFoundException() throws Exception {

        mockMvc.perform(put("/api/v1/organization/update/{id}", "11111-11111-1111-1111")
                        .content(objectMapper.writeValueAsString(organizationUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest(name = "Test {index}: update organization with {1}")
    @MethodSource("orgUpdateDtoProvider")
    @DisplayName("Test if update organization send DataIntegrityViolationException")
    void updateOrganizationThrowsDataIntegrityException(OrganizationUpdateDto organizationUpdateDto, String fieldBeingTested) throws Exception {

        persistOrganization();

        persistOrganizationToUpdate();

        mockMvc.perform(put("/api/v1/organization/update/{id}", organizationGetDto.id())
                        .content(objectMapper.writeValueAsString(organizationUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Please try again.")))
                .andReturn();
    }


    @Test
    @DisplayName("Test if delete organization works correctly")
    void deleteOrganization() throws Exception {

        persistOrganization();

        mockMvc.perform(delete("/api/v1/organization/delete/{id}", organizationGetDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(ORG_WITH_ID + organizationGetDto.id() + DELETE_SUCCESS));
    }

    @Test
    @DisplayName("Test if delete organization throws organization not found exception")
    void deleteOrganizationThrowsOrganizationNotFoundException() throws Exception {

        mockMvc.perform(delete("/api/v1/organization/delete/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
