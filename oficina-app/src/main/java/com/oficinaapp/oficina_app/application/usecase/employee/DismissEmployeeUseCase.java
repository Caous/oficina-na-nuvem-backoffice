package com.oficinaapp.oficina_app.application.usecase.employee;

import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Disables the account instead of deleting the row: service orders already
 * point at this person, and history must stay readable.
 */
@Service
public class DismissEmployeeUseCase {

    private final WorkshopEmployeeFinder employeeFinder;
    private final UserAccountRepository userAccounts;
    private final Clock clock;

    public DismissEmployeeUseCase(
            WorkshopEmployeeFinder employeeFinder,
            UserAccountRepository userAccounts,
            Clock clock
    ) {
        this.employeeFinder = employeeFinder;
        this.userAccounts = userAccounts;
        this.clock = clock;
    }

    @Transactional
    public void execute(Long employeeId, Long workshopId) {
        UserAccount employee = employeeFinder.findInWorkshop(employeeId, workshopId);

        userAccounts.save(employee.toBuilder()
                .enabled(false)
                .updatedAt(LocalDateTime.now(clock))
                .build());
    }
}
