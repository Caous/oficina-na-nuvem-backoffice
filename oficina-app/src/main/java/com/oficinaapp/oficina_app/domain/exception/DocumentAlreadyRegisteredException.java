package com.oficinaapp.oficina_app.domain.exception;

public class DocumentAlreadyRegisteredException extends DomainException {

    public DocumentAlreadyRegisteredException() {
        super("This document is already registered.");
    }
}
