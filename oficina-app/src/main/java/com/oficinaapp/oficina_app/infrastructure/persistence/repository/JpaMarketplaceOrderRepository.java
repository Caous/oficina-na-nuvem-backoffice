package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.infrastructure.persistence.entity.MarketplaceOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaMarketplaceOrderRepository extends JpaRepository<MarketplaceOrderEntity, Long> {

    List<MarketplaceOrderEntity> findByCustomerIdOrderByPlacedAtDesc(Long customerId);
}
