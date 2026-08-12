package com.oficinaapp.oficina_app.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Three circles of access: open (sign in and self service sign up), the
 * customer area under {@code /api/me}, and the workshop area, which only an
 * account bound to a workshop may reach.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String OWNER = "WORKSHOP_OWNER";
    private static final String EMPLOYEE = "WORKSHOP_EMPLOYEE";

    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/login",
            "/api/auth/register/customer",
            "/api/auth/register/workshop"
    };

    /** The API description is public; the endpoints it describes are not. */
    private static final String[] DOCUMENTATION = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    private static final String[] WORKSHOP_AREA = {
            "/api/employees/**",
            "/api/service-categories/**",
            "/api/services/**",
            "/api/service-orders/**",
            "/api/products/**",
            "/api/dashboard/**",
            "/api/customers/**"
    };

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtTokenCodec tokenCodec,
            JsonAuthenticationErrorHandler errorHandler
    ) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()

                        .requestMatchers(DOCUMENTATION).permitAll()

                        // Without this, a failed request is forwarded to /error with no
                        // security context and answers 401, hiding the real status.
                        .requestMatchers("/error").permitAll()

                        // Hiring, firing and editing the team is the owner's call.
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole(OWNER)
                        .requestMatchers(HttpMethod.PUT, "/api/employees/*").hasRole(OWNER)
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/*").hasRole(OWNER)

                        .requestMatchers(WORKSHOP_AREA).hasAnyRole(OWNER, EMPLOYEE)
                        .anyRequest().authenticated())
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(errorHandler)
                        .accessDeniedHandler(errorHandler))
                .addFilterBefore(new JwtAuthenticationFilter(tokenCodec), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Open during development so the Flutter web build can call the API from
     * any port. Narrow it to the real origins before deploying.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
