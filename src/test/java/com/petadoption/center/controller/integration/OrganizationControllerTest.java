package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.organization.OrganizationUpdateDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.util.Messages.DELETE_SUCCESS;
import static com.petadoption.center.util.Messages.ORG_WITH_ID;
import static jakarta.transaction.Transactional.TxType.NEVER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private OrganizationGetDto organizationGetDto;
    private OrganizationCreateDto organizationCreateDto;
    private OrganizationUpdateDto organizationUpdateDto;


    @BeforeEach
    void setUp() {
        organizationCreateDto = orgCreateDto();
        organizationUpdateDto = orgUpdateDto();
    }

    @Test
    @DisplayName("Test if create organization works correctly")
    void createOrganizationAndReturnGetDto() throws Exception {

       var result = mockMvc.perform(post("/api/v1/organization/")
                .content(objectMapper.writeValueAsString(organizationCreateDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(organizationCreateDto.name())))
                .andExpect(jsonPath("$.email", is(organizationCreateDto.email())))
                .andReturn();


       organizationGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), OrganizationGetDto.class);

    }

    @Test
    @DisplayName("Test if create organization send DataIntegrityViolationException")
    @Transactional(value = NEVER)
    void createOrganizationThrowsDataIntegrityException() throws Exception {

        mockMvc.perform(post("/api/v1/organization/")
                        .content(objectMapper.writeValueAsString(organizationCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(post("/api/v1/organization/")
                        .content(objectMapper.writeValueAsString(organizationCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

    }

    @Test
    @DisplayName("Test if get all organizations works correctly")
    void getAllOrganizations() throws Exception {

        createOrganizationAndReturnGetDto();

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

        createOrganizationAndReturnGetDto();

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

        createOrganizationAndReturnGetDto();

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


    @Test
    @DisplayName("Test if delete organization works correctly")
    void deleteOrganization() throws Exception {

        createOrganizationAndReturnGetDto();

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
