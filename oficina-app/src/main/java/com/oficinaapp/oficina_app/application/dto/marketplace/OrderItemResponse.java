package com.oficinaapp.oficina_app.application.dto.marketplace;

import com.oficinaapp.oficina_app.domain.model.MarketplaceOrderItem;

import java.math.BigDecimal;

public record OrderItemResponse(

        Long productId,
        String productName,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal lineTotal,
        Long sellerWorkshopId,
        String sellerName

) {

    public static OrderItemResponse from(MarketplaceOrderItem item) {
        return new OrderItemResponse(
                item.getProductId(),
                item.getProductName(),
                item.getUnitPrice(),
                item.getQuantity(),
                item.lineTotal(),
                item.getSellerWorkshopId(),
                item.getSellerName()
        );
    }
}
