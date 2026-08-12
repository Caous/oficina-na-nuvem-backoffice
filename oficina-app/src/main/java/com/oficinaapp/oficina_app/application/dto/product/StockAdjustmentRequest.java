package com.oficinaapp.oficina_app.application.dto.product;

import jakarta.validation.constraints.NotNull;

/**
 * @param delta units to add; a negative value takes units out.
 */
public record StockAdjustmentRequest(@NotNull Integer delta) {
}
