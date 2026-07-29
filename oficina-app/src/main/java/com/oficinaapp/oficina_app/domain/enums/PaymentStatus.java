package com.oficinaapp.oficina_app.domain.enums;

/**
 * There is no payment gateway yet: an order is born {@code PENDING} and only a
 * cash-on-delivery order can be settled later without one.
 */
public enum PaymentStatus {
    PENDING,
    PAID,
    FAILED,
    REFUNDED
}
