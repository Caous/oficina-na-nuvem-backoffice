package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.marketplace.OrderResponse;
import com.oficinaapp.oficina_app.application.dto.marketplace.PlaceOrderRequest;
import com.oficinaapp.oficina_app.application.usecase.marketplace.ListMyOrdersUseCase;
import com.oficinaapp.oficina_app.application.usecase.marketplace.PlaceOrderUseCase;
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

import java.util.List;

@RestController
@RequestMapping("/api")
public class MarketplaceOrderController {

    private final PlaceOrderUseCase placeOrder;
    private final ListMyOrdersUseCase listMyOrders;

    public MarketplaceOrderController(PlaceOrderUseCase placeOrder, ListMyOrdersUseCase listMyOrders) {
        this.placeOrder = placeOrder;
        this.listMyOrders = listMyOrders;
    }

    @PostMapping("/marketplace/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse place(
            @Valid @RequestBody PlaceOrderRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return placeOrder.execute(request, caller.id());
    }

    @GetMapping("/me/orders")
    public List<OrderResponse> myOrders(@AuthenticationPrincipal AuthenticatedUserPrincipal caller) {
        return listMyOrders.execute(caller.id());
    }
}
