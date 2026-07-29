package com.oficinaapp.oficina_app.domain.exception;

/**
 * The caller is known, but this particular move breaks a rule — a closed order
 * being reopened, an account without a workshop reaching workshop data.
 */
public class OperationNotAllowedException extends DomainException {

    public OperationNotAllowedException(String message) {
        super(message);
    }
}
