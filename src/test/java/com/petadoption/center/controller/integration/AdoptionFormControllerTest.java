package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.Species;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
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

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class AdoptionFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestPersistenceHelper testPersistenceHelper;

    private AdoptionFormCreateDto adoptionFormCreateDto;
    private AdoptionFormGetDto adoptionFormGetDto;
    private AdoptionFormUpdateDto adoptionFormUpdateDto;
    private Address address;
    private String userId;
    private String petId;
    private String orgId;
    private String speciesId;
    private String breedId;
    private String colorId;
    private String id;

    @BeforeEach
    void setUp() throws Exception {
        Species species = testPersistenceHelper.persistSpecies();
        speciesId = species.getId();
        breedId = testPersistenceHelper.persistTestBreed(species);
        colorId = testPersistenceHelper.persistTestColor();
        orgId = testPersistenceHelper.persistTestOrg();

        Family family = new Family(
                4,
                true,
                true,
                2,
                List.of("DOG", "PARROT")
        );

        address = new Address("Rua das Andorinhas, 123",
                "Vila Nova de Gaia",
                "Porto",
                "4410-000");

        addUser();
        addPet();

        adoptionFormCreateDto = new AdoptionFormCreateDto(
                userId,
                petId,
                family,
                "Neighbour",
                true,
                "Notes",
                address
        );
    }

    void addUser() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("Manuel")
                .lastName("Silva")
                .email("email@email.com")
                .nif("123456789")
                .dateOfBirth(LocalDate.of(1990, 10, 25))
                .address(address)
                .phoneNumber("123456789")
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/user/")
                .content(objectMapper.writeValueAsString(userCreateDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        UserGetDto userCreated = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto.class);

        userId = userCreated.id();
    }

    void addPet() throws Exception {
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
                .organizationId(orgId)
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/pet/addSingle")
                        .content(objectMapper.writeValueAsString(petCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        PetGetDto petCreated = objectMapper.readValue(result.getResponse().getContentAsString(), PetGetDto.class);

        petId = petCreated.id();
    }

    @Test
    @DisplayName("Test if create adoption form works correctly")
    @DirtiesContext
    void createAdoptionFormShouldWork() throws Exception {

        MvcResult result = mockMvc.perform(post("/api/v1/adoption-form/")
                .content(objectMapper.writeValueAsString(adoptionFormCreateDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        AdoptionFormGetDto adoptionFormCreated = objectMapper.readValue(result.getResponse().getContentAsString(), AdoptionFormGetDto.class);

    }

    @Test
    @DisplayName("Test get all adoption forms when empty returns empty")
    @DirtiesContext
    void testGetAllEmptyAdoptionFormsReturnsEmpty() throws Exception {

        mockMvc.perform(get("/api/v1/adoption-form/")
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)))
                .andReturn();
    }

    @Test
    @DisplayName("Test get all adoption with 1 element returns size 1")
    @DirtiesContext
    void testGetAllAdoptionFormsReturnsOne() throws Exception {

        createAdoptionFormShouldWork();

        mockMvc.perform(get("/api/v1/adoption-form/")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andReturn();
    }
}
