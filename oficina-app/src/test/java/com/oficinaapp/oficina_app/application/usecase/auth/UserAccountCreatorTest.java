package com.oficinaapp.oficina_app.application.usecase.auth;

import com.oficinaapp.oficina_app.application.port.PasswordHasher;
import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.exception.DocumentAlreadyRegisteredException;
import com.oficinaapp.oficina_app.domain.exception.EmailAlreadyRegisteredException;
import com.oficinaapp.oficina_app.domain.model.Address;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAccountCreatorTest {

    private static final Clock FIXED_CLOCK =
            Clock.fixed(Instant.parse("2026-03-10T12:00:00Z"), ZoneOffset.UTC);

    @Mock
    private UserAccountRepository userAccounts;

    @Mock
    private PasswordHasher passwordHasher;

    @Test
    @DisplayName("stores the account with normalized values and a hashed password")
    void storesNormalizedAccount() {
        UserAccountCreator creator = new UserAccountCreator(userAccounts, passwordHasher, FIXED_CLOCK);

        when(userAccounts.existsByEmail("joao@email.com")).thenReturn(false);
        when(userAccounts.existsByDocument("12345678900")).thenReturn(false);
        when(passwordHasher.hash("secret123")).thenReturn("hashed");
        when(userAccounts.save(any(UserAccount.class))).thenAnswer(invocation -> invocation.getArgument(0));

        creator.create(newAccount("  Joao Pereira ", " Joao@Email.com ", "123.456.789-00", "(11) 99999-8888"));

        ArgumentCaptor<UserAccount> saved = ArgumentCaptor.forClass(UserAccount.class);
        verify(userAccounts).save(saved.capture());

        UserAccount account = saved.getValue();
        assertThat(account.getName()).isEqualTo("Joao Pereira");
        assertThat(account.getEmail()).isEqualTo("joao@email.com");
        assertThat(account.getDocument()).isEqualTo("12345678900");
        assertThat(account.getPhone()).isEqualTo("11999998888");
        assertThat(account.getPasswordHash()).isEqualTo("hashed");
        assertThat(account.isEnabled()).isTrue();
        assertThat(account.getCreatedAt()).isEqualTo(LocalDateTime.parse("2026-03-10T12:00:00"));
    }

    @Test
    @DisplayName("refuses an e-mail that already has an account")
    void refusesDuplicatedEmail() {
        UserAccountCreator creator = new UserAccountCreator(userAccounts, passwordHasher, FIXED_CLOCK);

        when(userAccounts.existsByEmail("joao@email.com")).thenReturn(true);

        assertThatThrownBy(() -> creator.create(newAccount("Joao", "joao@email.com", "12345678900", "11999998888")))
                .isInstanceOf(EmailAlreadyRegisteredException.class);

        verify(userAccounts, never()).save(any());
    }

    @Test
    @DisplayName("refuses a document that already has an account")
    void refusesDuplicatedDocument() {
        UserAccountCreator creator = new UserAccountCreator(userAccounts, passwordHasher, FIXED_CLOCK);

        when(userAccounts.existsByEmail("joao@email.com")).thenReturn(false);
        when(userAccounts.existsByDocument("12345678900")).thenReturn(true);

        assertThatThrownBy(() -> creator.create(newAccount("Joao", "joao@email.com", "123.456.789-00", "11999998888")))
                .isInstanceOf(DocumentAlreadyRegisteredException.class);

        verify(userAccounts, never()).save(any());
    }

    private NewAccount newAccount(String name, String email, String document, String phone) {
        return new NewAccount(
                name,
                email,
                "secret123",
                phone,
                document,
                UserRole.CUSTOMER,
                null,
                null,
                new Address("01001000", "Praca da Se", "10", "", "Se", "Sao Paulo", "SP")
        );
    }
}
