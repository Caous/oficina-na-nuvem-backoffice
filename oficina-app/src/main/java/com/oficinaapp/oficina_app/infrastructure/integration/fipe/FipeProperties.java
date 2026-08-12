package com.oficinaapp.oficina_app.infrastructure.integration.fipe;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integration.fipe")
public record FipeProperties(String baseUrl) {
}
