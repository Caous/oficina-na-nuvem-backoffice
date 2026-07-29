package com.oficinaapp.oficina_app.application.usecase.auth;

import com.oficinaapp.oficina_app.application.dto.auth.AuthenticationResponse;
import com.oficinaapp.oficina_app.application.dto.auth.RegisterWorkshopRequest;
import com.oficinaapp.oficina_app.application.dto.common.AddressPayload;
import com.oficinaapp.oficina_app.application.port.AccessToken;
import com.oficinaapp.oficina_app.application.port.AccessTokenIssuer;
import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.exception.DocumentAlreadyRegisteredException;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.model.Workshop;
import com.oficinaapp.oficina_app.domain.repository.WorkshopRepository;
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
class RegisterWorkshopUseCaseTest {

    private static final Clock FIXED_CLOCK =
            Clock.fixed(Instant.parse("2026-03-10T12:00:00Z"), ZoneOffset.UTC);

    @Mock
    private WorkshopRepository workshops;

    @Mock
    private UserAccountCreator accountCreator;

    @Mock
    private AccessTokenIssuer tokenIssuer;

    @Test
    @DisplayName("creates the workshop and links the owner account to it")
    void createsWorkshopAndOwner() {
        RegisterWorkshopUseCase useCase =
                new RegisterWorkshopUseCase(workshops, accountCreator, tokenIssuer, FIXED_CLOCK);

        when(workshops.existsByDocument("12345678000199")).thenReturn(false);
        when(workshops.save(any(Workshop.class))).thenAnswer(invocation -> {
            Workshop workshop = invocation.getArgument(0);
            return workshop.toBuilder().id(7L).build();
        });
        when(accountCreator.create(any(NewAccount.class))).thenAnswer(invocation -> {
            NewAccount newAccount = invocation.getArgument(0);
            return UserAccount.builder()
                    .id(1L)
                    .name(newAccount.name())
                    .email(newAccount.email())
                    .role(newAccount.role())
                    .workshopId(newAccount.workshopId())
                    .enabled(true)
                    .createdAt(LocalDateTime.now(FIXED_CLOCK))
                    .updatedAt(LocalDateTime.now(FIXED_CLOCK))
                    .build();
        });
        when(tokenIssuer.issueFor(any(UserAccount.class)))
                .thenReturn(new AccessToken("token", Instant.parse("2026-03-10T20:00:00Z")));

        AuthenticationResponse response = useCase.execute(request());

        ArgumentCaptor<NewAccount> owner = ArgumentCaptor.forClass(NewAccount.class);
        verify(accountCreator).create(owner.capture());

        assertThat(owner.getValue().role()).isEqualTo(UserRole.WORKSHOP_OWNER);
        assertThat(owner.getValue().workshopId()).isEqualTo(7L);
        assertThat(response.user().workshopId()).isEqualTo(7L);
        assertThat(response.accessToken()).isEqualTo("token");
    }

    @Test
    @DisplayName("refuses a workshop whose document is already registered")
    void refusesDuplicatedWorkshopDocument() {
        RegisterWorkshopUseCase useCase =
                new RegisterWorkshopUseCase(workshops, accountCreator, tokenIssuer, FIXED_CLOCK);

        when(workshops.existsByDocument("12345678000199")).thenReturn(true);

        assertThatThrownBy(() -> useCase.execute(request()))
                .isInstanceOf(DocumentAlreadyRegisteredException.class);

        verify(workshops, never()).save(any());
        verify(accountCreator, never()).create(any());
    }

    private RegisterWorkshopRequest request() {
        return new RegisterWorkshopRequest(
                "Oficina do Ze",
                "12.345.678/0001-99",
                "contato@oficinadoze.com",
                "(11) 3333-4444",
                "secret123",
                new AddressPayload("01001000", "Praca da Se", "10", "", "Se", "Sao Paulo", "SP")
        );
    }
}
