package com.petadoption.center.controller.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

public class TestContainerConfig {

    protected static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;
    protected static final GenericContainer<?> REDIS_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:alpine")
                .withDatabaseName("petCenterTests")
                .withUsername("root")
                .withPassword("secret");

        POSTGRE_SQL_CONTAINER.start();

        REDIS_CONTAINER = new GenericContainer<>("redis:alpine")
                .withExposedPorts(6379);

        REDIS_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);

        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379));
    }
}