package com.oficinaapp.oficina_app.domain.exception;

public class DisabledAccountException extends DomainException {

    public DisabledAccountException() {
        super("This account is disabled.");
    }
}
