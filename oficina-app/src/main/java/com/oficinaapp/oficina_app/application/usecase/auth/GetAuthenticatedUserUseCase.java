package com.oficinaapp.oficina_app.application.usecase.auth;

import com.oficinaapp.oficina_app.application.dto.auth.AuthenticatedUser;
import com.oficinaapp.oficina_app.domain.exception.UserAccountNotFoundException;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

/**
 * Reads the account behind a valid token, so the app always shows current data
 * instead of whatever the token carried when it was issued.
 */
@Service
public class GetAuthenticatedUserUseCase {

    private final UserAccountRepository userAccounts;

    public GetAuthenticatedUserUseCase(UserAccountRepository userAccounts) {
        this.userAccounts = userAccounts;
    }

    public AuthenticatedUser execute(Long accountId) {
        return userAccounts.findById(accountId)
                .map(AuthenticatedUser::from)
                .orElseThrow(UserAccountNotFoundException::new);
    }
}
