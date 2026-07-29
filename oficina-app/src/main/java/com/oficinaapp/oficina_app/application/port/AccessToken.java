package com.oficinaapp.oficina_app.application.port;

import java.time.Instant;

public record AccessToken(String value, Instant expiresAt) {
}
