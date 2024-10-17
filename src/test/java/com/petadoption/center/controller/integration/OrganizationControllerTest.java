package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import com.petadoption.center.aspect.Error;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.createAddress;
import static com.petadoption.center.testUtils.TestEntityFactory.createSocialMedia;
import static com.petadoption.center.util.Messages.ORG_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrganizationControllerTest extends TestContainerConfig {

    private OrganizationGetDto organizationGetDto;
    private OrganizationCreateDto organizationCreateDto;
    private OrganizationUpdateDto organizationUpdateDto;
    private String organizationId;

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
                Arguments.of(baseOrg.toBuilder().email("org@email.com").build(), "repeated email", "uniqueorgemail"),
                Arguments.of(baseOrg.toBuilder().nipc("523456789").build(), "repeated nipc", "uniqueorgnipc"),
                Arguments.of(baseOrg.toBuilder().phoneNumber("923456789").build(), "repeated phone number", "uniqueorgphonenumber"),
                Arguments.of(baseOrg.toBuilder().address(Address.builder()
                        .street("Rua de Santo Antonio, 123")
                        .postalCode("4444-444")
                        .city("Lisbon")
                        .state("Lisbon")
                        .build()).build(), "repeated address - Street + postal code", "uniqueorgstreetandpostalcode"),

                Arguments.of(baseOrg.toBuilder().websiteUrl("https://www.org.com").build(), "repeated website url", "uniqueorgwebsiteurl"),

                Arguments.of(baseOrg.toBuilder().socialMedia(SocialMedia.builder()
                        .facebook("https://www.facebook.com")
                        .build()).build(), "repeated social media - facebook", "uniqueorgfacebook"),

                Arguments.of(baseOrg.toBuilder().socialMedia(SocialMedia.builder()
                        .instagram("https://www.instagram.com")
                        .build()).build(), "repeated social media - instagram", "uniqueorginstagram"),

                Arguments.of(baseOrg.toBuilder().socialMedia(SocialMedia.builder()
                        .twitter("https://www.twitter.com")
                        .build()).build(), "repeated social media - twitter", "uniqueorgtwitter"),

                Arguments.of(baseOrg.toBuilder().socialMedia(SocialMedia.builder()
                        .youtube("https://www.youtube.com")
                        .build()).build(), "repeated social media - youtube", "uniqueorgyoutube")
        );
    }

    static Stream<Arguments> orgUpdateDtoProvider() {
        OrganizationUpdateDto baseOrg = OrganizationUpdateDto.builder()
                .name("Adopting Center")
                .email("adopting@email.com")
                .phoneNumber("937654123")
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
                Arguments.of(baseOrg.toBuilder().email("org@email.com").build(), "repeated email", "uniqueorgemail"),
                Arguments.of(baseOrg.toBuilder().phoneNumber("923456789").build(), "repeated phone number", "uniqueorgphonenumber"),
                Arguments.of(baseOrg.toBuilder().address(Address.builder()
                                .street("Rua de Santo Antonio, 123")
                                .postalCode("4444-444")
                                .city("Lisbon")
                                .state("Lisbon")
                                .build())
                        .build(), "repeated address - Street + postal code", "uniqueorgstreetandpostalcode"),

                Arguments.of(baseOrg.toBuilder().websiteUrl("https://www.org.com").build(), "repeated website url", "uniqueorgwebsiteurl"),

                Arguments.of(baseOrg.toBuilder().socialMedia(SocialMedia.builder()
                        .facebook("https://www.facebook.com")
                        .build()).build(), "repeated social media - facebook", "uniqueorgfacebook"),

                Arguments.of(baseOrg.toBuilder().socialMedia(SocialMedia.builder()
                        .instagram("https://www.instagram.com")
                        .build()).build(), "repeated social media - instagram", "uniqueorginstagram"),

                Arguments.of(baseOrg.toBuilder().socialMedia(SocialMedia.builder()
                        .twitter("https://www.twitter.com")
                        .build()).build(), "repeated social media - twitter", "uniqueorgtwitter"),

                Arguments.of(baseOrg.toBuilder().socialMedia(SocialMedia.builder()
                        .youtube("https://www.youtube.com")
                        .build()).build(), "repeated social media - youtube", "uniqueorgyoutube")
        );
    }

    private OrganizationGetDto persistOrganization() throws Exception {

        var result = mockMvc.perform(post("/api/v1/organization/")
                        .content(objectMapper.writeValueAsString(organizationCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        OrganizationGetDto resultDto = objectMapper.readValue(result.getResponse().getContentAsString(), OrganizationGetDto.class);
        organizationId = resultDto.id();
        return resultDto;
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

        OrganizationGetDto expectedOrganization = OrganizationGetDto.builder()
                .name("Pet Adoption Center")
                .email("org@email.com")
                .nipc("523456789")
                .phoneNumber("923456789")
                .address(createAddress())
                .websiteUrl("https://www.org.com")
                .socialMedia(createSocialMedia())
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();

       OrganizationGetDto orgGetDto = persistOrganization();

       assertThat(orgGetDto)
               .usingRecursiveComparison()
               .ignoringFields("id")
               .ignoringFieldsMatchingRegexes(".*createdAt")
               .isEqualTo(expectedOrganization);

        assertNotNull(orgGetDto.createdAt());
        assertTrue(orgGetDto.id().matches("^[0-9a-fA-F-]{36}$"));

    }

    @ParameterizedTest(name = "Test {index}: Creating organization with {1}")
    @MethodSource("orgCreateDtoProvider")
    @DisplayName("Test if create organization send DataIntegrityViolationException")
    void createOrganizationThrowsDataIntegrityException(OrganizationCreateDto organizationCreateDto, String fieldBeingTested, String constraint) throws Exception {

        persistOrganization();

        var result = mockMvc.perform(post("/api/v1/organization/")
                        .content(objectMapper.writeValueAsString(organizationCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertEquals(error.constraint(), constraint);
    }

    @Test
    @DisplayName("Test if throw HttpMessageNotReadableException if request body is empty")
    void shouldThrowHttpMessageNotReadableException_WhenCreateIsCalledWithNoBody() throws Exception {

        var result = mockMvc.perform(post("/api/v1/organization/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertTrue(error.message().contains("Required request body is missing"));
    }

    @Test
    @DisplayName("Test if get all organizations works correctly")
    void getAllOrganizations() throws Exception {

        OrganizationGetDto orgCreatedDto= persistOrganization();

        mockMvc.perform(get("/api/v1/organization/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(orgCreatedDto.id())))
                .andExpect(jsonPath("$[0].name", is(orgCreatedDto.name())))
                .andExpect(jsonPath("$[0].email", is(orgCreatedDto.email())))
                .andExpect(jsonPath("$[0].nipc", is(orgCreatedDto.nipc())))
                .andExpect(jsonPath("$[0].phoneNumber", is(orgCreatedDto.phoneNumber())))
                .andExpect(jsonPath("$[0].websiteUrl", is(orgCreatedDto.websiteUrl())));
    }

    @Test
    @DisplayName("Test if get all organizations return a empty list if no organizations in database")
    void getAllOrganizationsEmptyList() throws Exception {

        mockMvc.perform(get("/api/v1/organization/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Test if get organization by id works correctly")
    void getOrganizationById() throws Exception {

        OrganizationGetDto orgCreated = persistOrganization();

        var result = mockMvc.perform(get("/api/v1/organization/id/{id}", organizationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        OrganizationGetDto getResult = objectMapper.readValue(result.getResponse().getContentAsString(), OrganizationGetDto.class);

        assertThat(getResult)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(orgCreated);
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

        OrganizationGetDto organizationUpdatedGetDto = OrganizationGetDto.builder()
                .name(organizationUpdateDto.name())
                .email(organizationUpdateDto.email())
                .phoneNumber(organizationUpdateDto.phoneNumber())
                .address(organizationUpdateDto.address())
                .websiteUrl(organizationUpdateDto.websiteUrl())
                .socialMedia(organizationUpdateDto.socialMedia())
                .build();

        persistOrganization();

        var result = mockMvc.perform(put("/api/v1/organization/update/{id}", organizationId)
                        .content(objectMapper.writeValueAsString(organizationUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        OrganizationGetDto getUpdateResult = objectMapper.readValue(result.getResponse().getContentAsString(), OrganizationGetDto.class);

        assertThat(getUpdateResult)
                .usingRecursiveComparison()
                .ignoringFields("id", "nipc")
                .ignoringFieldsMatchingRegexes(".*createdAt")
                .isEqualTo(organizationUpdatedGetDto);
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
    void updateOrganizationThrowsDataIntegrityException(OrganizationUpdateDto organizationUpdateDto, String fieldBeingTested, String constraint) throws Exception {

        persistOrganization();

        persistOrganizationToUpdate();

       var result = mockMvc.perform(put("/api/v1/organization/update/{id}", organizationGetDto.id())
                        .content(objectMapper.writeValueAsString(organizationUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        Error error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertEquals(error.constraint(), constraint );
    }

    @Test
    @DisplayName("Test if delete organization works correctly")
    void deleteOrganization() throws Exception {

        persistOrganization();

        mockMvc.perform(delete("/api/v1/organization/delete/{id}", organizationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(ORG_DELETE_MESSAGE, organizationId)));
    }

    @Test
    @DisplayName("Test if delete organization throws organization not found exception")
    void deleteOrganizationThrowsOrganizationNotFoundException() throws Exception {

        mockMvc.perform(delete("/api/v1/organization/delete/{id}", "11111-11111-1111-1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
