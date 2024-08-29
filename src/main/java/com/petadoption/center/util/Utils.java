package com.petadoption.center.util;

import com.petadoption.center.exception.db.DatabaseConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Utils(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void checkDbConnection() throws DatabaseConnectionException {

        try {
            jdbcTemplate.execute("SELECT 1");
        } catch (Exception e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }
}
