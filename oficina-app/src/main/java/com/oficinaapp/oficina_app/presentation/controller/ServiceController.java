package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.catalog.WorkshopServiceRequest;
import com.oficinaapp.oficina_app.application.dto.catalog.WorkshopServiceResponse;
import com.oficinaapp.oficina_app.application.usecase.catalog.DeleteWorkshopServiceUseCase;
import com.oficinaapp.oficina_app.application.usecase.catalog.ListWorkshopServicesUseCase;
import com.oficinaapp.oficina_app.application.usecase.catalog.SaveWorkshopServiceUseCase;
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
@RequestMapping("/api/services")
public class ServiceController {

    private final ListWorkshopServicesUseCase listServices;
    private final SaveWorkshopServiceUseCase saveService;
    private final DeleteWorkshopServiceUseCase deleteService;

    public ServiceController(
            ListWorkshopServicesUseCase listServices,
            SaveWorkshopServiceUseCase saveService,
            DeleteWorkshopServiceUseCase deleteService
    ) {
        this.listServices = listServices;
        this.saveService = saveService;
        this.deleteService = deleteService;
    }

    @GetMapping
    public List<WorkshopServiceResponse> list(@AuthenticationPrincipal AuthenticatedUserPrincipal caller) {
        return listServices.execute(caller.requireWorkshopId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkshopServiceResponse create(
            @Valid @RequestBody WorkshopServiceRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return saveService.execute(null, request, caller.requireWorkshopId());
    }

    @PutMapping("/{id}")
    public WorkshopServiceResponse update(
            @PathVariable Long id,
            @Valid @RequestBody WorkshopServiceRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return saveService.execute(id, request, caller.requireWorkshopId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        deleteService.execute(id, caller.requireWorkshopId());
    }
}
