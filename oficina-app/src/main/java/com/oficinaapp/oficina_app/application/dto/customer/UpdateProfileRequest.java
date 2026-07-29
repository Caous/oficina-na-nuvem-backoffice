package com.oficinaapp.oficina_app.application.dto.customer;

import com.oficinaapp.oficina_app.application.dto.common.AddressPayload;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * E-mail and document are missing on purpose: one is the login, the other is
 * the identity. Changing either is its own flow, not a profile edit.
 */
public record UpdateProfileRequest(

        @NotBlank String name,
        @NotBlank String phone,
        @Valid AddressPayload address

) {
}
