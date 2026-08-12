package com.oficinaapp.oficina_app.application.dto.employee;

import com.oficinaapp.oficina_app.application.dto.common.AddressPayload;
import com.oficinaapp.oficina_app.domain.enums.EmployeeJobTitle;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * The password is not here on purpose: only the employee changes their own
 * password, never the owner.
 */
public record UpdateEmployeeRequest(

        @NotBlank String name,
        @NotBlank String phone,
        @NotNull EmployeeJobTitle jobTitle,
        @Valid AddressPayload address

) {
}
