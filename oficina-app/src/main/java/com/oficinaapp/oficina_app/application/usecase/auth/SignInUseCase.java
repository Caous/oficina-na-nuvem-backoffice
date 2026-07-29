package com.oficinaapp.oficina_app.application.usecase.auth;

import com.oficinaapp.oficina_app.application.dto.auth.AuthenticationResponse;
import com.oficinaapp.oficina_app.application.dto.auth.SignInRequest;
import com.oficinaapp.oficina_app.application.port.AccessTokenIssuer;
import com.oficinaapp.oficina_app.application.port.PasswordHasher;
import com.oficinaapp.oficina_app.domain.exception.DisabledAccountException;
import com.oficinaapp.oficina_app.domain.exception.InvalidCredentialsException;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import com.oficinaapp.oficina_app.domain.support.TextNormalizer;
import org.springframework.stereotype.Service;

@Service
public class SignInUseCase {

    private final UserAccountRepository userAccounts;
    private final PasswordHasher passwordHasher;
    private final AccessTokenIssuer tokenIssuer;

    public SignInUseCase(
            UserAccountRepository userAccounts,
            PasswordHasher passwordHasher,
            AccessTokenIssuer tokenIssuer
    ) {
        this.userAccounts = userAccounts;
        this.passwordHasher = passwordHasher;
        this.tokenIssuer = tokenIssuer;
    }

    public AuthenticationResponse execute(SignInRequest request) {
        UserAccount account = userAccounts.findByEmail(TextNormalizer.email(request.email()))
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordHasher.matches(request.password(), account.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        if (!account.isEnabled()) {
            throw new DisabledAccountException();
        }

        return AuthenticationResponse.of(account, tokenIssuer.issueFor(account));
    }
}
