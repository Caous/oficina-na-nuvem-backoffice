package com.oficinaapp.oficina_app.application.dto.auth;

import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.model.UserAccount;

/**
 * The signed in person as the mobile app needs to see them. It never carries
 * the password hash.
 */
public record AuthenticatedUser(

        Long id,
        String name,
        String email,
        UserRole role,
        Long workshopId

) {

    public static AuthenticatedUser from(UserAccount account) {
        return new AuthenticatedUser(
                account.getId(),
                account.getName(),
                account.getEmail(),
                account.getRole(),
                account.getWorkshopId()
        );
    }
}
