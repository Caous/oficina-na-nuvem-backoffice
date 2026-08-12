package com.oficinaapp.oficina_app.application.dto.marketplace;

import com.oficinaapp.oficina_app.domain.enums.ProductCategory;
import com.oficinaapp.oficina_app.domain.model.Product;

import java.math.BigDecimal;

/** What the customer sees: no cost, no publication flag, but a seller. */
public record MarketplaceProductResponse(

        Long id,
        String name,
        String description,
        ProductCategory category,
        String sku,
        BigDecimal price,
        int stockQuantity,
        Long sellerWorkshopId,
        String sellerName

) {

    public static MarketplaceProductResponse from(Product product, String sellerName) {
        return new MarketplaceProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getSku(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getWorkshopId(),
                sellerName
        );
    }
}
