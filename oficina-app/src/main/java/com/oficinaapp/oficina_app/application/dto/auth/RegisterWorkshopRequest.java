package com.oficinaapp.oficina_app.application.dto.auth;

import com.oficinaapp.oficina_app.application.dto.common.AddressPayload;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterWorkshopRequest(

        @NotBlank String tradeName,
        @NotBlank String document,
        @NotBlank @Email String email,
        @NotBlank String phone,
        @NotBlank @Size(min = 8, max = 72) String password,
        @NotNull @Valid AddressPayload address

) {
}
