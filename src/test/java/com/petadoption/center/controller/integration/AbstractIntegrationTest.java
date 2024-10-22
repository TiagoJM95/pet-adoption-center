package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.adoptionForm.AdoptionFormCreateDto;
import com.petadoption.center.dto.adoptionForm.AdoptionFormGetDto;
import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorCreateDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.interest.InterestCreateDto;
import com.petadoption.center.dto.interest.InterestGetDto;
import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.dto.user.UserCreateDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.repository.*;
import com.petadoption.center.testUtils.ConstantsURL;
import com.petadoption.center.testUtils.TestPersistenceHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.petadoption.center.testUtils.ConstantsURL.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractIntegrationTest extends TestContainerConfig {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected TestPersistenceHelper helper;
    @SpyBean
    protected UserRepository userRepository;
    @Autowired
    protected PetRepository petRepository;
    @Autowired
    protected OrganizationRepository organizationRepository;
    @Autowired
    protected AdoptionFormRepository adoptionFormRepository;
    @Autowired
    protected SpeciesRepository speciesRepository;
    @Autowired
    protected BreedRepository breedRepository;
    @Autowired
    protected ColorRepository colorRepository;
    @Autowired
    protected InterestRepository interestRepository;


    @AfterAll
    public void cleanAll() {
        interestRepository.deleteAll();
        adoptionFormRepository.deleteAll();
        petRepository.deleteAll();
        breedRepository.deleteAll();
        speciesRepository.deleteAll();
        organizationRepository.deleteAll();
        colorRepository.deleteAll();
        userRepository.deleteAll();
    }

    SpeciesGetDto persistSpecies(SpeciesCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post(SPECIES_GET_ALL_OR_CREATE_URL)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), SpeciesGetDto.class);
    }

    BreedGetDto persistBreed(BreedCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post(BREED_GET_ALL_OR_CREATE_URL)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), BreedGetDto.class);
    }

    ColorGetDto persistColor(ColorCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post(COLOR_GET_ALL_OR_CREATE_URL)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), ColorGetDto.class);
    }

    OrganizationGetDto persistOrganization(OrganizationCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post(ORG_GET_ALL_OR_CREATE_URL)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), OrganizationGetDto.class);
    }

    PetGetDto persistPet(PetCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post(PET_ADD_SINGLE_URL)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), PetGetDto.class);
    }

    UserGetDto persistUser(UserCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post(USER_GET_ALL_OR_CREATE_URL)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), UserGetDto.class);
    }

    InterestGetDto persistInterest(InterestCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post("")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), InterestGetDto.class);
    }

    AdoptionFormGetDto persistAdoptionForm(AdoptionFormCreateDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post(ADOPTION_FORM_GET_ALL_OR_CREATE_URL)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), AdoptionFormGetDto.class);
    }
}