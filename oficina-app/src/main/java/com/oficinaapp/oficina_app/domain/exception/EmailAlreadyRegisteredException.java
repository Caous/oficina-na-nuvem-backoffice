package com.oficinaapp.oficina_app.domain.exception;

public class EmailAlreadyRegisteredException extends DomainException {

    public EmailAlreadyRegisteredException() {
        super("This e-mail is already registered.");
    }
}
