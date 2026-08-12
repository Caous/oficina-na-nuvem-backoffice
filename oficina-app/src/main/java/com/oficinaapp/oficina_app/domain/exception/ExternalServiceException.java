package com.oficinaapp.oficina_app.domain.exception;

/**
 * A service we do not own refused to answer — the FIPE table, for instance.
 */
public class ExternalServiceException extends DomainException {

    public ExternalServiceException(String service) {
        super(service + " is unavailable right now. Try again in a moment.");
    }
}
