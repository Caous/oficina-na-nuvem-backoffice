package com.oficinaapp.oficina_app.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Describes the API for Swagger UI.
 *
 * <p>The bearer scheme is declared once and required globally, so the
 * "Authorize" button in the UI unlocks every endpoint at once — paste the
 * {@code accessToken} that {@code /api/auth/login} returns.
 */
@Configuration
public class OpenApiConfig {

    private static final String BEARER_SCHEME = "bearerAuth";

    @Bean
    OpenAPI oficinaOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Oficina na Nuvem — Backoffice API")
                        .version("v1")
                        .description("""
                                API behind the Oficina na Nuvem mobile app.

                                One account table serves three roles: CUSTOMER, \
                                WORKSHOP_OWNER and WORKSHOP_EMPLOYEE. Sign in at \
                                /api/auth/login and press Authorize with the \
                                accessToken it returns.

                                Customer and workshop are many-to-many, and the \
                                marketplace lists published products from every \
                                workshop."""))
                .components(new Components().addSecuritySchemes(
                        BEARER_SCHEME,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME));
    }
}
