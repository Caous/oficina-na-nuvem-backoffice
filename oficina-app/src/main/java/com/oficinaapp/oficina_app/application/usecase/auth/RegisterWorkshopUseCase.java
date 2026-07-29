package com.oficinaapp.oficina_app.application.usecase.auth;

import com.oficinaapp.oficina_app.application.dto.auth.AuthenticationResponse;
import com.oficinaapp.oficina_app.application.dto.auth.RegisterWorkshopRequest;
import com.oficinaapp.oficina_app.application.port.AccessTokenIssuer;
import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.exception.DocumentAlreadyRegisteredException;
import com.oficinaapp.oficina_app.domain.model.Address;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.model.Workshop;
import com.oficinaapp.oficina_app.domain.repository.WorkshopRepository;
import com.oficinaapp.oficina_app.domain.support.TextNormalizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Registers the workshop and the owner account that manages it. Both are
 * created in the same transaction: a workshop without an owner could not be
 * reached by anyone.
 */
@Service
public class RegisterWorkshopUseCase {

    private final WorkshopRepository workshops;
    private final UserAccountCreator accountCreator;
    private final AccessTokenIssuer tokenIssuer;
    private final Clock clock;

    public RegisterWorkshopUseCase(
            WorkshopRepository workshops,
            UserAccountCreator accountCreator,
            AccessTokenIssuer tokenIssuer,
            Clock clock
    ) {
        this.workshops = workshops;
        this.accountCreator = accountCreator;
        this.tokenIssuer = tokenIssuer;
        this.clock = clock;
    }

    @Transactional
    public AuthenticationResponse execute(RegisterWorkshopRequest request) {
        if (workshops.existsByDocument(TextNormalizer.digits(request.document()))) {
            throw new DocumentAlreadyRegisteredException();
        }

        Address address = request.address().toDomain();

        Workshop workshop = workshops.save(Workshop.register(
                request.tradeName(),
                request.document(),
                request.email(),
                request.phone(),
                address,
                LocalDateTime.now(clock)
        ));

        UserAccount owner = accountCreator.create(new NewAccount(
                request.tradeName(),
                request.email(),
                request.password(),
                request.phone(),
                request.document(),
                UserRole.WORKSHOP_OWNER,
                workshop.getId(),
                null,
                address
        ));

        return AuthenticationResponse.of(owner, tokenIssuer.issueFor(owner));
    }
}
