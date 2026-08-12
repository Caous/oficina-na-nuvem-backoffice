package com.oficinaapp.oficina_app.domain.model;

import com.oficinaapp.oficina_app.domain.enums.ProductCategory;
import com.oficinaapp.oficina_app.domain.exception.InsufficientStockException;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * One row serving both ends: the workshop controls price, stock and
 * publication; the customer only ever sees what is published and in stock.
 */
@Getter
@Builder(toBuilder = true)
public class Product {

    private final Long id;
    private final Long workshopId;
    private final String name;
    private final String description;
    private final ProductCategory category;
    private final String sku;
    private final BigDecimal price;
    private final int stockQuantity;
    private final boolean published;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public boolean isOutOfStock() {
        return stockQuantity <= 0;
    }

    public boolean isVisibleOnMarketplace() {
        return published && !isOutOfStock();
    }

    /**
     * Adds {@code delta} to the balance — a negative value takes units out.
     *
     * @throws InsufficientStockException when the balance would go negative.
     */
    public Product withStockChangedBy(int delta, LocalDateTime changedAt) {
        int balance = stockQuantity + delta;

        if (balance < 0) {
            throw new InsufficientStockException(name, stockQuantity);
        }

        return toBuilder()
                .stockQuantity(balance)
                .updatedAt(changedAt)
                .build();
    }
}
