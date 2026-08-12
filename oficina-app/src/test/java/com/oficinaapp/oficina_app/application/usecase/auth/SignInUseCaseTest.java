package com.oficinaapp.oficina_app.application.usecase.auth;

import com.oficinaapp.oficina_app.application.dto.auth.AuthenticationResponse;
import com.oficinaapp.oficina_app.application.dto.auth.SignInRequest;
import com.oficinaapp.oficina_app.application.port.AccessToken;
import com.oficinaapp.oficina_app.application.port.AccessTokenIssuer;
import com.oficinaapp.oficina_app.application.port.PasswordHasher;
import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.exception.DisabledAccountException;
import com.oficinaapp.oficina_app.domain.exception.InvalidCredentialsException;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignInUseCaseTest {

    private static final String EMAIL = "owner@workshop.com";
    private static final String RAW_PASSWORD = "secret123";
    private static final String PASSWORD_HASH = "hashed-secret";

    @Mock
    private UserAccountRepository userAccounts;

    @Mock
    private PasswordHasher passwordHasher;

    @Mock
    private AccessTokenIssuer tokenIssuer;

    @InjectMocks
    private SignInUseCase signIn;

    private UserAccount account;

    @BeforeEach
    void setUp() {
        account = enabledAccount();
    }

    @Test
    @DisplayName("returns a token and the user when the credentials match")
    void signsInWithValidCredentials() {
        when(userAccounts.findByEmail(EMAIL)).thenReturn(Optional.of(account));
        when(passwordHasher.matches(RAW_PASSWORD, PASSWORD_HASH)).thenReturn(true);
        when(tokenIssuer.issueFor(account)).thenReturn(new AccessToken("token", Instant.parse("2026-01-01T00:00:00Z")));

        AuthenticationResponse response = signIn.execute(new SignInRequest(EMAIL, RAW_PASSWORD));

        assertThat(response.accessToken()).isEqualTo("token");
        assertThat(response.user().email()).isEqualTo(EMAIL);
        assertThat(response.user().role()).isEqualTo(UserRole.WORKSHOP_OWNER);
        assertThat(response.user().workshopId()).isEqualTo(7L);
    }

    @Test
    @DisplayName("finds the account even when the e-mail was typed with different casing")
    void normalizesTheEmailBeforeSearching() {
        when(userAccounts.findByEmail(EMAIL)).thenReturn(Optional.of(account));
        when(passwordHasher.matches(RAW_PASSWORD, PASSWORD_HASH)).thenReturn(true);
        when(tokenIssuer.issueFor(account)).thenReturn(new AccessToken("token", Instant.parse("2026-01-01T00:00:00Z")));

        AuthenticationResponse response = signIn.execute(new SignInRequest("  Owner@Workshop.com ", RAW_PASSWORD));

        assertThat(response.accessToken()).isEqualTo("token");
    }

    @Test
    @DisplayName("rejects an unknown e-mail without saying the account does not exist")
    void rejectsUnknownEmail() {
        when(userAccounts.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> signIn.execute(new SignInRequest(EMAIL, RAW_PASSWORD)))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Invalid e-mail or password.");
    }

    @Test
    @DisplayName("rejects a wrong password with the very same message")
    void rejectsWrongPassword() {
        when(userAccounts.findByEmail(EMAIL)).thenReturn(Optional.of(account));
        when(passwordHasher.matches("wrong", PASSWORD_HASH)).thenReturn(false);

        assertThatThrownBy(() -> signIn.execute(new SignInRequest(EMAIL, "wrong")))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Invalid e-mail or password.");
    }

    @Test
    @DisplayName("refuses a disabled account even with the right password")
    void refusesDisabledAccount() {
        UserAccount disabled = account.toBuilder().enabled(false).build();

        when(userAccounts.findByEmail(EMAIL)).thenReturn(Optional.of(disabled));
        when(passwordHasher.matches(RAW_PASSWORD, PASSWORD_HASH)).thenReturn(true);

        assertThatThrownBy(() -> signIn.execute(new SignInRequest(EMAIL, RAW_PASSWORD)))
                .isInstanceOf(DisabledAccountException.class);
    }

    @Test
    @DisplayName("never issues a token when authentication fails")
    void doesNotIssueTokenOnFailure() {
        when(userAccounts.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> signIn.execute(new SignInRequest(EMAIL, RAW_PASSWORD)))
                .isInstanceOf(InvalidCredentialsException.class);

        verify(tokenIssuer, never()).issueFor(any());
    }

    private UserAccount enabledAccount() {
        return UserAccount.builder()
                .id(1L)
                .name("Oficina do Ze")
                .email(EMAIL)
                .passwordHash(PASSWORD_HASH)
                .phone("11999998888")
                .document("12345678000199")
                .role(UserRole.WORKSHOP_OWNER)
                .workshopId(7L)
                .enabled(true)
                .createdAt(LocalDateTime.parse("2026-01-01T10:00:00"))
                .updatedAt(LocalDateTime.parse("2026-01-01T10:00:00"))
                .build();
    }
}
