package com.oficinaapp.oficina_app.domain.model;

import com.oficinaapp.oficina_app.domain.enums.EmployeeJobTitle;
import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.support.TextNormalizer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * A person who can sign in, whatever the role: customer, workshop owner or
 * workshop employee. Credentials live here and nowhere else, so a single login
 * flow serves every role.
 */
@Getter
@Builder(toBuilder = true)
public class UserAccount {

    private final Long id;
    private final String name;
    private final String email;
    private final String passwordHash;
    private final String phone;
    private final String document;
    private final UserRole role;

    /** Workshop the account belongs to; null for customers. */
    private final Long workshopId;

    /** What the person does in the workshop; null unless they are an employee. */
    private final EmployeeJobTitle jobTitle;

    private final Address address;
    private final boolean enabled;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * Builds a brand new account with its values already normalized. The
     * password arrives hashed — hashing is an infrastructure concern.
     */
    public static UserAccount register(
            String name,
            String email,
            String passwordHash,
            String phone,
            String document,
            UserRole role,
            Long workshopId,
            EmployeeJobTitle jobTitle,
            Address address,
            LocalDateTime registeredAt
    ) {
        return UserAccount.builder()
                .name(name.trim())
                .email(TextNormalizer.email(email))
                .passwordHash(passwordHash)
                .phone(TextNormalizer.digits(phone))
                .document(TextNormalizer.digits(document))
                .role(role)
                .workshopId(workshopId)
                .jobTitle(jobTitle)
                .address(address)
                .enabled(true)
                .createdAt(registeredAt)
                .updatedAt(registeredAt)
                .build();
    }
}
