package com.oficinaapp.oficina_app.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Time as a dependency: use cases read the clock instead of calling
 * {@code LocalDateTime.now()}, which keeps them testable.
 */
@Configuration
public class ClockConfig {

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }
}
