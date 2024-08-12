package com.petadoption.center.util;

import com.petadoption.center.exception.db.DatabaseConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;

public class Utils {

    public static void checkDbConnection() throws DatabaseConnectionException {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            jdbcTemplate.execute("SELECT 1");
        } catch (Exception e) {
            throw new DatabaseConnectionException();
        }
    }
}
