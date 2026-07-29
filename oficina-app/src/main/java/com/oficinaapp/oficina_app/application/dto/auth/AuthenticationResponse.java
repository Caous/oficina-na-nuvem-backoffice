package com.oficinaapp.oficina_app.application.dto.auth;

import com.oficinaapp.oficina_app.application.port.AccessToken;
import com.oficinaapp.oficina_app.domain.model.UserAccount;

import java.time.Instant;

public record AuthenticationResponse(

        String accessToken,
        Instant expiresAt,
        AuthenticatedUser user

) {

    public static AuthenticationResponse of(UserAccount account, AccessToken token) {
        return new AuthenticationResponse(
                token.value(),
                token.expiresAt(),
                AuthenticatedUser.from(account)
        );
    }
}
