package com.petadoption.center.exception.db;

import static com.petadoption.center.util.Messages.DB_CONNECTION_ERROR;

public class DatabaseConnectionException extends DatabaseException {
    public DatabaseConnectionException() {
        super(DB_CONNECTION_ERROR);
    }
}
