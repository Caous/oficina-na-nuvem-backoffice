package com.oficinaapp.oficina_app.presentation.exception;

import com.oficinaapp.oficina_app.domain.exception.DisabledAccountException;
import com.oficinaapp.oficina_app.domain.exception.DocumentAlreadyRegisteredException;
import com.oficinaapp.oficina_app.domain.exception.EmailAlreadyRegisteredException;
import com.oficinaapp.oficina_app.domain.exception.ExternalServiceException;
import com.oficinaapp.oficina_app.domain.exception.InsufficientStockException;
import com.oficinaapp.oficina_app.domain.exception.InvalidCredentialsException;
import com.oficinaapp.oficina_app.domain.exception.OperationNotAllowedException;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.exception.UserAccountNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps domain failures to HTTP. The domain stays unaware of status codes, and
 * every error reaches the app in the same shape.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentials(InvalidCredentialsException exception) {
        return respond(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(DisabledAccountException.class)
    public ResponseEntity<ApiError> handleDisabledAccount(DisabledAccountException exception) {
        return respond(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<ApiError> handleOperationNotAllowed(OperationNotAllowedException exception) {
        return respond(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiError> handleInsufficientStock(InsufficientStockException exception) {
        return respond(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler({UserAccountNotFoundException.class, ResourceNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(RuntimeException exception) {
        return respond(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({EmailAlreadyRegisteredException.class, DocumentAlreadyRegisteredException.class})
    public ResponseEntity<ApiError> handleAlreadyRegistered(RuntimeException exception) {
        return respond(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ApiError> handleExternalService(ExternalServiceException exception) {
        return respond(HttpStatus.BAD_GATEWAY, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> fields = new HashMap<>();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            fields.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiError.of(HttpStatus.BAD_REQUEST, "Some fields are invalid.", fields));
    }

    /** A unique index we did not check by hand still has to read as a conflict. */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException exception) {
        log.warn("Database rejected the request", exception);

        return respond(HttpStatus.CONFLICT, "This record conflicts with one that already exists.");
    }

    /**
     * Last resort: the caller gets the usual shape and nothing about our
     * internals, while the log keeps the whole story.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception exception) {
        log.error("Unhandled failure", exception);

        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong on our side.");
    }

    private ResponseEntity<ApiError> respond(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ApiError.of(status, message));
    }
}
