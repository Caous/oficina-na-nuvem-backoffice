package com.oficinaapp.oficina_app.application.usecase.employee;

import com.oficinaapp.oficina_app.application.dto.employee.EmployeeResponse;
import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListEmployeesUseCase {

    private final UserAccountRepository userAccounts;

    public ListEmployeesUseCase(UserAccountRepository userAccounts) {
        this.userAccounts = userAccounts;
    }

    /** Dismissed employees keep their history but leave the list. */
    public List<EmployeeResponse> execute(Long workshopId) {
        return userAccounts.findByWorkshopIdAndRole(workshopId, UserRole.WORKSHOP_EMPLOYEE).stream()
                .filter(UserAccount::isEnabled)
                .map(EmployeeResponse::from)
                .toList();
    }
}
