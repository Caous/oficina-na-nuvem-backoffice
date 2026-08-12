package com.oficinaapp.oficina_app.application.usecase.auth;

import com.oficinaapp.oficina_app.application.dto.auth.AuthenticationResponse;
import com.oficinaapp.oficina_app.application.dto.auth.RegisterCustomerRequest;
import com.oficinaapp.oficina_app.application.port.AccessTokenIssuer;
import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Opens a customer account and signs the person in, so the app never asks for
 * the password twice in a row.
 */
@Service
public class RegisterCustomerUseCase {

    private final UserAccountCreator accountCreator;
    private final AccessTokenIssuer tokenIssuer;

    public RegisterCustomerUseCase(UserAccountCreator accountCreator, AccessTokenIssuer tokenIssuer) {
        this.accountCreator = accountCreator;
        this.tokenIssuer = tokenIssuer;
    }

    @Transactional
    public AuthenticationResponse execute(RegisterCustomerRequest request) {
        UserAccount customer = accountCreator.create(new NewAccount(
                request.name(),
                request.email(),
                request.password(),
                request.phone(),
                request.document(),
                UserRole.CUSTOMER,
                null,
                null,
                request.address().toDomain()
        ));

        return AuthenticationResponse.of(customer, tokenIssuer.issueFor(customer));
    }
}
