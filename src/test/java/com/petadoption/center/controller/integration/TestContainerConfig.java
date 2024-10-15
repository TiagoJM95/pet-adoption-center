package com.petadoption.center.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.repository.*;
import com.petadoption.center.testUtils.TestPersistenceHelper;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestContainerConfig {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected TestPersistenceHelper helper;

    @Autowired
    protected  UserRepository userRepository;
    @Autowired
    protected  PetRepository petRepository;
    @Autowired
    protected  OrganizationRepository organizationRepository;
    @Autowired
    protected  AdoptionFormRepository adoptionFormRepository;
    @Autowired
    protected  SpeciesRepository speciesRepository;
    @Autowired
    protected  BreedRepository breedRepository;
    @Autowired
    protected  ColorRepository colorRepository;
    @Autowired
    protected  InterestRepository interestRepository;

    protected String URL = "/api/v1";

    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("petCenterTests")
                .withUsername("root")
                .withPassword("secret");

            POSTGRE_SQL_CONTAINER.start();
    }


    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
    }
}

