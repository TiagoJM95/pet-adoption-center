package com.petadoption.center.exception;

import static com.petadoption.center.util.Messages.DB_CONNECTION_ERROR;

public class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException() {
        super(DB_CONNECTION_ERROR);
    }
}