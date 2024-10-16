package com.petadoption.center.controller.integration;

import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormUpdateDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.petadoption.center.testUtils.TestDtoFactory.userCreateDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createAttributes;
import static com.petadoption.center.util.Messages.ADOPTION_FORM_DELETE_MESSAGE;
import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdoptionFormControllerTest extends TestContainerConfig{

    private AdoptionFormCreateDto adoptionFormCreateDto;
    private AdoptionFormGetDto adoptionFormGetDto;
    private AdoptionFormUpdateDto adoptionFormUpdateDto;
    private UserGetDto userGetDto;
    private PetGetDto petGetDto;
    private String userId;
    private String petId;
    private String orgId;
    private String speciesId;
    private String breedId;
    private String colorId;
    private String adoptionFormId;


    @BeforeEach
    void setUp() throws Exception {
        speciesId = helper.persistTestSpecies();
        breedId = helper.persistTestPrimaryBreed();
        colorId = helper.persistTestPrimaryColor();
        orgId = helper.persistTestOrg();

        Family family = new Family(
                4,
                true,
                true,
                2,
                List.of("DOG", "PARROT")
        );

        Address address = new Address("Rua das Andorinhas, 123",
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

        adoptionFormUpdateDto = new AdoptionFormUpdateDto(
                family,
                "Pet Hotel",
                false,
                "Only 2 weeks per year of vacation",
                address
        );
    }

    @AfterEach
    void cleanTable(){
        helper.cleanAll();
    }

    private void addUser() throws Exception {
        UserCreateDto userCreateDto = userCreateDto();

        MvcResult result = mockMvc.perform(post("/api/v1/user/")
                .content(objectMapper.writeValueAsString(userCreateDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        userGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto.class);

        userId = userGetDto.id();
    }

    private void addPet() throws Exception {
        PetCreateDto petCreateDto = PetCreateDto.builder()
                .name("Max")
                .speciesId(speciesId)
                .primaryBreedId(breedId)
                .primaryColor(colorId)
                .gender("MALE")
                .coat("SHORT")
                .size("SMALL")
                .age("ADULT")
                .description("Max is a very friendly dog")
                .imageUrl("https://www.dogimages.com")
                .isAdopted(false)
                .attributes(createAttributes())
                .organizationId(orgId)
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/pet/addSingle")
                        .content(objectMapper.writeValueAsString(petCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        petGetDto = objectMapper.readValue(result.getResponse().getContentAsString(), PetGetDto.class);

        petId = petGetDto.id();
    }

    private void addAdoptionForm() throws Exception{

        MvcResult result = mockMvc.perform(post("/api/v1/adoption-form/")
                        .content(objectMapper.writeValueAsString(adoptionFormCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        AdoptionFormGetDto adoptionFormCreated = objectMapper.readValue(result.getResponse().getContentAsString(), AdoptionFormGetDto.class);

        adoptionFormId = adoptionFormCreated.id();

        adoptionFormGetDto = new AdoptionFormGetDto(
                adoptionFormId,
                userGetDto,
                petGetDto,
                adoptionFormCreated.userFamily(),
                adoptionFormCreated.petVacationHome(),
                adoptionFormCreated.isResponsibleForPet(),
                adoptionFormCreated.otherNotes(),
                adoptionFormCreated.petAddress(),
                adoptionFormCreated.createdAt()
        );
    }

    @Test
    @DisplayName("Test get all adoption forms when empty returns empty")
    void testGetAllEmptyAdoptionFormsReturnsEmpty() throws Exception {

        mockMvc.perform(get("/api/v1/adoption-form/")
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

        addAdoptionForm();

        mockMvc.perform(get("/api/v1/adoption-form/")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(adoptionFormGetDto.id())))
                .andExpect(jsonPath("$[0].user.id", is(adoptionFormGetDto.user().id())))
                .andExpect(jsonPath("$[0].pet.id", is(adoptionFormGetDto.pet().id())))
                .andReturn();
    }

    @Test
    @DisplayName("Test if create adoption form works correctly")
    void testCreateAdoptionForm() throws Exception {

        MvcResult result = mockMvc.perform(post("/api/v1/adoption-form/")
                        .content(objectMapper.writeValueAsString(adoptionFormCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        AdoptionFormGetDto adoptionFormCreated = objectMapper.readValue(result.getResponse().getContentAsString(), AdoptionFormGetDto.class);

        adoptionFormId = adoptionFormCreated.id();

        adoptionFormGetDto = new AdoptionFormGetDto(
                adoptionFormId,
                userGetDto,
                petGetDto,
                adoptionFormCreated.userFamily(),
                adoptionFormCreated.petVacationHome(),
                adoptionFormCreated.isResponsibleForPet(),
                adoptionFormCreated.otherNotes(),
                adoptionFormCreated.petAddress(),
                adoptionFormCreated.createdAt()
        );
    }

    @Test
    @DisplayName("Test if update an adoption form works correctly")
    void testUpdate() throws Exception {

        addAdoptionForm();

        mockMvc.perform(put("/api/v1/adoption-form/update/{id}", adoptionFormId)
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

        testUpdate();

        mockMvc.perform(delete("/api/v1/adoption-form/delete/{id}", adoptionFormId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(format(ADOPTION_FORM_DELETE_MESSAGE, adoptionFormId)));
    }
}
