package com.oficinaapp.oficina_app.domain.exception;

public class UserAccountNotFoundException extends DomainException {

    public UserAccountNotFoundException() {
        super("User account not found.");
    }
}
