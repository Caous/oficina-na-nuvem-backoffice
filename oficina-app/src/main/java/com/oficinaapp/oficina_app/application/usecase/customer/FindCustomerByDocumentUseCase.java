package com.oficinaapp.oficina_app.application.usecase.customer;

import com.oficinaapp.oficina_app.application.dto.customer.CustomerResponse;
import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import com.oficinaapp.oficina_app.domain.repository.WorkshopCustomerRepository;
import com.oficinaapp.oficina_app.domain.support.TextNormalizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * How a customer joins a workshop's list: someone types the document at the
 * counter. Finding them links the two, so the next visit needs no search.
 */
@Service
public class FindCustomerByDocumentUseCase {

    private final UserAccountRepository userAccounts;
    private final WorkshopCustomerRepository links;

    public FindCustomerByDocumentUseCase(UserAccountRepository userAccounts, WorkshopCustomerRepository links) {
        this.userAccounts = userAccounts;
        this.links = links;
    }

    @Transactional
    public CustomerResponse execute(String document, Long workshopId) {
        UserAccount customer = userAccounts.findByDocument(TextNormalizer.digits(document))
                .filter(account -> account.getRole() == UserRole.CUSTOMER)
                .filter(UserAccount::isEnabled)
                .orElseThrow(() -> new ResourceNotFoundException("Customer"));

        links.link(workshopId, customer.getId());

        return CustomerResponse.from(customer);
    }
}
