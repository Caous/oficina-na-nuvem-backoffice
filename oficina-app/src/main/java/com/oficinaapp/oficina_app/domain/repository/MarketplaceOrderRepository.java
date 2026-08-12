package com.oficinaapp.oficina_app.domain.repository;

import com.oficinaapp.oficina_app.domain.model.MarketplaceOrder;

import java.util.List;

public interface MarketplaceOrderRepository {

    MarketplaceOrder save(MarketplaceOrder order);

    List<MarketplaceOrder> findByCustomerId(Long customerId);
}
