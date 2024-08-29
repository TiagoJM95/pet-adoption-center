package com.petadoption.center.aspect;

import com.petadoption.center.exception.breed.BreedNameDuplicateException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorDuplicateException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.db.DatabaseConnectionException;
import com.petadoption.center.exception.organization.*;
import com.petadoption.center.exception.pet.PetDuplicateImageException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNameDuplicateException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.exception.user.UserEmailDuplicateException;
import com.petadoption.center.exception.user.UserNotFoundException;
import com.petadoption.center.exception.user.UserPhoneNumberDuplicateException;
import com.petadoption.center.util.DbConnection;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.petadoption.center.util.Messages.*;


@Aspect
@ControllerAdvice
public class ExceptionsHandler {

    private final DbConnection dbConnection;

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    @Autowired
    public ExceptionsHandler(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @ExceptionHandler(value = {UserNotFoundException.class, BreedNotFoundException.class, ColorNotFoundException.class, OrgNotFoundException.class, PetNotFoundException.class, SpeciesNotFoundException.class})
    public ResponseEntity<String> NotFoundHandler(Exception ex) {
        logger.error(LOGGER_NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

    }


    @ExceptionHandler(value = {BreedNameDuplicateException.class, ColorDuplicateException.class, OrgDuplicateAddressException.class, OrgDuplicateEmailException.class, OrgDuplicatePhoneNumberException.class, OrgDuplicateSocialMediaException.class, OrgDuplicateWebsiteException.class, PetDuplicateImageException.class, SpeciesNameDuplicateException.class, UserPhoneNumberDuplicateException.class, UserEmailDuplicateException.class})
    public ResponseEntity<String> DuplicateHandler(Exception ex) {
        logger.error(LOGGER_DUPLICATE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());

    }

    @ExceptionHandler(value = {DatabaseConnectionException.class})
    public ResponseEntity<String> DbConnectionHandler(Exception ex) {
        logger.error(LOGGER_DB_CONNECTION, ex.getMessage());
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(ex.getMessage());

    }

    @Before("execution(* com.petadoption.center.service.*.*(..))")
    public void testDbConnection() throws DatabaseConnectionException {
        dbConnection.checkDbConnection();

    }

}
