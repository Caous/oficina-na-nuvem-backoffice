package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.employee.CreateEmployeeRequest;
import com.oficinaapp.oficina_app.application.dto.employee.EmployeeResponse;
import com.oficinaapp.oficina_app.application.dto.employee.UpdateEmployeeRequest;
import com.oficinaapp.oficina_app.application.usecase.employee.CreateEmployeeUseCase;
import com.oficinaapp.oficina_app.application.usecase.employee.DismissEmployeeUseCase;
import com.oficinaapp.oficina_app.application.usecase.employee.ListEmployeesUseCase;
import com.oficinaapp.oficina_app.application.usecase.employee.UpdateEmployeeUseCase;
import com.oficinaapp.oficina_app.infrastructure.security.AuthenticatedUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final ListEmployeesUseCase listEmployees;
    private final CreateEmployeeUseCase createEmployee;
    private final UpdateEmployeeUseCase updateEmployee;
    private final DismissEmployeeUseCase dismissEmployee;

    public EmployeeController(
            ListEmployeesUseCase listEmployees,
            CreateEmployeeUseCase createEmployee,
            UpdateEmployeeUseCase updateEmployee,
            DismissEmployeeUseCase dismissEmployee
    ) {
        this.listEmployees = listEmployees;
        this.createEmployee = createEmployee;
        this.updateEmployee = updateEmployee;
        this.dismissEmployee = dismissEmployee;
    }

    @GetMapping
    public List<EmployeeResponse> list(@AuthenticationPrincipal AuthenticatedUserPrincipal caller) {
        return listEmployees.execute(caller.requireWorkshopId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse create(
            @Valid @RequestBody CreateEmployeeRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return createEmployee.execute(request, caller.requireWorkshopId());
    }

    @PutMapping("/{id}")
    public EmployeeResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return updateEmployee.execute(id, request, caller.requireWorkshopId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dismiss(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        dismissEmployee.execute(id, caller.requireWorkshopId());
    }
}
