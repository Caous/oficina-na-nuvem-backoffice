package com.oficinaapp.oficina_app.application.usecase.auth;

import com.oficinaapp.oficina_app.application.port.PasswordHasher;
import com.oficinaapp.oficina_app.domain.exception.DocumentAlreadyRegisteredException;
import com.oficinaapp.oficina_app.domain.exception.EmailAlreadyRegisteredException;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import com.oficinaapp.oficina_app.domain.support.TextNormalizer;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * The single place that turns a registration request into a stored account.
 * The three registration use cases differ in what they gather, not in how an
 * account is opened.
 */
@Component
public class UserAccountCreator {

    private final UserAccountRepository userAccounts;
    private final PasswordHasher passwordHasher;
    private final Clock clock;

    public UserAccountCreator(UserAccountRepository userAccounts, PasswordHasher passwordHasher, Clock clock) {
        this.userAccounts = userAccounts;
        this.passwordHasher = passwordHasher;
        this.clock = clock;
    }

    public UserAccount create(NewAccount newAccount) {
        rejectDuplicates(newAccount);

        UserAccount account = UserAccount.register(
                newAccount.name(),
                newAccount.email(),
                passwordHasher.hash(newAccount.rawPassword()),
                newAccount.phone(),
                newAccount.document(),
                newAccount.role(),
                newAccount.workshopId(),
                newAccount.jobTitle(),
                newAccount.address(),
                LocalDateTime.now(clock)
        );

        return userAccounts.save(account);
    }

    private void rejectDuplicates(NewAccount newAccount) {
        if (userAccounts.existsByEmail(TextNormalizer.email(newAccount.email()))) {
            throw new EmailAlreadyRegisteredException();
        }

        if (userAccounts.existsByDocument(TextNormalizer.digits(newAccount.document()))) {
            throw new DocumentAlreadyRegisteredException();
        }
    }
}
