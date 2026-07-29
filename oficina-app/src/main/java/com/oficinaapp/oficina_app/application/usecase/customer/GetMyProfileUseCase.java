package com.oficinaapp.oficina_app.application.usecase.customer;

import com.oficinaapp.oficina_app.application.dto.customer.CustomerProfileResponse;
import com.oficinaapp.oficina_app.domain.exception.UserAccountNotFoundException;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class GetMyProfileUseCase {

    private final UserAccountRepository userAccounts;

    public GetMyProfileUseCase(UserAccountRepository userAccounts) {
        this.userAccounts = userAccounts;
    }

    public CustomerProfileResponse execute(Long accountId) {
        return userAccounts.findById(accountId)
                .map(CustomerProfileResponse::from)
                .orElseThrow(UserAccountNotFoundException::new);
    }
}
