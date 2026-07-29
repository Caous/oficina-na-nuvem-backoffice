package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.auth.AuthenticatedUser;
import com.oficinaapp.oficina_app.application.dto.auth.AuthenticationResponse;
import com.oficinaapp.oficina_app.application.dto.auth.RegisterCustomerRequest;
import com.oficinaapp.oficina_app.application.dto.auth.RegisterWorkshopRequest;
import com.oficinaapp.oficina_app.application.dto.auth.SignInRequest;
import com.oficinaapp.oficina_app.application.usecase.auth.GetAuthenticatedUserUseCase;
import com.oficinaapp.oficina_app.application.usecase.auth.RegisterCustomerUseCase;
import com.oficinaapp.oficina_app.application.usecase.auth.RegisterWorkshopUseCase;
import com.oficinaapp.oficina_app.application.usecase.auth.SignInUseCase;
import com.oficinaapp.oficina_app.infrastructure.security.AuthenticatedUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Self service only: a person opens their own account here. Hiring an employee
 * is an act of the workshop and lives in {@code EmployeeController}.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final SignInUseCase signInUseCase;
    private final RegisterCustomerUseCase registerCustomerUseCase;
    private final RegisterWorkshopUseCase registerWorkshopUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    public AuthController(
            SignInUseCase signInUseCase,
            RegisterCustomerUseCase registerCustomerUseCase,
            RegisterWorkshopUseCase registerWorkshopUseCase,
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase
    ) {
        this.signInUseCase = signInUseCase;
        this.registerCustomerUseCase = registerCustomerUseCase;
        this.registerWorkshopUseCase = registerWorkshopUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody SignInRequest request) {
        return signInUseCase.execute(request);
    }

    @PostMapping("/register/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse registerCustomer(@Valid @RequestBody RegisterCustomerRequest request) {
        return registerCustomerUseCase.execute(request);
    }

    @PostMapping("/register/workshop")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse registerWorkshop(@Valid @RequestBody RegisterWorkshopRequest request) {
        return registerWorkshopUseCase.execute(request);
    }

    @GetMapping("/me")
    public AuthenticatedUser me(@AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return getAuthenticatedUserUseCase.execute(principal.id());
    }
}
