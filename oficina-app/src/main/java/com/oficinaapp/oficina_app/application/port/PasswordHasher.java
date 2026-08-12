package com.oficinaapp.oficina_app.application.port;

/**
 * Hashing algorithm used for credentials. The use cases depend on this port so
 * the algorithm can change without touching a single rule.
 */
public interface PasswordHasher {

    String hash(String rawPassword);

    boolean matches(String rawPassword, String passwordHash);
}
