package com.oficinaapp.oficina_app.application.dto.employee;

import com.oficinaapp.oficina_app.application.dto.common.AddressPayload;
import com.oficinaapp.oficina_app.domain.enums.EmployeeJobTitle;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateEmployeeRequest(

        @NotBlank String name,
        @NotBlank String document,
        @NotBlank @Email String email,
        @NotBlank String phone,
        @NotNull EmployeeJobTitle jobTitle,

        /**
         * Optional: when the owner does not choose one, the digits of the
         * employee's document become the initial password.
         */
        @Size(min = 8, max = 72) String password,

        @Valid AddressPayload address

) {
}
