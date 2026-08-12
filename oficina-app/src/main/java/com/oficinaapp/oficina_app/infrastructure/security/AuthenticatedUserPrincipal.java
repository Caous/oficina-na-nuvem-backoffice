package com.oficinaapp.oficina_app.infrastructure.security;

import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.exception.OperationNotAllowedException;

/**
 * What a valid token proves about the caller. Controllers read it through
 * {@code @AuthenticationPrincipal}.
 */
public record AuthenticatedUserPrincipal(

        Long id,
        String email,
        UserRole role,
        Long workshopId

) {

    /**
     * Workshop endpoints always scope their data by this value, so an account
     * without a workshop must never reach them.
     */
    public Long requireWorkshopId() {
        if (workshopId == null) {
            throw new OperationNotAllowedException("This account is not linked to a workshop.");
        }

        return workshopId;
    }
}
