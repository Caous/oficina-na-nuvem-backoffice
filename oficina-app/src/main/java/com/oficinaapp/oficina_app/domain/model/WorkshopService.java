package com.oficinaapp.oficina_app.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A service the workshop sells, with the room it allows for bargaining.
 */
@Getter
@Builder(toBuilder = true)
public class WorkshopService {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    private final Long id;
    private final Long workshopId;
    private final Long categoryId;
    private final String name;
    private final String description;
    private final BigDecimal price;

    /** How much may be taken off the price, from 0 to 100. */
    private final BigDecimal maxDiscountPercent;

    /** The floor an attendant may reach applying the whole discount. */
    public BigDecimal minimumPrice() {
        BigDecimal remaining = ONE_HUNDRED.subtract(maxDiscountPercent);

        return price.multiply(remaining)
                .divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);
    }
}
