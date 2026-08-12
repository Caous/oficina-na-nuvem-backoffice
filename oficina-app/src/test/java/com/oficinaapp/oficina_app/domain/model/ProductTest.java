package com.oficinaapp.oficina_app.domain.model;

import com.oficinaapp.oficina_app.domain.enums.ProductCategory;
import com.oficinaapp.oficina_app.domain.exception.InsufficientStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    private static final LocalDateTime NOW = LocalDateTime.parse("2026-03-10T12:00:00");

    @Test
    @DisplayName("takes units out of stock")
    void takesUnitsOut() {
        Product product = productWithStock(10);

        assertThat(product.withStockChangedBy(-3, NOW).getStockQuantity()).isEqualTo(7);
    }

    @Test
    @DisplayName("puts units back in stock")
    void putsUnitsIn() {
        Product product = productWithStock(10);

        assertThat(product.withStockChangedBy(5, NOW).getStockQuantity()).isEqualTo(15);
    }

    @Test
    @DisplayName("refuses to leave the balance negative")
    void refusesNegativeBalance() {
        Product product = productWithStock(2);

        assertThatThrownBy(() -> product.withStockChangedBy(-3, NOW))
                .isInstanceOf(InsufficientStockException.class)
                .hasMessageContaining("Only 2 unit(s)");
    }

    @Test
    @DisplayName("stays off the marketplace while it is published but out of stock")
    void hidesPublishedProductWithoutStock() {
        Product product = productWithStock(0).toBuilder().published(true).build();

        assertThat(product.isPublished()).isTrue();
        assertThat(product.isVisibleOnMarketplace()).isFalse();
    }

    @Test
    @DisplayName("stays off the marketplace while it is in stock but unpublished")
    void hidesUnpublishedProduct() {
        Product product = productWithStock(5).toBuilder().published(false).build();

        assertThat(product.isVisibleOnMarketplace()).isFalse();
    }

    private Product productWithStock(int stockQuantity) {
        return Product.builder()
                .id(1L)
                .workshopId(7L)
                .name("Filtro de oleo")
                .category(ProductCategory.FILTERS)
                .sku("FIL-001")
                .price(new BigDecimal("49.90"))
                .stockQuantity(stockQuantity)
                .published(true)
                .createdAt(NOW)
                .updatedAt(NOW)
                .build();
    }
}
