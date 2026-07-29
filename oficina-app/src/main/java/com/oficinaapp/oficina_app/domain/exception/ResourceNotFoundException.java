package com.oficinaapp.oficina_app.domain.exception;

/**
 * The record does not exist, or belongs to someone else — the caller is told
 * the same thing either way, so ids cannot be probed.
 */
public class ResourceNotFoundException extends DomainException {

    public ResourceNotFoundException(String resource) {
        super(resource + " not found.");
    }
}
