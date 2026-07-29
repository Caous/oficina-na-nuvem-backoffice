package com.oficinaapp.oficina_app.infrastructure.persistence.mapper;

import com.oficinaapp.oficina_app.domain.model.MarketplaceOrder;
import com.oficinaapp.oficina_app.domain.model.MarketplaceOrderItem;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.MarketplaceOrderEntity;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.MarketplaceOrderItemEntity;

import java.util.List;

public final class MarketplaceOrderMapper {

    private MarketplaceOrderMapper() {
    }

    public static MarketplaceOrderEntity toEntity(MarketplaceOrder order) {
        MarketplaceOrderEntity entity = new MarketplaceOrderEntity();

        entity.setId(order.getId());
        entity.setCustomerId(order.getCustomerId());
        entity.setStatus(order.getStatus());
        entity.setPaymentMethod(order.getPaymentMethod());
        entity.setPaymentStatus(order.getPaymentStatus());
        entity.setDeliveryAddress(AddressMapper.toEmbeddable(order.getDeliveryAddress()));
        entity.setTotalAmount(order.getTotalAmount());
        entity.setPlacedAt(order.getPlacedAt());
        entity.setItems(toItemEntities(order.getItems()));

        return entity;
    }

    public static MarketplaceOrder toDomain(MarketplaceOrderEntity entity) {
        return MarketplaceOrder.builder()
                .id(entity.getId())
                .customerId(entity.getCustomerId())
                .status(entity.getStatus())
                .paymentMethod(entity.getPaymentMethod())
                .paymentStatus(entity.getPaymentStatus())
                .deliveryAddress(AddressMapper.toDomain(entity.getDeliveryAddress()))
                .totalAmount(entity.getTotalAmount())
                .placedAt(entity.getPlacedAt())
                .items(toDomainItems(entity.getItems()))
                .build();
    }

    private static List<MarketplaceOrderItemEntity> toItemEntities(List<MarketplaceOrderItem> items) {
        return items.stream().map(item -> {
            MarketplaceOrderItemEntity entity = new MarketplaceOrderItemEntity();

            entity.setId(item.getId());
            entity.setProductId(item.getProductId());
            entity.setProductName(item.getProductName());
            entity.setUnitPrice(item.getUnitPrice());
            entity.setQuantity(item.getQuantity());
            entity.setSellerWorkshopId(item.getSellerWorkshopId());
            entity.setSellerName(item.getSellerName());

            return entity;
        }).toList();
    }

    private static List<MarketplaceOrderItem> toDomainItems(List<MarketplaceOrderItemEntity> entities) {
        return entities.stream()
                .map(entity -> MarketplaceOrderItem.builder()
                        .id(entity.getId())
                        .productId(entity.getProductId())
                        .productName(entity.getProductName())
                        .unitPrice(entity.getUnitPrice())
                        .quantity(entity.getQuantity())
                        .sellerWorkshopId(entity.getSellerWorkshopId())
                        .sellerName(entity.getSellerName())
                        .build())
                .toList();
    }
}
