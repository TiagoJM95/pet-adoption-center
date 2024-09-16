package com.petadoption.center.aspect;

import com.petadoption.center.exception.breed.BreedDuplicateException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorDuplicateException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.db.DatabaseConnectionException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.organization.OrganizationDuplicateException;
import com.petadoption.center.exception.pet.PetDuplicateException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesDuplicateException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.exception.user.UserDuplicateException;
import com.petadoption.center.exception.user.UserNotFoundException;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

import static com.petadoption.center.util.Messages.*;


@Aspect
@ControllerAdvice
public class ExceptionsHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(value = {UserNotFoundException.class, BreedNotFoundException.class, ColorNotFoundException.class, OrgNotFoundException.class, PetNotFoundException.class, SpeciesNotFoundException.class})
    public ResponseEntity<String> NotFoundHandler(Exception ex) {
        logger.error(LOGGER_NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

    }


    @ExceptionHandler(value = {DatabaseConnectionException.class})
    public ResponseEntity<String> DbConnectionHandler(Exception ex) {
        logger.error(LOGGER_DB_CONNECTION, ex.getMessage());
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(ex.getMessage());

    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<String> RequestBodyHandler(Exception ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

/*    @ExceptionHandler(value = {SQLException.class})
    public ResponseEntity<String> DbDuplicateHandler(Exception ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }*/

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) throws UserDuplicateException {
        String message = ex.getMostSpecificCause().getMessage();

        System.out.println(message);
        if (message.contains("(email)")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This email is already registered.");
        } else if (message.contains("(nif)")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This NIF is already in use.");
        } else if (message.contains("(phoneNumber)")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This phone number is already in use.");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Something went wrong saving user in the database.");
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }
}