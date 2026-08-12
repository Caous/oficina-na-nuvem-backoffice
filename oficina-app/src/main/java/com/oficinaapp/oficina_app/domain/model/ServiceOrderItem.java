package com.oficinaapp.oficina_app.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * A service performed inside an order. The name and price are copied from the
 * catalogue when the order opens, so later edits to the catalogue never rewrite
 * what was agreed.
 */
@Getter
@Builder(toBuilder = true)
public class ServiceOrderItem {

    private final Long id;
    private final Long serviceId;
    private final String serviceName;
    private final BigDecimal price;
}
