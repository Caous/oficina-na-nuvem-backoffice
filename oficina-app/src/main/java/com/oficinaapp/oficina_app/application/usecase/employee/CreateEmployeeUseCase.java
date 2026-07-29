package com.oficinaapp.oficina_app.application.usecase.employee;

import com.oficinaapp.oficina_app.application.dto.employee.CreateEmployeeRequest;
import com.oficinaapp.oficina_app.application.dto.employee.EmployeeResponse;
import com.oficinaapp.oficina_app.application.usecase.auth.NewAccount;
import com.oficinaapp.oficina_app.application.usecase.auth.UserAccountCreator;
import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.model.Address;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Hires someone into the caller's workshop. The workshop comes from the
 * owner's token, never from the request, so nobody can staff a workshop that
 * is not theirs.
 */
@Service
public class CreateEmployeeUseCase {

    private final UserAccountCreator accountCreator;

    public CreateEmployeeUseCase(UserAccountCreator accountCreator) {
        this.accountCreator = accountCreator;
    }

    @Transactional
    public EmployeeResponse execute(CreateEmployeeRequest request, Long workshopId) {
        Address address = request.address() == null ? null : request.address().toDomain();

        return EmployeeResponse.from(accountCreator.create(new NewAccount(
                request.name(),
                request.email(),
                request.password(),
                request.phone(),
                request.document(),
                UserRole.WORKSHOP_EMPLOYEE,
                workshopId,
                request.jobTitle(),
                address
        )));
    }
}
