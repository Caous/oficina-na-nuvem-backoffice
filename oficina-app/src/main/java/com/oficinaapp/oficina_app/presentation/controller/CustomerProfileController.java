package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.customer.CustomerProfileResponse;
import com.oficinaapp.oficina_app.application.dto.customer.UpdateProfileRequest;
import com.oficinaapp.oficina_app.application.usecase.customer.GetMyProfileUseCase;
import com.oficinaapp.oficina_app.application.usecase.customer.UpdateMyProfileUseCase;
import com.oficinaapp.oficina_app.infrastructure.security.AuthenticatedUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me/profile")
public class CustomerProfileController {

    private final GetMyProfileUseCase getMyProfile;
    private final UpdateMyProfileUseCase updateMyProfile;

    public CustomerProfileController(GetMyProfileUseCase getMyProfile, UpdateMyProfileUseCase updateMyProfile) {
        this.getMyProfile = getMyProfile;
        this.updateMyProfile = updateMyProfile;
    }

    @GetMapping
    public CustomerProfileResponse get(@AuthenticationPrincipal AuthenticatedUserPrincipal caller) {
        return getMyProfile.execute(caller.id());
    }

    @PutMapping
    public CustomerProfileResponse update(
            @Valid @RequestBody UpdateProfileRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return updateMyProfile.execute(caller.id(), request);
    }
}
