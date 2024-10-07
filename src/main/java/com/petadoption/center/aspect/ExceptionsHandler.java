package com.petadoption.center.aspect;

import com.petadoption.center.exception.DatabaseConnectionException;
import com.petadoption.center.exception.not_found.ModelNotFoundException;
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.petadoption.center.util.Messages.LOGGER_DB_CONNECTION;
import static com.petadoption.center.util.Messages.LOGGER_NOT_FOUND;

@Aspect
@ControllerAdvice
public class ExceptionsHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<String> notFoundHandler(Exception ex) {
        logger.error(LOGGER_NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<String> dbConnectionHandler(Exception ex) {
        logger.error(LOGGER_DB_CONNECTION, ex.getMessage());
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> requestBodyHandler(Exception ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Throwable cause = ex.getCause();
        String message = (cause != null) ? cause.getMessage() : "Unknown error";

        Matcher keyValueMatcher = Pattern.compile("Key \\(([^)]+)\\)=\\(([^)]+)\\)").matcher(message); // regex key values
        Matcher tableNameMatcher = Pattern.compile("insert into ([^\\s]+)").matcher(message); // regex table name

        String tableName = null;

        if (tableNameMatcher.find()) {
            tableName = tableNameMatcher.group(1);
            if (!tableName.isEmpty()) {
                tableName = tableName.substring(0, tableName.length() - 1); // remove last letter of table name
            }
        }

        if (keyValueMatcher.find()) {
            String[] keys = keyValueMatcher.group(1).split(",\\s");
            String[] values = keyValueMatcher.group(2).split(",\\s");

            StringBuilder output = new StringBuilder(); // build error message
            if (tableName != null) {
                output.append(tableName).append(" with ");
            }
            for (int i = 0; i < keys.length; i++) {
                if(i == keys.length - 1) {
                    output.append(keys[i]).append(" ").append(values[i]).append(" already exists.");
                    break;
                }
                output.append(keys[i]).append(" ").append(values[i]).append(" ").append("and ");
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(output.toString());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("You have entered an invalid data. Please try again.");
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("An unexpected error occurred: " + ex.getMessage());
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