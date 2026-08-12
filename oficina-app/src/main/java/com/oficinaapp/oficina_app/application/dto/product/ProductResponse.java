package com.oficinaapp.oficina_app.application.dto.product;

import com.oficinaapp.oficina_app.domain.enums.ProductCategory;
import com.oficinaapp.oficina_app.domain.model.Product;

import java.math.BigDecimal;

/** The workshop's own view of a product, stock and publication included. */
public record ProductResponse(

        Long id,
        String name,
        String description,
        ProductCategory category,
        String sku,
        BigDecimal price,
        int stockQuantity,
        boolean published,
        boolean visibleOnMarketplace

) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getSku(),
                product.getPrice(),
                product.getStockQuantity(),
                product.isPublished(),
                product.isVisibleOnMarketplace()
        );
    }
}
