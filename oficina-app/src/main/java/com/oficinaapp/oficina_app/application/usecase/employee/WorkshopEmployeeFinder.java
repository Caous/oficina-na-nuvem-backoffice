package com.oficinaapp.oficina_app.application.usecase.employee;

import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import org.springframework.stereotype.Component;

/**
 * Loads an employee only if they really belong to the caller's workshop. An
 * employee of another workshop reads as "not found", never as "forbidden".
 */
@Component
public class WorkshopEmployeeFinder {

    private final UserAccountRepository userAccounts;

    public WorkshopEmployeeFinder(UserAccountRepository userAccounts) {
        this.userAccounts = userAccounts;
    }

    public UserAccount findInWorkshop(Long employeeId, Long workshopId) {
        return userAccounts.findById(employeeId)
                .filter(account -> account.getRole() == UserRole.WORKSHOP_EMPLOYEE)
                .filter(account -> workshopId.equals(account.getWorkshopId()))
                .filter(UserAccount::isEnabled)
                .orElseThrow(() -> new ResourceNotFoundException("Employee"));
    }
}
