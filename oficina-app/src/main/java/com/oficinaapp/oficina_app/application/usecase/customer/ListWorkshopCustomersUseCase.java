package com.oficinaapp.oficina_app.application.usecase.customer;

import com.oficinaapp.oficina_app.application.dto.customer.CustomerResponse;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import com.oficinaapp.oficina_app.domain.repository.WorkshopCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ListWorkshopCustomersUseCase {

    private final WorkshopCustomerRepository links;
    private final UserAccountRepository userAccounts;

    public ListWorkshopCustomersUseCase(WorkshopCustomerRepository links, UserAccountRepository userAccounts) {
        this.links = links;
        this.userAccounts = userAccounts;
    }

    public List<CustomerResponse> execute(Long workshopId) {
        List<Long> customerIds = links.findCustomerIdsOf(workshopId);

        if (customerIds.isEmpty()) {
            return List.of();
        }

        return userAccounts.findAllById(customerIds).stream()
                .sorted(Comparator.comparing(UserAccount::getName))
                .map(CustomerResponse::from)
                .toList();
    }
}
