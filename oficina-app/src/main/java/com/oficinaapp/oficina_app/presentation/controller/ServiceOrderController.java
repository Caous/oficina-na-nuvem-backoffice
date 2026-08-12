package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.serviceorder.ChangeStatusRequest;
import com.oficinaapp.oficina_app.application.dto.serviceorder.OpenServiceOrderRequest;
import com.oficinaapp.oficina_app.application.dto.serviceorder.ServiceOrderResponse;
import com.oficinaapp.oficina_app.application.usecase.serviceorder.ChangeServiceOrderStatusUseCase;
import com.oficinaapp.oficina_app.application.usecase.serviceorder.GetServiceOrderUseCase;
import com.oficinaapp.oficina_app.application.usecase.serviceorder.ListServiceOrdersUseCase;
import com.oficinaapp.oficina_app.application.usecase.serviceorder.OpenServiceOrderUseCase;
import com.oficinaapp.oficina_app.domain.enums.ServiceOrderStatus;
import com.oficinaapp.oficina_app.infrastructure.security.AuthenticatedUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/service-orders")
public class ServiceOrderController {

    private final ListServiceOrdersUseCase listOrders;
    private final OpenServiceOrderUseCase openOrder;
    private final GetServiceOrderUseCase getOrder;
    private final ChangeServiceOrderStatusUseCase changeStatus;

    public ServiceOrderController(
            ListServiceOrdersUseCase listOrders,
            OpenServiceOrderUseCase openOrder,
            GetServiceOrderUseCase getOrder,
            ChangeServiceOrderStatusUseCase changeStatus
    ) {
        this.listOrders = listOrders;
        this.openOrder = openOrder;
        this.getOrder = getOrder;
        this.changeStatus = changeStatus;
    }

    @GetMapping
    public List<ServiceOrderResponse> list(
            @RequestParam(required = false) ServiceOrderStatus status,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return listOrders.execute(caller.requireWorkshopId(), status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceOrderResponse open(
            @Valid @RequestBody OpenServiceOrderRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return openOrder.execute(request, caller.requireWorkshopId());
    }

    @GetMapping("/{id}")
    public ServiceOrderResponse get(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return getOrder.execute(id, caller.requireWorkshopId());
    }

    @PatchMapping("/{id}/status")
    public ServiceOrderResponse changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody ChangeStatusRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return changeStatus.execute(id, request, caller.requireWorkshopId());
    }
}
