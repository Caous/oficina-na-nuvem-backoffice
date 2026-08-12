package com.oficinaapp.oficina_app.application.usecase.employee;

import com.oficinaapp.oficina_app.application.dto.employee.EmployeeResponse;
import com.oficinaapp.oficina_app.application.dto.employee.UpdateEmployeeRequest;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import com.oficinaapp.oficina_app.domain.support.TextNormalizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class UpdateEmployeeUseCase {

    private final WorkshopEmployeeFinder employeeFinder;
    private final UserAccountRepository userAccounts;
    private final Clock clock;

    public UpdateEmployeeUseCase(
            WorkshopEmployeeFinder employeeFinder,
            UserAccountRepository userAccounts,
            Clock clock
    ) {
        this.employeeFinder = employeeFinder;
        this.userAccounts = userAccounts;
        this.clock = clock;
    }

    @Transactional
    public EmployeeResponse execute(Long employeeId, UpdateEmployeeRequest request, Long workshopId) {
        UserAccount employee = employeeFinder.findInWorkshop(employeeId, workshopId);

        UserAccount updated = employee.toBuilder()
                .name(request.name().trim())
                .phone(TextNormalizer.digits(request.phone()))
                .jobTitle(request.jobTitle())
                .address(request.address() == null ? employee.getAddress() : request.address().toDomain())
                .updatedAt(LocalDateTime.now(clock))
                .build();

        return EmployeeResponse.from(userAccounts.save(updated));
    }
}
