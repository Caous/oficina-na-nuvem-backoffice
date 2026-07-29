package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.model.MarketplaceOrder;
import com.oficinaapp.oficina_app.domain.repository.MarketplaceOrderRepository;
import com.oficinaapp.oficina_app.infrastructure.persistence.mapper.MarketplaceOrderMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MarketplaceOrderRepositoryAdapter implements MarketplaceOrderRepository {

    private final JpaMarketplaceOrderRepository jpaRepository;

    public MarketplaceOrderRepositoryAdapter(JpaMarketplaceOrderRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public MarketplaceOrder save(MarketplaceOrder order) {
        return MarketplaceOrderMapper.toDomain(jpaRepository.save(MarketplaceOrderMapper.toEntity(order)));
    }

    @Override
    public List<MarketplaceOrder> findByCustomerId(Long customerId) {
        return jpaRepository.findByCustomerIdOrderByPlacedAtDesc(customerId).stream()
                .map(MarketplaceOrderMapper::toDomain)
                .toList();
    }
}
