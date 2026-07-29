package com.oficinaapp.oficina_app.application.usecase.marketplace;

import com.oficinaapp.oficina_app.application.dto.marketplace.OrderResponse;
import com.oficinaapp.oficina_app.domain.repository.MarketplaceOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListMyOrdersUseCase {

    private final MarketplaceOrderRepository orders;

    public ListMyOrdersUseCase(MarketplaceOrderRepository orders) {
        this.orders = orders;
    }

    public List<OrderResponse> execute(Long customerId) {
        return orders.findByCustomerId(customerId).stream()
                .map(OrderResponse::from)
                .toList();
    }
}
