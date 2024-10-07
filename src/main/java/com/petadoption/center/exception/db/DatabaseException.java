package com.petadoption.center.exception.db;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}