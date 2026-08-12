package com.oficinaapp.oficina_app.application.usecase.customer;

import com.oficinaapp.oficina_app.application.dto.customer.CustomerProfileResponse;
import com.oficinaapp.oficina_app.application.dto.customer.UpdateProfileRequest;
import com.oficinaapp.oficina_app.domain.exception.UserAccountNotFoundException;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import com.oficinaapp.oficina_app.domain.support.TextNormalizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class UpdateMyProfileUseCase {

    private final UserAccountRepository userAccounts;
    private final Clock clock;

    public UpdateMyProfileUseCase(UserAccountRepository userAccounts, Clock clock) {
        this.userAccounts = userAccounts;
        this.clock = clock;
    }

    @Transactional
    public CustomerProfileResponse execute(Long accountId, UpdateProfileRequest request) {
        UserAccount account = userAccounts.findById(accountId)
                .orElseThrow(UserAccountNotFoundException::new);

        UserAccount updated = account.toBuilder()
                .name(request.name().trim())
                .phone(TextNormalizer.digits(request.phone()))
                .address(request.address() == null ? account.getAddress() : request.address().toDomain())
                .updatedAt(LocalDateTime.now(clock))
                .build();

        return CustomerProfileResponse.from(userAccounts.save(updated));
    }
}
