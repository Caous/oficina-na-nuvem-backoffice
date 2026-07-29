package com.oficinaapp.oficina_app.application.dto.marketplace;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * The price is not here on purpose: it is read from the catalogue, so a
 * tampered app cannot choose what to pay.
 */
public record OrderItemPayload(

        @NotNull Long productId,
        @NotNull @Min(1) Integer quantity

) {
}
