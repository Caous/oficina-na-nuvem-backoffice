package com.oficinaapp.oficina_app.domain.exception;

/**
 * Base for every rule the domain refuses to break. The presentation layer maps
 * each subtype to an HTTP status, keeping the domain free of web concerns.
 */
public abstract class DomainException extends RuntimeException {

    protected DomainException(String message) {
        super(message);
    }
}
