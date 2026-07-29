package com.oficinaapp.oficina_app.application.usecase.auth;

import com.oficinaapp.oficina_app.domain.enums.EmployeeJobTitle;
import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.model.Address;

/**
 * Everything {@link UserAccountCreator} needs to open an account, whatever the
 * role that asked for it.
 */
public record NewAccount(

        String name,
        String email,
        String rawPassword,
        String phone,
        String document,
        UserRole role,
        Long workshopId,
        EmployeeJobTitle jobTitle,
        Address address

) {
}
