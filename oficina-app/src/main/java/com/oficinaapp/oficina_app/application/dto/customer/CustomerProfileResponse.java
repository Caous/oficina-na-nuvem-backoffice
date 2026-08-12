package com.oficinaapp.oficina_app.application.dto.customer;

import com.oficinaapp.oficina_app.application.dto.common.AddressPayload;
import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.model.UserAccount;

public record CustomerProfileResponse(

        Long id,
        String name,
        String email,
        String phone,
        String document,
        UserRole role,
        AddressPayload address

) {

    public static CustomerProfileResponse from(UserAccount account) {
        return new CustomerProfileResponse(
                account.getId(),
                account.getName(),
                account.getEmail(),
                account.getPhone(),
                account.getDocument(),
                account.getRole(),
                AddressPayload.from(account.getAddress())
        );
    }
}
