package com.oficinaapp.oficina_app.application.dto.employee;

import com.oficinaapp.oficina_app.application.dto.common.AddressPayload;
import com.oficinaapp.oficina_app.domain.enums.EmployeeJobTitle;
import com.oficinaapp.oficina_app.domain.model.UserAccount;

public record EmployeeResponse(

        Long id,
        String name,
        String document,
        String email,
        String phone,
        EmployeeJobTitle jobTitle,
        AddressPayload address

) {

    public static EmployeeResponse from(UserAccount account) {
        return new EmployeeResponse(
                account.getId(),
                account.getName(),
                account.getDocument(),
                account.getEmail(),
                account.getPhone(),
                account.getJobTitle(),
                AddressPayload.from(account.getAddress())
        );
    }
}
