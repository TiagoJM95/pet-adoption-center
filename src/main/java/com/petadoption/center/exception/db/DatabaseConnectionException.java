package com.petadoption.center.exception.db;

public class DatabaseConnectionException extends DatabaseException {
    public DatabaseConnectionException() {
        super("Database connection is not available");
    }
}
