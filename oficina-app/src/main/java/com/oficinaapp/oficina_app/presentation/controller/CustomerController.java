package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.customer.CustomerResponse;
import com.oficinaapp.oficina_app.application.dto.vehicle.VehicleResponse;
import com.oficinaapp.oficina_app.application.usecase.customer.FindCustomerByDocumentUseCase;
import com.oficinaapp.oficina_app.application.usecase.customer.ListCustomerVehiclesUseCase;
import com.oficinaapp.oficina_app.application.usecase.customer.ListWorkshopCustomersUseCase;
import com.oficinaapp.oficina_app.infrastructure.security.AuthenticatedUserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The customers of the signed in workshop. A workshop never lists the whole
 * customer base — it searches by document, and the lookup links the two.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final ListWorkshopCustomersUseCase listCustomers;
    private final FindCustomerByDocumentUseCase findByDocument;
    private final ListCustomerVehiclesUseCase listCustomerVehicles;

    public CustomerController(
            ListWorkshopCustomersUseCase listCustomers,
            FindCustomerByDocumentUseCase findByDocument,
            ListCustomerVehiclesUseCase listCustomerVehicles
    ) {
        this.listCustomers = listCustomers;
        this.findByDocument = findByDocument;
        this.listCustomerVehicles = listCustomerVehicles;
    }

    @GetMapping
    public List<CustomerResponse> list(@AuthenticationPrincipal AuthenticatedUserPrincipal caller) {
        return listCustomers.execute(caller.requireWorkshopId());
    }

    @GetMapping("/search")
    public CustomerResponse search(
            @RequestParam String document,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return findByDocument.execute(document, caller.requireWorkshopId());
    }

    @GetMapping("/{id}/vehicles")
    public List<VehicleResponse> vehicles(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return listCustomerVehicles.execute(id, caller.requireWorkshopId());
    }
}
