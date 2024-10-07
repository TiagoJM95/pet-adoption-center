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

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.createAddress;
import static com.petadoption.center.testUtils.TestEntityFactory.createSocialMedia;
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
    private OrganizationGetDto updatedOrganizationGetDto;

    @BeforeEach
    void setUp() {
        organizationCreateDto = orgCreateDto();
        organizationUpdateDto = orgUpdateDto();
        updatedOrganizationGetDto = OrganizationGetDto.builder()
                .id("777777-77777777-7777")
                .name("Pet Adoption Center")
                .email("email@email.com")
                .nif("123456789")
                .phoneNumber("123456789")
                .address(createAddress())
                .websiteUrl("https://www.org.com")
                .socialMedia(createSocialMedia())
                .createdAt(LocalDateTime.of(2024,1,1,1,1))
                .build();
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

}
