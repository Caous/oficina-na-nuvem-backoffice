package com.oficinaapp.oficina_app.application.dto.marketplace;

import com.oficinaapp.oficina_app.application.dto.common.AddressPayload;
import com.oficinaapp.oficina_app.domain.enums.MarketplaceOrderStatus;
import com.oficinaapp.oficina_app.domain.enums.PaymentMethod;
import com.oficinaapp.oficina_app.domain.enums.PaymentStatus;
import com.oficinaapp.oficina_app.domain.model.MarketplaceOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(

        Long id,
        MarketplaceOrderStatus status,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        AddressPayload deliveryAddress,
        BigDecimal totalAmount,
        LocalDateTime placedAt,
        List<OrderItemResponse> items

) {

    public static OrderResponse from(MarketplaceOrder order) {
        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getPaymentMethod(),
                order.getPaymentStatus(),
                AddressPayload.from(order.getDeliveryAddress()),
                order.getTotalAmount(),
                order.getPlacedAt(),
                order.getItems().stream().map(OrderItemResponse::from).toList()
        );
    }
}
