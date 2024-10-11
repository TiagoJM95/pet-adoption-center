package com.petadoption.center.aspect;

import com.petadoption.center.exception.DatabaseConnectionException;
import com.petadoption.center.exception.not_found.ModelNotFoundException;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.petadoption.center.util.Messages.*;

@RestControllerAdvice
public class ExceptionsHandler {

    // Adicionar handler para BreedMismatchException e InvalidStatusChangeException

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(ModelNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Error notFoundHandler(Exception ex, HttpServletRequest request) {
        logger.error(LOGGER_NOT_FOUND, ex.getMessage());
        return Error.builder()
                .timestamp(new Date())
                .message(ex.getMessage())
                .method(request.getMethod())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(DatabaseConnectionException.class)
    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    public Error dbConnectionHandler(Exception ex, HttpServletRequest request) {
        logger.error(LOGGER_DB_CONNECTION, ex.getMessage());
        return Error.builder()
                .timestamp(new Date())
                .message(ex.getMessage())
                .method(request.getMethod())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error requestBodyHandler(Exception ex, HttpServletRequest request) {
        logger.error(ex.getMessage());
        return Error.builder()
                .timestamp(new Date())
                .message(ex.getMessage())
                .method(request.getMethod())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Error handleDataIntegrityViolationException(Exception ex, HttpServletRequest request) {
        // Get the root cause of the exception to extract the database message
        String message = (ex.getCause() != null) ? ex.getCause().getMessage() : "Unknown error";

        // Pattern to extract the keys and values that caused the violation
        Matcher keyValueMatcher = Pattern.compile("Key \\(([^)]+)\\)=\\(([^)]+)\\)").matcher(message);
        String friendlyMessage = "Duplicate entry";  // Default message

        if (keyValueMatcher.find()) {
            String[] keys = keyValueMatcher.group(1).split(",\\s*");  // Get the keys (e.g., 'name', 'species_id', etc.)
            String[] values = keyValueMatcher.group(2).split(",\\s*");  // Get the values (e.g., 'Buddy', 'UUID1', 'UUID2', etc.)

            StringBuilder output = new StringBuilder();  // Build the key-value pair message
            for (int i = 0; i < keys.length; i++) {
                output.append(keys[i]).append(": ").append(values[i]);  // Append key and value
                if (i < keys.length - 1) {
                    output.append(", ");  // Add comma between pairs, but not after the last one
                }
            }
            if(keys.length > 1){
                friendlyMessage = output.append(" combination already exists!").toString();
            }
            else {
                friendlyMessage = output.append(" already exists!").toString();  // Append the final part of the message
            }
        }

        // Extract the constraint name if needed
        Matcher constraintMatcher = Pattern.compile("constraint \"([^\"]+)\"").matcher(message);
        String constraintName = constraintMatcher.find() ? constraintMatcher.group(1) : "Unknown constraint";

        return Error.builder()
                .timestamp(new Date())
                .message(friendlyMessage)  // Replace DB message with the friendly one
                .constraint(constraintName)
                .method(request.getMethod())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    public Error handleGeneralException(Exception ex, HttpServletRequest request) {
        logger.error(LOGGER_GENERIC, ex.getMessage());
        return Error.builder()
                .timestamp(new Date())
                .message(ex.getMessage())
                .method(request.getMethod())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> validationsHandlerNotValid(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}