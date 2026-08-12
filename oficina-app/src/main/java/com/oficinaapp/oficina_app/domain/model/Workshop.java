package com.oficinaapp.oficina_app.domain.model;

import com.oficinaapp.oficina_app.domain.support.TextNormalizer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * The repair shop itself. Its owner and its employees are {@link UserAccount}s
 * pointing back to this workshop.
 */
@Getter
@Builder(toBuilder = true)
public class Workshop {

    private final Long id;
    private final String tradeName;
    private final String document;
    private final String email;
    private final String phone;
    private final Address address;
    private final boolean enabled;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static Workshop register(
            String tradeName,
            String document,
            String email,
            String phone,
            Address address,
            LocalDateTime registeredAt
    ) {
        return Workshop.builder()
                .tradeName(tradeName.trim())
                .document(TextNormalizer.digits(document))
                .email(TextNormalizer.email(email))
                .phone(TextNormalizer.digits(phone))
                .address(address)
                .enabled(true)
                .createdAt(registeredAt)
                .updatedAt(registeredAt)
                .build();
    }
}
