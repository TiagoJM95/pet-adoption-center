package com.petadoption.center.aspect;

import com.petadoption.center.config.DbConnection;
import com.petadoption.center.exception.DatabaseConnectionException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Aspect
@ControllerAdvice
public class DbAspect {

    private final DbConnection dbConnection;

    @Autowired
    public DbAspect(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Before("execution(* com.petadoption.center.service.*.*(..))")
    public void testDbConnection() throws DatabaseConnectionException {
        dbConnection.checkDbConnection();
    }
}