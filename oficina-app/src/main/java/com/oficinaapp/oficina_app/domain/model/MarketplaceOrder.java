package com.oficinaapp.oficina_app.domain.model;

import com.oficinaapp.oficina_app.domain.enums.MarketplaceOrderStatus;
import com.oficinaapp.oficina_app.domain.enums.PaymentMethod;
import com.oficinaapp.oficina_app.domain.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A purchase made in the customer marketplace. One order may gather items from
 * more than one workshop; each line remembers who sells it.
 */
@Getter
@Builder(toBuilder = true)
public class MarketplaceOrder {

    private final Long id;
    private final Long customerId;
    private final MarketplaceOrderStatus status;
    private final PaymentMethod paymentMethod;
    private final PaymentStatus paymentStatus;
    private final Address deliveryAddress;
    private final BigDecimal totalAmount;
    private final LocalDateTime placedAt;
    private final List<MarketplaceOrderItem> items;

    public static MarketplaceOrder place(
            Long customerId,
            PaymentMethod paymentMethod,
            Address deliveryAddress,
            List<MarketplaceOrderItem> items,
            LocalDateTime placedAt
    ) {
        return MarketplaceOrder.builder()
                .customerId(customerId)
                .status(MarketplaceOrderStatus.PLACED)
                .paymentMethod(paymentMethod)
                .paymentStatus(PaymentStatus.PENDING)
                .deliveryAddress(deliveryAddress)
                .items(items)
                .totalAmount(sumOf(items))
                .placedAt(placedAt)
                .build();
    }

    private static BigDecimal sumOf(List<MarketplaceOrderItem> items) {
        return items.stream()
                .map(MarketplaceOrderItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
