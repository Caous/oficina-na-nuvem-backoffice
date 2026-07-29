package com.oficinaapp.oficina_app.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class WorkshopServiceTest {

    @Test
    @DisplayName("computes the floor the attendant may reach")
    void computesMinimumPrice() {
        WorkshopService service = serviceWith("200.00", "15.00");

        assertThat(service.minimumPrice()).isEqualByComparingTo("170.00");
    }

    @Test
    @DisplayName("keeps the full price when no discount is allowed")
    void keepsFullPriceWithoutDiscount() {
        WorkshopService service = serviceWith("200.00", "0.00");

        assertThat(service.minimumPrice()).isEqualByComparingTo("200.00");
    }

    private WorkshopService serviceWith(String price, String maxDiscountPercent) {
        return WorkshopService.builder()
                .id(1L)
                .workshopId(7L)
                .categoryId(1L)
                .name("Troca de oleo")
                .price(new BigDecimal(price))
                .maxDiscountPercent(new BigDecimal(maxDiscountPercent))
                .build();
    }
}
