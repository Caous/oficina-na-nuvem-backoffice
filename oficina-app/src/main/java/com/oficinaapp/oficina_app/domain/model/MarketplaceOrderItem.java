package com.oficinaapp.oficina_app.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * A line of an order. Name, price and seller are copied at purchase time: what
 * the customer bought must not change when the catalogue does.
 */
@Getter
@Builder(toBuilder = true)
public class MarketplaceOrderItem {

    private final Long id;
    private final Long productId;
    private final String productName;
    private final BigDecimal unitPrice;
    private final int quantity;
    private final Long sellerWorkshopId;
    private final String sellerName;

    public BigDecimal lineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
