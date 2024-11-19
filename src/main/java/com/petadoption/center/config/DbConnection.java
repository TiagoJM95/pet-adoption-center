package com.petadoption.center.config;

import com.petadoption.center.exception.DatabaseConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DbConnection {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbConnection(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void checkDbConnection() throws DatabaseConnectionException {
        try {
            jdbcTemplate.execute("SELECT 1");
        } catch (Exception e) {
            log.info("Database Connection Error: {}", e.getMessage());
            throw new DatabaseConnectionException();
        }
    }
}