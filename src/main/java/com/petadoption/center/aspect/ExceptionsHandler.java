package com.petadoption.center.aspect;

import com.petadoption.center.exception.BreedMismatchException;
import com.petadoption.center.exception.DatabaseConnectionException;
import com.petadoption.center.exception.InvalidStatusChangeException;
import com.petadoption.center.exception.not_found.ModelNotFoundException;
import com.petadoption.center.util.ConstraintMessageResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    private final ConstraintMessageResolver messageResolver;

    @Autowired
    public ExceptionsHandler(ConstraintMessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    @ExceptionHandler(ModelNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Error notFoundHandler(ModelNotFoundException ex, HttpServletRequest request) {
        logger.error(LOGGER_NOT_FOUND, ex.getMessage());
        return Error.builder()
                .timestamp(new Date())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build();
    }

    @ExceptionHandler(DatabaseConnectionException.class)
    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    public Error dbConnectionHandler(DatabaseConnectionException ex, HttpServletRequest request) {
        logger.error(LOGGER_DB_CONNECTION, ex.getMessage());
        return Error.builder()
                .timestamp(new Date())
                .status(HttpStatus.REQUEST_TIMEOUT.value())
                .error(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error requestBodyHandler(HttpMessageNotReadableException ex, HttpServletRequest request) {
        logger.error(LOGGER_NO_BODY, ex.getMessage());
        return Error.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Error handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        logger.error(LOGGER_DUPLICATE, ex.getMessage());
        String message = (ex.getCause() != null) ? ex.getCause().getMessage() : "Unknown error";

        Matcher constraintMatcher = Pattern.compile("constraint \"([^\"]+)\"").matcher(message);
        String constraintName = constraintMatcher.find() ? constraintMatcher.group(1) : "Unknown constraint";

        String friendlyMessage = messageResolver.getMessage(constraintName);
        String hint = messageResolver.getHint(constraintName);

        return Error.builder()
                .timestamp(new Date())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(friendlyMessage)
                .path(request.getRequestURI())
                .method(request.getMethod())
                .constraint(constraintName)
                .hint(hint)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    public Error handleGeneralException(Exception ex, HttpServletRequest request) {
        logger.error(LOGGER_EXCEPTION, ex.getMessage());
        return Error.builder()
                .timestamp(new Date())
                .status(HttpStatus.REQUEST_TIMEOUT.value())
                .error(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.error(LOGGER_VALIDATION, ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return Error.builder()
                .timestamp(new Date())
                .status(HttpStatus.REQUEST_TIMEOUT.value())
                .error(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .validationIssue(errors)
                .build();
    }

    @ExceptionHandler(BreedMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleBreedMismatchException(BreedMismatchException ex, HttpServletRequest request) {
        logger.error(LOGGER_MISMATCH, ex.getMessage());
        return Error.builder()
                .timestamp(new Date())
                .status(HttpStatus.REQUEST_TIMEOUT.value())
                .error(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidStatusChangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleInvalidStatusChangeException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.error(LOGGER_STATUS, ex.getMessage());
        return Error.builder()
                .timestamp(new Date())
                .status(HttpStatus.REQUEST_TIMEOUT.value())
                .error(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .message(ex.getMessage())
                .build();
    }
}