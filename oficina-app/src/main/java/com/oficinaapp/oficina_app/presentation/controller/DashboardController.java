package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.dashboard.DashboardSummaryResponse;
import com.oficinaapp.oficina_app.application.usecase.dashboard.GetDashboardSummaryUseCase;
import com.oficinaapp.oficina_app.infrastructure.security.AuthenticatedUserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final GetDashboardSummaryUseCase getSummary;

    public DashboardController(GetDashboardSummaryUseCase getSummary) {
        this.getSummary = getSummary;
    }

    @GetMapping("/summary")
    public DashboardSummaryResponse summary(@AuthenticationPrincipal AuthenticatedUserPrincipal caller) {
        return getSummary.execute(caller.requireWorkshopId());
    }
}
