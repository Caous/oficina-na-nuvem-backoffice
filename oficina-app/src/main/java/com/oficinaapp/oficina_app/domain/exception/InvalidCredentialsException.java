package com.oficinaapp.oficina_app.domain.exception;

/**
 * Raised for both an unknown e-mail and a wrong password: telling them apart
 * would let anyone probe which accounts exist.
 */
public class InvalidCredentialsException extends DomainException {

    public InvalidCredentialsException() {
        super("Invalid e-mail or password.");
    }
}
