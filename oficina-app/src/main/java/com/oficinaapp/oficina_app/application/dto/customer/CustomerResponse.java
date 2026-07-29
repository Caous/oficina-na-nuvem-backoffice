package com.oficinaapp.oficina_app.application.dto.customer;

import com.oficinaapp.oficina_app.domain.model.UserAccount;

/** The customer as the workshop sees them: enough to open a service order. */
public record CustomerResponse(

        Long id,
        String name,
        String document,
        String phone,
        String email

) {

    public static CustomerResponse from(UserAccount account) {
        return new CustomerResponse(
                account.getId(),
                account.getName(),
                account.getDocument(),
                account.getPhone(),
                account.getEmail()
        );
    }
}
